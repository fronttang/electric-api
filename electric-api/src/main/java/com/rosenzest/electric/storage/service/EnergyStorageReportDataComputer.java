package com.rosenzest.electric.storage.service;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.deepoove.poi.data.ByteArrayPictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.rosenzest.base.Result;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.electric.storage.entity.EnergyStorage;
import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.electric.storage.report.EchartRenderingData;
import com.rosenzest.electric.storage.report.EnergyStorageEchartsData;
import com.rosenzest.electric.storage.report.EnergyStorageMonthPowerData;
import com.rosenzest.electric.storage.report.EnergyStoragePowerData;
import com.rosenzest.electric.storage.report.EnergyStorageYearData;
import com.rosenzest.electric.util.BigDecimalUtil;
import com.rosenzest.server.base.util.RestTemplateUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnergyStorageReportDataComputer {

	@Autowired
	private SystemProperties properties;

	public List<EnergyStorageMonthPowerData> monthPowerData(EnergyStorage project,
			List<EnergyStorageMonth> monthDatas) {
		List<EnergyStorageMonthPowerData> result = new ArrayList<EnergyStorageMonthPowerData>();

		if (CollUtil.isNotEmpty(monthDatas)) {

			for (EnergyStorageMonth month : monthDatas) {
				EnergyStorageMonthPowerData monthPowerData = new EnergyStorageMonthPowerData();

				String monthStr = month.getMonth();
				Date monthDate = DateUtil.parse(monthStr, "yyyy-MM");

				// 年月
				monthPowerData.setMonth(monthDate);

				// 这个月的第一天
				DateTime beginOfMonth = DateUtil.beginOfMonth(monthDate);
				monthPowerData.setStartDay(DateUtil.format(beginOfMonth, "yyyy-MM-dd"));

				// 这个月的最后一天
				DateTime endOfMonth = DateUtil.endOfMonth(monthDate);
				monthPowerData.setEndDay(DateUtil.format(endOfMonth, "yyyy-MM-dd"));

				// 用电天数
				int daysOfMonth = DateUtil.lengthOfMonth(DateUtil.month(monthDate) + 1,
						DateUtil.isLeapYear(DateUtil.year(monthDate)));
				monthPowerData.setDays(daysOfMonth);

				BigDecimal A = Objects.nonNull(month.getJian()) ? month.getJian() : BigDecimal.ZERO;
				BigDecimal B = Objects.nonNull(month.getFeng()) ? month.getFeng() : BigDecimal.ZERO;
				BigDecimal C = Objects.nonNull(month.getPing()) ? month.getPing() : BigDecimal.ZERO;
				BigDecimal D = Objects.nonNull(month.getGu()) ? month.getGu() : BigDecimal.ZERO;

				// 月总有功电量（kWh) A+B+C+D
				BigDecimal total = A.add(B).add(C).add(D);
				monthPowerData.setTotal(total);

				// 月尖期有功电量（kWh)
				monthPowerData.setJian(A);
				// 月峰期有功电量（kWh)
				monthPowerData.setFeng(B);
				// 月平期有功电量（kWh)
				monthPowerData.setPing(C);
				// 月谷期有功电量（kWh)
				monthPowerData.setGu(D);

				BigDecimal X = new BigDecimal(daysOfMonth);
				// 日平均用电（kWh)
				monthPowerData.setDayAverage(total.divide(X, 8, RoundingMode.HALF_UP));
				// 日尖、峰期平均用电（kWh)
				BigDecimal jianfeng = A.add(B);
				jianfeng = jianfeng.divide(X, 8, RoundingMode.HALF_UP);
				monthPowerData.setDayJianFeng(jianfeng);
				// 日平期平均用电（kWh)
				BigDecimal ping = C.divide(X, 8, RoundingMode.HALF_UP);
				monthPowerData.setDayPing(ping);
				// 日谷期平均用电（kWh)
				BigDecimal gu = D.divide(X, 8, RoundingMode.HALF_UP);
				monthPowerData.setDayGu(gu);

				// 日尖、峰期平均功率（kW)
				monthPowerData.setDayJianFengPower(jianfeng.divide(new BigDecimal(7), 8, RoundingMode.HALF_UP));
				// 日平期平均功率（kW)
				BigDecimal pingPower = ping.divide(new BigDecimal(9), 8, RoundingMode.HALF_UP);
				monthPowerData.setDayPingPower(pingPower);
				// 日谷期平均功率（kW)
				BigDecimal guPower = gu.divide(new BigDecimal(8), 8, RoundingMode.HALF_UP);
				monthPowerData.setDayGuPower(guPower);

				BigDecimal E = Objects.nonNull(project.getCapacity()) ? project.getCapacity() : BigDecimal.ZERO;
				// 变压器谷期剩余负载（kW)
				monthPowerData.setGuLoad(E.subtract(guPower));
				// 变压器平期期剩余负载（kW)
				monthPowerData.setPingLoad(E.subtract(pingPower));

				result.add(monthPowerData);
			}
		}

		return result;
	}

	public EnergyStoragePowerData powerData(EnergyStorage project, List<EnergyStorageMonthPowerData> monthPowerDatas) {

		EnergyStoragePowerData powerData = new EnergyStoragePowerData();

		if (CollUtil.isNotEmpty(monthPowerDatas)) {

			// 首尾各去掉( N-RD(N/2))/2 个月
			int totalMonth = monthPowerDatas.size();

			int deleteRow = (totalMonth - totalMonth / 2) / 2;

			if (deleteRow > 0) {
				monthPowerDatas.subList(0, deleteRow).clear();
				monthPowerDatas.subList(monthPowerDatas.size() - deleteRow, monthPowerDatas.size()).clear();
				// firstRows.addAll(lastRows);
				// monthPowerDatas.removeAll(firstRows);
			}

			// 综合消纳功率水平 T1=Average(日尖、峰期平均功率)
			Optional<BigDecimal> dayJianFengPowerOptional = monthPowerDatas.stream()
					.map(EnergyStorageMonthPowerData::getDayJianFengPower).reduce(BigDecimal::add);
			BigDecimal dayJianFengPower = dayJianFengPowerOptional.isPresent() ? dayJianFengPowerOptional.get()
					: BigDecimal.ZERO;
			BigDecimal T1 = dayJianFengPower.divide(new BigDecimal(monthPowerDatas.size()), 8, RoundingMode.HALF_UP);
			powerData.setT1(T1);

			// 变压器综合剩余情况 T2=Average(变压器平期期剩余负载)
			Optional<BigDecimal> pingLoadOptional = monthPowerDatas.stream()
					.map(EnergyStorageMonthPowerData::getPingLoad).reduce(BigDecimal::add);
			BigDecimal pingLoad = pingLoadOptional.isPresent() ? pingLoadOptional.get() : BigDecimal.ZERO;
			BigDecimal T2 = pingLoad.divide(new BigDecimal(monthPowerDatas.size()), 8, RoundingMode.HALF_UP);
			powerData.setT2(T2);

			// 综合预计装机功率
			BigDecimal T3 = T1.max(T2);
			powerData.setT3(T3);

			// 预计装机容量 T4=RD(T3/100)*100kW / RD(T3/100）*215kWh
			BigDecimal T4A = T3.divide(new BigDecimal(100), 0, RoundingMode.HALF_EVEN);
			T4A = T4A.multiply(new BigDecimal(100));
			powerData.setT4a(T4A);

			BigDecimal T4B = T3.divide(new BigDecimal(100), 0, RoundingMode.HALF_EVEN);
			T4B = T4B.multiply(new BigDecimal(215));
			powerData.setT4b(T4B);
		}

		return powerData;
	}

	public List<EnergyStorageYearData> yearData(EnergyStorage project, EnergyStoragePowerData powerData) {
		List<EnergyStorageYearData> result = new ArrayList<EnergyStorageYearData>();

		// 默认10年
		for (int i = 1; i <= 10; i++) {

			BigDecimal lastYear = BigDecimal.valueOf(i).subtract(BigDecimal.valueOf(1));

			EnergyStorageYearData yearData = new EnergyStorageYearData();
			yearData.setYear(i);
			yearData.setYearName(String.valueOf(i));

			// 充电量(万度）
			BigDecimal T4 = Objects.nonNull(powerData.getT4b()) ? powerData.getT4b() : BigDecimal.ZERO;
			BigDecimal F = Objects.nonNull(project.getDod()) ? project.getDod() : BigDecimal.ZERO;
			BigDecimal FRATE = F.divide(new BigDecimal(100), 8, RoundingMode.HALF_EVEN);
			BigDecimal G = Objects.nonNull(project.getEfficiency()) ? project.getEfficiency() : BigDecimal.ZERO;
			BigDecimal GRATE = G.divide(new BigDecimal(100), 8, RoundingMode.HALF_EVEN);
			BigDecimal H = Objects.nonNull(project.getAnnualDays()) ? new BigDecimal(project.getAnnualDays())
					: BigDecimal.ZERO;
			BigDecimal I = Objects.nonNull(project.getFirstDecayRate()) ? project.getFirstDecayRate() : BigDecimal.ZERO;
			BigDecimal IRATE = I.divide(new BigDecimal(100), 8, RoundingMode.HALF_EVEN);
			BigDecimal R = Objects.nonNull(project.getStepDecayRate()) ? project.getStepDecayRate() : BigDecimal.ZERO;
			BigDecimal RRATE = R.divide(new BigDecimal(100), 8, RoundingMode.HALF_EVEN);

			BigDecimal SQRTG = BigDecimalUtil.sqrt(GRATE, 8);

			BigDecimal temp = BigDecimal.ZERO;
			if (i == 1) {
				// T4*F/SQRT（G）*H*2*（1-I/2）/10000
				temp = BigDecimal.ONE.subtract(IRATE.divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_EVEN));
			} else {
				// T4*F/SQRT（G）*H*2*（1-I-R*上年份数/2）/10000
				temp = RRATE.multiply(lastYear).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_EVEN);
				temp = BigDecimal.ONE.subtract(IRATE).subtract(temp);
			}
			BigDecimal charge = T4.multiply(FRATE).divide(SQRTG, 8, RoundingMode.HALF_EVEN).multiply(H)
					.multiply(BigDecimal.valueOf(2));
			charge = charge.multiply(temp).divide(BigDecimal.valueOf(10000), 8, RoundingMode.HALF_EVEN);
			yearData.setCharge(charge);

			// 放电量(万度）
			// T4*F*SQRT（G）*H*2*(1-I/2)/10000
			// T4（2）*F*SQRT（G）*H*2*(1-I-R*上年份数/2）/10000
			BigDecimal discharge = T4.multiply(FRATE).multiply(SQRTG).multiply(H).multiply(BigDecimal.valueOf(2));
			discharge = discharge.multiply(temp).divide(BigDecimal.valueOf(10000), 8, RoundingMode.HALF_EVEN);
			yearData.setDischarge(discharge);

			// 差价收益
			// 放电量*J-充电量*（K+L）/2+放电量*0.2（M-J）
			BigDecimal J = Objects.nonNull(project.getFeng()) ? project.getFeng() : BigDecimal.ZERO;
			BigDecimal K = Objects.nonNull(project.getPing()) ? project.getPing() : BigDecimal.ZERO;
			BigDecimal L = Objects.nonNull(project.getGu()) ? project.getGu() : BigDecimal.ZERO;
			BigDecimal M = Objects.nonNull(project.getJian()) ? project.getJian() : BigDecimal.ZERO;

			BigDecimal temp1 = charge.multiply(J);
			BigDecimal temp2 = discharge.multiply(K.add(L)).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_EVEN);
			BigDecimal temp3 = discharge.multiply(BigDecimal.valueOf(0.2)).multiply(M.subtract(J));

			BigDecimal income = temp1.subtract(temp2).add(temp3);
			yearData.setIncome(income);

			// 其他收益 N+P
			BigDecimal N = Objects.nonNull(project.getIncome()) ? project.getIncome() : BigDecimal.ZERO;
			BigDecimal P = Objects.nonNull(project.getVirtualOperation()) ? project.getVirtualOperation()
					: BigDecimal.ZERO;

			BigDecimal otherIncome = N.add(P);
			yearData.setOtherIncome(otherIncome);

			// 业主收益
			// 差价收益*R+其他收益*Q
			BigDecimal R1 = Objects.nonNull(project.getProportion()) ? project.getProportion() : BigDecimal.ZERO;
			BigDecimal R1RATE = R1.divide(new BigDecimal(100), 8, RoundingMode.HALF_EVEN);
			BigDecimal Q = Objects.nonNull(project.getProportionOther()) ? project.getProportionOther()
					: BigDecimal.ZERO;
			BigDecimal QRATE = Q.divide(new BigDecimal(100), 8, RoundingMode.HALF_EVEN);

			BigDecimal ownerIncome = income.multiply(R1RATE).add(otherIncome.multiply(QRATE));
			yearData.setOwnerIncome(ownerIncome);

			// 资方收益
			// 差价收益+其他收益-业主收益
			BigDecimal investorIncome = income.add(otherIncome).subtract(ownerIncome);
			yearData.setInvestorIncome(investorIncome);

			result.add(yearData);
		}
		return result;
	}

	public EnergyStorageYearData totalYearData(List<EnergyStorageYearData> yearDatas) {
		EnergyStorageYearData result = new EnergyStorageYearData();

		if (CollUtil.isNotEmpty(yearDatas)) {
			result.setYearName("10年累计");

			// 充电量(万度）
			Optional<BigDecimal> chargeOptional = yearDatas.stream().filter((d) -> Objects.nonNull(d.getCharge()))
					.map(EnergyStorageYearData::getCharge).reduce(BigDecimal::add);
			BigDecimal charge = chargeOptional.isPresent() ? chargeOptional.get() : BigDecimal.ZERO;
			result.setCharge(charge);

			// 放电量(万度）
			Optional<BigDecimal> disChargeOptional = yearDatas.stream().filter((d) -> Objects.nonNull(d.getDischarge()))
					.map(EnergyStorageYearData::getDischarge).reduce(BigDecimal::add);
			BigDecimal disCharge = disChargeOptional.isPresent() ? disChargeOptional.get() : BigDecimal.ZERO;
			result.setDischarge(disCharge);

			// 差价收益
			Optional<BigDecimal> incomeOptional = yearDatas.stream().filter((d) -> Objects.nonNull(d.getIncome()))
					.map(EnergyStorageYearData::getIncome).reduce(BigDecimal::add);
			BigDecimal income = incomeOptional.isPresent() ? incomeOptional.get() : BigDecimal.ZERO;
			result.setIncome(income);

			// 其他收益
			Optional<BigDecimal> otherIncomeOptional = yearDatas.stream()
					.filter((d) -> Objects.nonNull(d.getOtherIncome())).map(EnergyStorageYearData::getOtherIncome)
					.reduce(BigDecimal::add);
			BigDecimal otherIncome = otherIncomeOptional.isPresent() ? otherIncomeOptional.get() : BigDecimal.ZERO;
			result.setOtherIncome(otherIncome);

			// 业主收益
			Optional<BigDecimal> ownerIncomeOptional = yearDatas.stream()
					.filter((d) -> Objects.nonNull(d.getOwnerIncome())).map(EnergyStorageYearData::getOwnerIncome)
					.reduce(BigDecimal::add);
			BigDecimal ownerIncome = ownerIncomeOptional.isPresent() ? ownerIncomeOptional.get() : BigDecimal.ZERO;
			result.setOwnerIncome(ownerIncome);

			// 资方收益
			Optional<BigDecimal> investorIncomeOptional = yearDatas.stream()
					.filter((d) -> Objects.nonNull(d.getInvestorIncome())).map(EnergyStorageYearData::getInvestorIncome)
					.reduce(BigDecimal::add);
			BigDecimal investorIncome = investorIncomeOptional.isPresent() ? investorIncomeOptional.get()
					: BigDecimal.ZERO;
			result.setInvestorIncome(investorIncome);
		}

		return result;
	}

	public EnergyStorageYearData averageYearData(EnergyStorageYearData totalYearData) {
		EnergyStorageYearData result = new EnergyStorageYearData();

		if (totalYearData != null) {
			result.setYearName("10年平均");

			// 充电量(万度）
			BigDecimal charge = Objects.nonNull(totalYearData.getCharge()) ? totalYearData.getCharge()
					: BigDecimal.ZERO;
			charge = charge.divide(BigDecimal.valueOf(10), 8, RoundingMode.HALF_EVEN);
			result.setCharge(charge);

			// 放电量(万度）
			BigDecimal disCharge = Objects.nonNull(totalYearData.getDischarge()) ? totalYearData.getDischarge()
					: BigDecimal.ZERO;
			disCharge = disCharge.divide(BigDecimal.valueOf(10), 8, RoundingMode.HALF_EVEN);
			result.setDischarge(disCharge);

			// 差价收益
			BigDecimal income = Objects.nonNull(totalYearData.getIncome()) ? totalYearData.getIncome()
					: BigDecimal.ZERO;
			income = income.divide(BigDecimal.valueOf(10), 8, RoundingMode.HALF_EVEN);
			result.setIncome(income);

			// 其他收益
			BigDecimal otherIncome = Objects.nonNull(totalYearData.getOtherIncome()) ? totalYearData.getOtherIncome()
					: BigDecimal.ZERO;
			otherIncome = otherIncome.divide(BigDecimal.valueOf(10), 8, RoundingMode.HALF_EVEN);
			result.setOtherIncome(otherIncome);

			// 业主收益
			BigDecimal ownerIncome = Objects.nonNull(totalYearData.getOwnerIncome()) ? totalYearData.getOwnerIncome()
					: BigDecimal.ZERO;
			ownerIncome = ownerIncome.divide(BigDecimal.valueOf(10), 8, RoundingMode.HALF_EVEN);
			result.setOwnerIncome(ownerIncome);

			// 资方收益
			BigDecimal investorIncome = Objects.nonNull(totalYearData.getInvestorIncome())
					? totalYearData.getInvestorIncome()
					: BigDecimal.ZERO;
			investorIncome = investorIncome.divide(BigDecimal.valueOf(10), 8, RoundingMode.HALF_EVEN);
			result.setInvestorIncome(investorIncome);
		}

		return result;
	}

	public List<EnergyStorageEchartsData> echartsData1(EnergyStorage project) {

		List<EnergyStorageEchartsData> result = new ArrayList<EnergyStorageEchartsData>();

		// BigDecimal M = BigDecimal.ZERO;
		BigDecimal J = BigDecimal.ZERO;
		BigDecimal K = BigDecimal.ZERO;
		BigDecimal L = BigDecimal.ZERO;

		if (project != null) {
			// M = Objects.nonNull(project.getJian()) ? project.getJian() : BigDecimal.ZERO;
			J = Objects.nonNull(project.getFeng()) ? project.getFeng() : BigDecimal.ZERO;
			K = Objects.nonNull(project.getPing()) ? project.getPing() : BigDecimal.ZERO;
			L = Objects.nonNull(project.getGu()) ? project.getGu() : BigDecimal.ZERO;
		}

		// double A = M.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double B = J.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double C = K.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double D = L.setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		// 谷（充电）
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		// 平（待机）
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		// 峰（充电）
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		// 平（充电）
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		// 峰（放电）
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		// 平（待机）
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));

		return result;
	}

	public List<EnergyStorageEchartsData> echartsData2(EnergyStorage project) {

		List<EnergyStorageEchartsData> result = new ArrayList<EnergyStorageEchartsData>();

		BigDecimal M = BigDecimal.ZERO;
		BigDecimal J = BigDecimal.ZERO;
		BigDecimal K = BigDecimal.ZERO;
		BigDecimal L = BigDecimal.ZERO;

		if (project != null) {
			M = Objects.nonNull(project.getJian()) ? project.getJian() : BigDecimal.ZERO;
			J = Objects.nonNull(project.getFeng()) ? project.getFeng() : BigDecimal.ZERO;
			K = Objects.nonNull(project.getPing()) ? project.getPing() : BigDecimal.ZERO;
			L = Objects.nonNull(project.getGu()) ? project.getGu() : BigDecimal.ZERO;
		}

		double A = M.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double B = J.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double C = K.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		double D = L.setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		// 谷（充电）
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		result.add(new EnergyStorageEchartsData(D, "#e1eed7"));
		// 平（待机）
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		// 峰（放电）
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		// 尖（放电）
		result.add(new EnergyStorageEchartsData(A, "#ff0000"));
		// 平（充电）
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		// 峰（待机）
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		// 尖（放电）
		result.add(new EnergyStorageEchartsData(A, "#ff0000"));
		result.add(new EnergyStorageEchartsData(A, "#ff0000"));
		// 峰（放电）
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		result.add(new EnergyStorageEchartsData(B, "#de8244"));
		// 平（待机）
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));
		result.add(new EnergyStorageEchartsData(C, "#f6c244"));

		return result;
	}

	public ByteArrayPictureRenderData getEnergyBarImage1(EnergyStorage project) {

		return getEnergyBarImage1(echartsData1(project));
	}

	public ByteArrayPictureRenderData getEnergyBarImage2(EnergyStorage project) {

		return getEnergyBarImage2(echartsData2(project));
	}

	public ByteArrayPictureRenderData getEnergyBarImage1(List<EnergyStorageEchartsData> data) {

		EchartRenderingData param = new EchartRenderingData();
		param.setData(data);
		param.setChartType("option1");
		return getEnergyBarImage(param);
	}

	public ByteArrayPictureRenderData getEnergyBarImage2(List<EnergyStorageEchartsData> data) {

		EchartRenderingData param = new EchartRenderingData();
		param.setData(data);
		param.setChartType("option2");
		return getEnergyBarImage(param);
	}

	public ByteArrayPictureRenderData getEnergyBarImage(EchartRenderingData param) {
		try {
			@SuppressWarnings("unchecked")
			Result<String> result = RestTemplateUtils.exchange(properties.getEcharts().getApi(), HttpMethod.POST, null,
					param, Result.class);

			if (result != null && result.getData() != null) {

				BufferedImage image = ImgUtil.toImage(StrUtil.subAfter(result.getData(), ",", false));

				ByteArrayPictureRenderData picture = new ByteArrayPictureRenderData(ImgUtil.toBytes(image, "png"));
				PictureStyle pictureStyle = new PictureStyle();
				pictureStyle.setScalePattern(WidthScalePattern.FIT);
				picture.setPictureStyle(pictureStyle);
				return picture;
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

}

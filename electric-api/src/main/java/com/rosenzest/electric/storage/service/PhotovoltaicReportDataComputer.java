package com.rosenzest.electric.storage.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rosenzest.electric.storage.entity.Photovoltaic;
import com.rosenzest.electric.storage.vo.PhotovoltaicAnnualEmissionData;
import com.rosenzest.electric.storage.vo.PhotovoltaicAnnualPowerData;

import cn.hutool.core.collection.CollUtil;

@Component
public class PhotovoltaicReportDataComputer {

	/**
	 * 项目年限
	 */
	public static final int years = 25;

	/**
	 * 发电量数据
	 * 
	 * @param data
	 */
	public List<PhotovoltaicAnnualPowerData> computeAnnualPowerData(Photovoltaic data) {

		List<PhotovoltaicAnnualPowerData> result = new ArrayList<PhotovoltaicAnnualPowerData>();

		BigDecimal annualPower = BigDecimal.ZERO;
		for (int i = 1; i <= years; i++) {
			PhotovoltaicAnnualPowerData reportData = new PhotovoltaicAnnualPowerData();
			reportData.setYear(i);
			if (i == 1) {
				// A*B/10000（1-C）

				BigDecimal A = Objects.nonNull(data.getEffectiveHours()) ? data.getEffectiveHours() : BigDecimal.ZERO;
				BigDecimal B = Objects.nonNull(data.getCapacityKw()) ? data.getCapacityKw() : BigDecimal.ZERO;
				BigDecimal C = Objects.nonNull(data.getFirstDecayRate()) ? data.getFirstDecayRate() : BigDecimal.ZERO;
				C = C.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);

				annualPower = A.multiply(B);
				annualPower = annualPower.divide(new BigDecimal(10000), 8, RoundingMode.HALF_UP);
				annualPower = annualPower.multiply(BigDecimal.ONE.subtract(C));

			} else {
				// 【上年发电量】*（1-D）
				BigDecimal D = Objects.nonNull(data.getStepDecayRate()) ? data.getStepDecayRate() : BigDecimal.ZERO;
				D = D.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
				annualPower = annualPower.multiply(BigDecimal.ONE.subtract(D));
			}

			// 发电量万KWh)
			reportData.setAnnualPower(annualPower);

			// 电价（度/元）
			BigDecimal E = Objects.nonNull(data.getAveragePrice()) ? data.getAveragePrice() : BigDecimal.ZERO;
			reportData.setPrice(E);

			// 项目收益（万元） 【发电量】*E
			BigDecimal benefits = annualPower.multiply(E);
			reportData.setBenefits(benefits);

			// 投资方收益占比
			BigDecimal F = Objects.nonNull(data.getDiscountRate()) ? data.getDiscountRate() : BigDecimal.ZERO;
			reportData.setInvestorBenefitsRate(F);

			BigDecimal FRATE = F.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
			// 业主方收益占比 1-F
			reportData.setOwnerBenefitsRate(new BigDecimal(100).subtract(F));

			// 投资方收益（万元） 【项目收益】*F
			reportData.setInvestorBenefits(benefits.multiply(FRATE));

			// 业主方收益（万元）【项目收益】*（1-F）
			reportData.setOwnerBenefits(benefits.multiply(BigDecimal.ONE.subtract(FRATE)));

			result.add(reportData);
		}

		return result;
	}

	/**
	 * 碳排放数据 X=122.9 Y=0.6565 Z=6.2 Q=2.1
	 * 
	 * @param annualPowerDatas
	 * @param data
	 * @return
	 */
	public PhotovoltaicAnnualEmissionData computeTotalAnnualEmissionData(
			List<PhotovoltaicAnnualPowerData> annualPowerDatas, Photovoltaic data) {

		PhotovoltaicAnnualEmissionData result = new PhotovoltaicAnnualEmissionData();

		BigDecimal X = new BigDecimal(122.9);
		BigDecimal Y = new BigDecimal(0.6565);
		BigDecimal Z = new BigDecimal(6.2);
		BigDecimal Q = new BigDecimal(2.1);

		if (CollUtil.isNotEmpty(annualPowerDatas)) {

			// 发电量（万千瓦时） SUM（1-25）

			Optional<BigDecimal> annualPowerOptional = annualPowerDatas.stream()
					.map(PhotovoltaicAnnualPowerData::getAnnualPower).reduce(BigDecimal::add);

			BigDecimal annualPower = annualPowerOptional.isPresent() ? annualPowerOptional.get() : BigDecimal.ZERO;
			result.setAnnualPower(annualPower);

			// 标准煤消耗量（吨） SUM（1-25）*X/100
			BigDecimal coal = annualPower.multiply(X);
			coal = coal.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
			result.setCoal(coal);

			// 二氧化碳排放量（吨）SUM（1-25）*Y/100
			BigDecimal co2 = annualPower.multiply(Y);
			co2 = co2.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
			result.setCo2(co2);

			// 硫氧化物排放量（吨） SUM（1-25）*Z/100
			BigDecimal sulfur = annualPower.multiply(Z);
			sulfur = sulfur.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
			result.setSulfur(sulfur);

			// 氮氧化物排放量（吨）SUM（1-25）*Q/100
			BigDecimal nitrogen = annualPower.multiply(Q);
			nitrogen = nitrogen.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP);
			result.setNitrogen(nitrogen);
		}

		return result;
	}

	public PhotovoltaicAnnualEmissionData computeAvarageAnnualEmissionData(
			List<PhotovoltaicAnnualPowerData> annualPowerDatas, Photovoltaic data) {

		PhotovoltaicAnnualEmissionData result = computeTotalAnnualEmissionData(annualPowerDatas, data);
		BigDecimal year = new BigDecimal(years);
		result.setAnnualPower(result.getAnnualPower().divide(year, 8, RoundingMode.HALF_UP));
		result.setCoal(result.getCoal().divide(year, 8, RoundingMode.HALF_UP));
		result.setCo2(result.getCo2().divide(year, 8, RoundingMode.HALF_UP));
		result.setSulfur(result.getSulfur().divide(year, 8, RoundingMode.HALF_UP));
		result.setNitrogen(result.getNitrogen().divide(year, 8, RoundingMode.HALF_UP));

		return result;
	}

	public PhotovoltaicAnnualPowerData getTotalAnnualPowerData(List<PhotovoltaicAnnualPowerData> annualPowerDatas) {
		PhotovoltaicAnnualPowerData total = new PhotovoltaicAnnualPowerData();
		if (CollUtil.isNotEmpty(annualPowerDatas)) {
			PhotovoltaicAnnualPowerData annualPowerData = annualPowerDatas.get(0);

			total.setYearName("25年总计");

			// 发电量万KWh
			Optional<BigDecimal> annualPowerOptional = annualPowerDatas.stream()
					.map(PhotovoltaicAnnualPowerData::getAnnualPower).reduce(BigDecimal::add);
			BigDecimal annualPower = annualPowerOptional.isPresent() ? annualPowerOptional.get() : BigDecimal.ZERO;
			total.setAnnualPower(annualPower);

			// 电价（度/元）
			BigDecimal E = Objects.nonNull(annualPowerData.getPrice()) ? annualPowerData.getPrice() : BigDecimal.ZERO;
			total.setPrice(E);

			// 项目收益（万元） 【发电量】*E
			Optional<BigDecimal> benefitsOptional = annualPowerDatas.stream()
					.map(PhotovoltaicAnnualPowerData::getBenefits).reduce(BigDecimal::add);
			BigDecimal benefits = benefitsOptional.isPresent() ? benefitsOptional.get() : BigDecimal.ZERO;
			total.setBenefits(benefits);

			// 投资方收益占比
			BigDecimal F = Objects.nonNull(annualPowerData.getInvestorBenefitsRate())
					? annualPowerData.getInvestorBenefitsRate()
					: BigDecimal.ZERO;
			total.setInvestorBenefitsRate(F);

			// 业主方收益占比 1-F
			total.setOwnerBenefitsRate(annualPowerData.getOwnerBenefitsRate());

			// 投资方收益（万元） 【项目收益】*F
			Optional<BigDecimal> investorBenefitsOptional = annualPowerDatas.stream()
					.map(PhotovoltaicAnnualPowerData::getInvestorBenefits).reduce(BigDecimal::add);
			BigDecimal investorBenefits = investorBenefitsOptional.isPresent() ? investorBenefitsOptional.get()
					: BigDecimal.ZERO;
			total.setInvestorBenefits(investorBenefits);

			// 业主方收益（万元）【项目收益】*（1-F）
			Optional<BigDecimal> ownerBenefitsOptional = annualPowerDatas.stream()
					.map(PhotovoltaicAnnualPowerData::getOwnerBenefits).reduce(BigDecimal::add);
			BigDecimal ownerBenefits = ownerBenefitsOptional.isPresent() ? ownerBenefitsOptional.get()
					: BigDecimal.ZERO;
			total.setOwnerBenefits(ownerBenefits);

		}
		return total;
	}

	public PhotovoltaicAnnualPowerData getAvarageAnnualPowerData(PhotovoltaicAnnualPowerData totalPowerData) {
		PhotovoltaicAnnualPowerData avarage = new PhotovoltaicAnnualPowerData();

		BigDecimal year = new BigDecimal(years);
		avarage.setYearName("25年平均");

		// 发电量万KWh
		BigDecimal annualPower = Objects.nonNull(totalPowerData.getAnnualPower()) ? totalPowerData.getAnnualPower()
				: BigDecimal.ZERO;
		avarage.setAnnualPower(annualPower.divide(year, 8, RoundingMode.HALF_UP));

		// 电价（度/元）
		avarage.setPrice(totalPowerData.getPrice());

		// 项目收益（万元） 【发电量】*E
		BigDecimal benefits = Objects.nonNull(totalPowerData.getBenefits()) ? totalPowerData.getBenefits()
				: BigDecimal.ZERO;
		avarage.setBenefits(benefits.divide(year, 8, RoundingMode.HALF_UP));

		// 投资方收益占比
		avarage.setInvestorBenefitsRate(totalPowerData.getInvestorBenefitsRate());

		// 业主方收益占比 1-F
		avarage.setOwnerBenefitsRate(totalPowerData.getOwnerBenefitsRate());

		// 投资方收益（万元） 【项目收益】*F
		BigDecimal investorBenefits = Objects.nonNull(totalPowerData.getInvestorBenefits())
				? totalPowerData.getInvestorBenefits()
				: BigDecimal.ZERO;
		avarage.setInvestorBenefits(investorBenefits.divide(year, 8, RoundingMode.HALF_UP));

		// 业主方收益（万元）【项目收益】*（1-F）
		BigDecimal ownerBenefits = Objects.nonNull(totalPowerData.getOwnerBenefits())
				? totalPowerData.getOwnerBenefits()
				: BigDecimal.ZERO;
		avarage.setOwnerBenefits(ownerBenefits.divide(year, 8, RoundingMode.HALF_UP));

		return avarage;
	}
}

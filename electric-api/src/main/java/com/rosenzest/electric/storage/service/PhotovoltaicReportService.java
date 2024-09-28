package com.rosenzest.electric.storage.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.IdUtils;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.electric.storage.entity.Photovoltaic;
import com.rosenzest.electric.storage.vo.PhotovoltaicAnnualEmissionData;
import com.rosenzest.electric.storage.vo.PhotovoltaicAnnualPowerData;
import com.rosenzest.electric.storage.vo.PhotovoltaicReportData;
import com.rosenzest.electric.storage.vo.PhotovoltaicReportPowerData;
import com.rosenzest.electric.storage.vo.PhotovoltaicVo;
import com.rosenzest.electric.util.FileUploadUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PhotovoltaicReportService {

	@Autowired
	private IPhotovoltaicService photovoltaicService;

	@Autowired
	private PhotovoltaicReportDataComputer dataComputer;

	public String report(Long id) {

		Photovoltaic photovoltaic = photovoltaicService.getById(id);

		if (photovoltaic != null) {

			PhotovoltaicReportData data = new PhotovoltaicReportData();

			PhotovoltaicVo photovoltaicVo = new PhotovoltaicVo();
			BeanUtils.copyProperties(photovoltaic, photovoltaicVo);
			photovoltaicVo.setProjectDate(DateUtil.format(photovoltaic.getCreateTime(), "yyyy年MM月"));
			// 项目信息
			data.setProject(photovoltaicVo);

			// 年发电量数据
			List<PhotovoltaicAnnualPowerData> annualPowerDatas = new ArrayList<PhotovoltaicAnnualPowerData>();
			List<PhotovoltaicAnnualPowerData> annualPowerData = dataComputer.computeAnnualPowerData(photovoltaic);
			if (CollUtil.isNotEmpty(annualPowerData)) {
				annualPowerDatas.addAll(annualPowerData);
				data.setFirstPower(annualPowerData.get(0));
			}

			// 25年总计
			PhotovoltaicAnnualPowerData total = dataComputer.getTotalAnnualPowerData(annualPowerData);
			// 25年平均
			PhotovoltaicAnnualPowerData avarage = dataComputer.getAvarageAnnualPowerData(total);
			annualPowerDatas.add(avarage);
			annualPowerDatas.add(total);

			data.setTotalPower(total);
			data.setAvaragePower(avarage);
			data.setAnnualPowerList(annualPowerDatas);

			List<PhotovoltaicReportPowerData> powerDatas = new ArrayList<PhotovoltaicReportPowerData>();

			if (CollUtil.isNotEmpty(annualPowerData)) {
				List<PhotovoltaicAnnualPowerData> powerData = new ArrayList<PhotovoltaicAnnualPowerData>();

				annualPowerData.forEach((annual) -> {
					PhotovoltaicAnnualPowerData temp = new PhotovoltaicAnnualPowerData();
					temp.setYearName(String.valueOf(annual.getYear()));
					temp.setAnnualPower(annual.getAnnualPower());
					powerData.add(temp);
				});

				PhotovoltaicAnnualPowerData firstData = new PhotovoltaicAnnualPowerData();
				Optional<PhotovoltaicAnnualPowerData> first = annualPowerData.stream()
						.filter((d) -> d.getYear().equals(1)).findFirst();
				firstData.setYearName("首年发电量");
				firstData.setAnnualPower(first.isPresent() ? first.get().getAnnualPower() : BigDecimal.ZERO);

				Optional<BigDecimal> annualPowerOptional = annualPowerData.stream()
						.map(PhotovoltaicAnnualPowerData::getAnnualPower).reduce(BigDecimal::add);
				BigDecimal annualPower = annualPowerOptional.isPresent() ? annualPowerOptional.get() : BigDecimal.ZERO;

				PhotovoltaicAnnualPowerData totalData = new PhotovoltaicAnnualPowerData();
				totalData.setYearName("25年发电量总和");
				totalData.setAnnualPower(annualPower);

				PhotovoltaicAnnualPowerData avarageData = new PhotovoltaicAnnualPowerData();
				avarageData.setYearName("平均每年发电量");
				avarageData.setAnnualPower(annualPower.divide(new BigDecimal(25), 8, RoundingMode.HALF_UP));

				powerData.add(firstData);
				powerData.add(avarageData);
				powerData.add(totalData);

				for (int i = 0; i < 14; i++) {

					PhotovoltaicReportPowerData reportPowerData = new PhotovoltaicReportPowerData();

					PhotovoltaicAnnualPowerData photovoltaicAnnualPowerData = powerData.get(i);
					reportPowerData.setYear(photovoltaicAnnualPowerData.getYearName());
					reportPowerData.setAnnualPower(photovoltaicAnnualPowerData.getAnnualPower());

					PhotovoltaicAnnualPowerData photovoltaicAnnualPowerData1 = powerData.get(i + 14);
					reportPowerData.setYear1(photovoltaicAnnualPowerData1.getYearName());
					reportPowerData.setAnnualPower1(photovoltaicAnnualPowerData1.getAnnualPower());

					powerDatas.add(reportPowerData);
				}
			}

			data.setPowerList(powerDatas);

			// 排放数据
			PhotovoltaicAnnualEmissionData totalEmission = dataComputer.computeTotalAnnualEmissionData(annualPowerData,
					photovoltaic);
			data.setTotalEmission(totalEmission);

			PhotovoltaicAnnualEmissionData avarageEmission = dataComputer
					.computeAvarageAnnualEmissionData(annualPowerData, photovoltaic);
			data.setAvarageEmission(avarageEmission);

			try {
				LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
				ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("annualPowerList", policy)
						.bind("powerList", policy);
				Configure config = configureBuilder.build();

				Map<String, Object> dataMap = BeanUtil.beanToMap(data);

				log.info("dataMap:{}", JSON.toJSONString(dataMap));

				NiceXWPFDocument main;

				InputStream mainInputStream = ClassPathResource.class.getClassLoader()
						.getResourceAsStream("report/photovoltaic.docx");
				XWPFTemplate mainTemplate = XWPFTemplate.compile(mainInputStream, config).render(dataMap);
				main = mainTemplate.getXWPFDocument();

				LocalDateTime now = LocalDateTime.now();
				String fileName = photovoltaic.getName() + "报告.docx";
				String datePath = DateUtil.format(now, "yyyy/MM/dd");
				String filePath = StrUtil.format("{}/{}/{}", datePath, IdUtils.getSnowflakeNextIdStr(), fileName);

				String baseDir = SystemProperties.getUploadPath();
				File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

				main.write(new FileOutputStream(saveFile));

				PoitlIOUtils.closeQuietlyMulti(main); // 最后不要忘记关闭这些流。
				String url = FileUploadUtils.getPathFileName(baseDir, filePath);
				return url;
			} catch (Exception e) {
				log.error("", e);
			}

		}
		return null;
	}
}

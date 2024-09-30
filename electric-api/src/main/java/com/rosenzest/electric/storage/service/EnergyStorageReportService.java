package com.rosenzest.electric.storage.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
import com.rosenzest.electric.storage.entity.EnergyStorage;
import com.rosenzest.electric.storage.entity.EnergyStorageMonth;
import com.rosenzest.electric.storage.report.EnergyStorageMonthPowerData;
import com.rosenzest.electric.storage.report.EnergyStoragePowerData;
import com.rosenzest.electric.storage.report.EnergyStorageReportData;
import com.rosenzest.electric.storage.report.EnergyStorageYearData;
import com.rosenzest.electric.storage.vo.EnergyStorageVo;
import com.rosenzest.electric.util.FileUploadUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnergyStorageReportService {

	@Autowired
	private IEnergyStorageService energyStorageService;

	@Autowired
	private IEnergyStorageMonthService energyStorageMonthService;

	@Autowired
	private EnergyStorageReportDataComputer dataComputer;

	public String report(Long id) {

		EnergyStorage project = energyStorageService.getById(id);
		if (project != null) {
			// 月原始数据
			List<EnergyStorageMonth> energyStorageMonthList = energyStorageMonthService.getListByEnergyStorageId(id);

			// 月数据
			List<EnergyStorageMonthPowerData> monthPowerData = dataComputer.monthPowerData(project,
					energyStorageMonthList);

			// 计算信息
			EnergyStoragePowerData powerData = dataComputer.powerData(project, monthPowerData);

			// 年数据
			List<EnergyStorageYearData> yearData = dataComputer.yearData(project, powerData);

			EnergyStorageYearData firstYearData = yearData.get(0);

			// 年数据汇总
			EnergyStorageYearData totalYearData = dataComputer.totalYearData(yearData);

			// 平均年数据
			EnergyStorageYearData averageYearData = dataComputer.averageYearData(totalYearData);

			yearData.add(averageYearData);
			yearData.add(totalYearData);

			EnergyStorageReportData reportData = new EnergyStorageReportData();

			EnergyStorageVo vo = new EnergyStorageVo();
			BeanUtils.copyProperties(project, vo);
			vo.setProjectDate(DateUtil.format(project.getCreateTime(), "yyyy年MM月"));
			reportData.setProject(vo);
			reportData.setYearList(yearData);
			reportData.setPower(powerData);
			reportData.setTotal(totalYearData);
			reportData.setAverage(averageYearData);
			reportData.setFirst(firstYearData);
			reportData.setBar1(dataComputer.getEnergyBarImage1(project));
			reportData.setBar2(dataComputer.getEnergyBarImage2(project));

			try {
				LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
				ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("yearList", policy);
				Configure config = configureBuilder.build();

				Map<String, Object> dataMap = BeanUtil.beanToMap(reportData);

				log.info("dataMap:{}", JSON.toJSONString(dataMap));

				NiceXWPFDocument main;

				InputStream mainInputStream = ClassPathResource.class.getClassLoader()
						.getResourceAsStream("report/energystorage.docx");
				XWPFTemplate mainTemplate = XWPFTemplate.compile(mainInputStream, config).render(dataMap);
				main = mainTemplate.getXWPFDocument();

				LocalDateTime now = LocalDateTime.now();
				String fileName = project.getName() + "报告.docx";
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

package com.rosenzest.electric.controller;

import static cn.binarywang.wx.miniapp.constant.WxMaConstants.DEFAULT_ENV_VERSION;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.ListResult;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.PageList;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.dto.InitialOwnerUnitSettingDto;
import com.rosenzest.electric.dto.OwnerUnitDto;
import com.rosenzest.electric.dto.OwnerUnitQuery;
import com.rosenzest.electric.entity.OwnerUnit;
import com.rosenzest.electric.entity.OwnerUnitReport;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.ProjectWorker;
import com.rosenzest.electric.enums.InitialInspectionStatus;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.enums.ProjectWorkerType;
import com.rosenzest.electric.enums.UnitReportType;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.electric.service.IOwnerUnitDangerService;
import com.rosenzest.electric.service.IOwnerUnitReportService;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.IProjectWorkerService;
import com.rosenzest.electric.util.FileUploadUtils;
import com.rosenzest.electric.vo.InitialOwnerUnitVo;
import com.rosenzest.electric.vo.OwnerUnitQrcodeVo;
import com.rosenzest.electric.vo.OwnerUnitVo;
import com.rosenzest.electric.vo.ReportFileVo;
import com.rosenzest.server.base.annotation.TokenRule;
import com.rosenzest.server.base.enums.UserType;
import com.rosenzest.server.base.util.RestTemplateUtils;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

@Slf4j
@Api(tags = "业主单元")
@RestController
@RequestMapping("/unit")
public class OwnerUnitController extends ElectricBaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerService projectWorkerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private SystemProperties properties;

	@Autowired
	private WxMaService wxMaService;

	@ApiOperation(tags = "业主单元", value = "业主单元列表(初检)")
	@PostMapping("/initial/list")
	public ListResult<InitialOwnerUnitVo> list(@RequestBody @Valid OwnerUnitQuery query) {

		query.setProjectId(getProjectId());

		List<InitialOwnerUnitVo> unitList = new ArrayList<InitialOwnerUnitVo>();
		Project project = projectService.getById(query.getProjectId());
		if (project == null) {
			return ListResult.SUCCESS(0L, unitList);
		}
		query.setType(project.getType());

		ProjectWorker projectWorker = projectWorkerService.getProjectWorker(query.getProjectId(), getUserId(),
				ProjectWorkerType.INSPECTOR.code());
		if (projectWorker == null) {
			return ListResult.SUCCESS(0L, unitList);
		}

		query.setWorkerId(projectWorker.getId());

		PageList pageList = new PageList(query.getPage(), query.getPageSize());
		unitList = ownerUnitService.queryInitialList(query, pageList);

		return ListResult.SUCCESS(pageList.getTotalNum(), unitList);
	}

	@ApiOperation(tags = "业主单元", value = "设置初检状态")
	@PostMapping("/setting")
	public Result<?> setting(@RequestBody @Valid InitialOwnerUnitSettingDto data) {

		LoginUser loginUser = getLoginUser();

		OwnerUnit ownerUnit = ownerUnitService.getById(data.getId());
		if (ownerUnit == null) {
			throw new BusinessException(400, "业主单元不存在");
		}

		// 导入数据不做检查
		if (!"admin".equalsIgnoreCase(ownerUnit.getCreateBy())) {
			// 非admin数据检查编辑权限 工作人员权限检查
			checkPermission(ownerUnit, ownerUnit);
		}

//		if (!String.valueOf(loginUser.getUserId()).equalsIgnoreCase(ownerUnit.getCreateBy())) {
//			// 工作人员权限检查
//			if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
//					ProjectWorkerAreaRoleType.EDIT)) {
//				return Result.ERROR(400, "无操作权限");
//			}
//		}

		OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndType(data.getId(), UnitReportType.INITIAL);

		// if (report != null &&
		// InitialInspectionStatus.FINISH.code().equalsIgnoreCase(report.getDetectStatus()))
		// {
		// return Result.ERROR(400, "已初检完成");
		// }

		if (report == null) {
			report = new OwnerUnitReport();
		}
		report.setType(UnitReportType.INITIAL.code());
		report.setInspector(loginUser.getName());
		report.setInspectorId(loginUser.getUserId());
		report.setUnitId(data.getId());
		report.setIsDangerNotice(data.getIsDangerNotice());
		report.setIsComplete(data.getIsComplete());
		report.setIsTest(data.getIsTest());
		report.setIsTestReason(data.getIsTestReason());
		report.setDetectData(new Date());
		if ("1".equalsIgnoreCase(data.getIsComplete())) {
			report.setDetectStatus(InitialInspectionStatus.FINISH.code());
		} else if ("1".equalsIgnoreCase(data.getIsTest())) {
			report.setDetectStatus(InitialInspectionStatus.UNABLE_TO_DETECT.code());
		} else {
			report.setDetectStatus(InitialInspectionStatus.CHECKING.code());
		}

		// 没有初检编号的随机生成一个
		if (StrUtil.isBlank(report.getCode())) {
			report.setCode(SnowFlakeUtil.uniqueString());
		}

		if ("admin".equalsIgnoreCase(ownerUnit.getCreateBy())) {
			OwnerUnit update = new OwnerUnit();
			update.setId(data.getId());
			update.setCreateBy(String.valueOf(loginUser.getUserId()));
			ownerUnitService.updateById(update);
		}

		if (ownerUnitReportService.saveOrUpdate(report)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元", value = "查询业主单元信息(城中村/工业园)")
	@GetMapping("/{unitId}")
	public Result<OwnerUnitVo> unitInfo(@PathVariable Long unitId) {

		OwnerUnitVo ownerUnit = ownerUnitService.getOwnerUnitById(unitId);

		if (ownerUnit != null) {
			if (ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(ownerUnit.getType())
					|| ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(ownerUnit.getType())) {
				return Result.SUCCESS(ownerUnit);
			}
		}
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "业主单元", value = "添加/修改业主单元(城中村/工业园)")
	@PostMapping("")
	public Result<OwnerUnitVo> saveUnit(@RequestBody @Valid OwnerUnitDto data) {

		LoginUser loginUser = getLoginUser();

		data.setProjectId(loginUser.getProjectId());

		Project project = projectService.getById(data.getProjectId());
		if (project == null) {
			return Result.ERROR(400, "无操作权限");
		}

		if (!ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(project.getType())
				&& !ProjectType.INDUSTRIAL_AREA.code().equalsIgnoreCase(project.getType())) {
			return Result.ERROR(400, "非城中村/工业园项目");
		}

		if (StrUtil.isBlank(data.getDistrict())) {
			return Result.ERROR(400, "区ID不能为空");
		} else if (StrUtil.isBlank(data.getStreet())) {
			return Result.ERROR(400, "街道ID不能为空");
		}
		if (ProjectType.URBAN_VILLAGE.code().equalsIgnoreCase(project.getType())) {
			if (StrUtil.isBlank(data.getCommunity())) {
				return Result.ERROR(400, "社区ID不能为空");
			} else if (StrUtil.isBlank(data.getHamlet())) {
				return Result.ERROR(400, "村ID不能为空");
			}
		}

		OwnerUnit unit = new OwnerUnit();
		BeanUtils.copyProperties(data, unit);

		if (data.getId() != null) {
			OwnerUnit dbUnit = ownerUnitService.getById(data.getId());

			if (dbUnit == null) {
				return Result.ERROR(400, "无操作权限");
			}

			data.setCreateBy(dbUnit.getCreateBy());

			// 导入数据不做检查
			if (!"admin".equalsIgnoreCase(dbUnit.getCreateBy())) {
				// 非admin数据检查编辑权限 工作人员权限检查
				checkPermission(dbUnit, dbUnit);
			}
		}
//		// 工作人员权限检查
//		if (!projectWorkerService.checkWorkerAreaRole(unit, loginUser.getUserId(), ProjectWorkerAreaRoleType.EDIT)) {
//			return Result.ERROR(400, "无操作权限");
//		}

		if (ownerUnitService.checkOwnerUnitName(unit)) {
			return Result.ERROR(400, StrUtil.format("该项目区域下已存在名为[{}]的业主单元", unit.getName()));
		}

		data.setProjectName(project.getName());
		data.setDetectId(project.getDetectId());
		data.setType(project.getType());

		if (ownerUnitService.saveOwnerUnit(data)) {
			return this.unitInfo(data.getId());
		} else {
			return Result.ERROR();
		}
	}

	@ApiOperation(tags = "业主单元", value = "删除业主单元")
	@DeleteMapping("/{unitId}")
	public Result<?> deleteUnit(@PathVariable Long unitId) {

		// LoginUser loginUser = getLoginUser();
		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);
		if (ownerUnit == null) {
			return Result.ERROR(400, "无操作权限");
		}

		// 导入数据不做检查
		if (!"admin".equalsIgnoreCase(ownerUnit.getCreateBy())) {
			// 非admin数据检查编辑权限 工作人员权限检查
			checkPermission(ownerUnit, ownerUnit);
		}

//		// 工作人员权限检查
//		if (!projectWorkerService.checkWorkerAreaRole(ownerUnit, loginUser.getUserId(),
//				ProjectWorkerAreaRoleType.EDIT)) {
//			return Result.ERROR(400, "无操作权限");
//		}

		// 检查是否有隐患
		if (ownerUnitDangerService.countByUnitId(unitId) > 0) {
			return Result.ERROR(400, "请先删除隐患数据！");
		}

		if (ProjectType.CHARGING_STATION.code().equalsIgnoreCase(ownerUnit.getType())) {
			// 充电站
		}

		if (ownerUnitService.removeById(unitId)) {
			return Result.SUCCESS();
		} else {
			return Result.ERROR();
		}
	}

	@TokenRule(project = false, terminal = { TerminalType.APP, TerminalType.MINIAPP }, userType = { UserType.WORKER,
			UserType.GRADMAN, UserType.OWNER_UNIT, UserType.AREA_USER, UserType.VISITOR })
	@ApiOperation(tags = "业主单元", value = "业主单元二维码")
	@GetMapping("/qrcode/{unitId}")
	public Result<OwnerUnitQrcodeVo> unitQrcode(@PathVariable Long unitId) throws WxErrorException, IOException {

		OwnerUnit ownerUnit = ownerUnitService.getById(unitId);

		if (ownerUnit != null) {

			OwnerUnitQrcodeVo vo = new OwnerUnitQrcodeVo();
			vo.setId(ownerUnit.getId());
			vo.setName(ownerUnit.getName());
			vo.setAddress(ownerUnit.getAddress());

			OwnerUnitReport report = ownerUnitReportService.getReportByUnitIdAndType(ownerUnit.getId(),
					UnitReportType.INITIAL);
			if (report != null) {
				vo.setDetectData(report.getDetectData());
			} else {
				vo.setDetectData(new Date());
			}
			if (StrUtil.isNotBlank(ownerUnit.getMngQrcode())) {
				vo.setQrcode(ownerUnit.getMngQrcode());
				return Result.SUCCESS(vo);
			}
			// 业主端地址
			String page = properties.getMiniapp().getOwnerUrl();

			DES des = SecureUtil.des(properties.getOwnerUnitDesKey().getBytes());

			// VisitorKeyDto keyData = new VisitorKeyDto(ownerUnit.getId());

			String key = des.encryptHex(String.valueOf(ownerUnit.getId()));
//			ownerUrl = UrlBuilder.of(ownerUrl).addQuery("key", key).build();
//			// ownerUrl = URLUtil.encode(ownerUrl);
//			String qrcode = QrCodeUtil.generateAsBase64(ownerUrl, QrConfig.create(), "png");

			log.info("key:{}", key);

			// String scene = StrUtil.format("key={}", key);
			// scene = URLUtil.encode(scene);
			WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
			byte[] qrCodeByte = qrcodeService.createWxaCodeUnlimitBytes(key, page, false, DEFAULT_ENV_VERSION, 430,
					true, null, false);

			LocalDateTime now = LocalDateTime.now();
			String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
			String fileName = timestamp + SnowFlakeUtil.uniqueString();
			String datePath = DateUtil.format(now, "yyyy/MM/dd");

			String filePath = StrUtil.format("{}/{}.png", datePath, fileName);

			String baseDir = SystemProperties.getUploadPath();
			File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

			ImgUtil.write(ImgUtil.toImage(qrCodeByte), saveFile);

			// String qrcode = ImgUtil.toBase64DataUri(ImgUtil.toImage(qrCodeByte), "png");

			String qrcode = FileUploadUtils.getPathFileName(baseDir, filePath);
			vo.setQrcode(qrcode);

			OwnerUnit update = new OwnerUnit();
			update.setId(ownerUnit.getId());
			update.setMngQrcode(qrcode);
			ownerUnitService.saveOrUpdate(update);

			return Result.SUCCESS(vo);
		}
		return Result.ERROR(ResultEnum.FORBIDDEN);
	}

	@TokenRule(project = false, terminal = { TerminalType.APP, TerminalType.MINIAPP }, userType = { UserType.WORKER,
			UserType.GRADMAN, UserType.OWNER_UNIT, UserType.AREA_USER, UserType.VISITOR })
	@ApiOperation(tags = "业主单元", value = "业主单元word报告")
	@GetMapping("/report/word/{unitId}/{type}")
	public Result<ReportFileVo> reportWord(@PathVariable Long unitId, @PathVariable String type) {

		// LoginUser loginUser = getLoginUser();

		UnitReportType reportType = EnumUtils.init(UnitReportType.class).fromCode(type);
		if (reportType == null) {
			return Result.ERROR();
		}

		String url = StrUtil.format("{}/report/download/{}/{}", properties.getAdmin(), unitId, type);

		@SuppressWarnings("unchecked")
		Result<ReportFileVo> result = RestTemplateUtils.exchange(url, HttpMethod.GET, null, null, Result.class);

		return result;
	}
}

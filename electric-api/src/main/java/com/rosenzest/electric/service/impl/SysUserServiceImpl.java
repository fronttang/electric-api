package com.rosenzest.electric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.base.LoginUser;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.enums.TerminalType;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.base.util.BeanUtils;
import com.rosenzest.base.util.RedisUtil;
import com.rosenzest.base.util.SnowFlakeUtil;
import com.rosenzest.electric.constant.ElectricErrorCode;
import com.rosenzest.electric.dto.LoginDto;
import com.rosenzest.electric.entity.DetectUnit;
import com.rosenzest.electric.entity.Project;
import com.rosenzest.electric.entity.SysUser;
import com.rosenzest.electric.mapper.SysUserMapper;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.electric.service.IDetectUnitService;
import com.rosenzest.electric.service.IProjectService;
import com.rosenzest.electric.service.ISysUserService;
import com.rosenzest.electric.vo.DetectUnitVo;
import com.rosenzest.electric.vo.LoginVo;
import com.rosenzest.electric.vo.ProjectVo;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.cache.CacheKeyBuilder;
import com.rosenzest.server.base.enums.UserStatus;
import com.rosenzest.server.base.enums.UserType;
import com.rosenzest.server.base.properties.TokenProperties;
import com.rosenzest.server.base.util.JwtTokenUtil;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Service
public class SysUserServiceImpl extends ModelBaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	private TokenProperties tokenProperties;

	@Autowired
	private IDetectUnitService detectUnitService;

	@Autowired
	private IProjectService projectService;

	@Override
	public SysUser getByUserName(String userName) {

		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>();
		queryWrapper.eq(SysUser::getUserName, userName);
		queryWrapper.last(" LIMIT 1 ");

		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	public LoginVo login(LoginDto login) {

		// 通过用户名，用户名就是手机号
		String userName = login.getUserName();
		SysUser user = getByUserName(userName);
		if (user != null) {

			TerminalType terminalType = EnumUtils.init(TerminalType.class).fromCode(login.getTerminal());

			// 只能 工作人员/电力设计/光伏&储能 登录APP

			if (TerminalType.APP == terminalType) {
				if (!UserType.WORKER.code().equalsIgnoreCase(user.getUserType())
						&& !UserType.STORAGE.code().equalsIgnoreCase(user.getUserType())
						&& !UserType.ELECTRIC.code().equalsIgnoreCase(user.getUserType())) {
					throw new BusinessException(ElectricErrorCode.VERIFICATION_ERROR);
				}
			} else if (TerminalType.MINIAPP == terminalType) {
				// 业主单元账号/街区账号/网络员可以登录小程序
				if (!UserType.OWNER_UNIT.code().equalsIgnoreCase(user.getUserType())
						&& !UserType.AREA_USER.code().equalsIgnoreCase(user.getUserType())
						&& !UserType.GRADMAN.code().equalsIgnoreCase(user.getUserType())) {
					throw new BusinessException(ElectricErrorCode.VERIFICATION_ERROR);
				}
			} else {
				throw new BusinessException(ElectricErrorCode.VERIFICATION_ERROR);
			}

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			// 匹配密码
			if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {

				if (!UserStatus.OK.code().equalsIgnoreCase(user.getStatus())) {
					throw new BusinessException("用户已被停用");
				}

				String token = generateToken(user, terminalType);
				return getLoginVo(user, token, terminalType);
			} else {
				throw new BusinessException(ElectricErrorCode.VERIFICATION_ERROR);
			}
		} else {
			throw new BusinessException(ElectricErrorCode.VERIFICATION_ERROR);
		}
	}

	private LoginVo getLoginVo(SysUser user, String token, TerminalType terminalType) {
		LoginVo loginVo = new LoginVo();
		loginVo.setUserId(user.getUserId());
		loginVo.setUserName(user.getUserName());
		loginVo.setType(user.getUserType());
		loginVo.setToken(token);
		loginVo.setNickName(user.getNickName());
		loginVo.setPicModel(user.getPicModel());

		// 获取用户检测单位
		Long detectId = user.getDetectId();
		if (detectId != null && TerminalType.APP == terminalType) {
			DetectUnit detectUnit = detectUnitService.getById(detectId);
			DetectUnitVo detectUnitVo = new DetectUnitVo();
			BeanUtils.copyProperties(detectUnit, detectUnitVo);
			loginVo.setDetectUnit(detectUnitVo);
		}

		if (TerminalType.MINIAPP == terminalType && user.getProjectId() != null) {
			Project project = projectService.getById(user.getProjectId());
			ProjectVo projectVo = new ProjectVo();
			BeanUtils.copyProperties(project, projectVo);
			loginVo.setProject(projectVo);
		}

		return loginVo;
	}

	protected String generateToken(SysUser user, TerminalType terminalType) {

		if (terminalType == null) {
			terminalType = TerminalType.APP;
		}

		// Long userId = user.getUserId();

		// 用户登录标识
		// 20240730 可重复登录
		String uuid = SnowFlakeUtil.uniqueString();

		String tokenKey = CacheKeyBuilder.getCustTokenKey(terminalType.code(), user.getUserId(), uuid);

		// 构建TOKEN
		LoginUser payload = new LoginUser();
		payload.setUserId(user.getUserId());
		payload.setUserName(user.getUserName());
		payload.setType(user.getUserType());
		payload.setTerminal(terminalType);
		payload.setDetectId(user.getDetectId());
		payload.setName(user.getNickName());
		payload.setUuid(uuid);
		payload.setAccountType(user.getAccountType());

		// if (TerminalType.MINIAPP == terminalType) {
		payload.setProjectId(user.getProjectId());
		// }

		String token = JwtTokenUtil.generateToken(payload);

		// 用户token及用户信息缓存
		RedisUtil.set(tokenKey, token, tokenProperties.getExpire());
		// RedisUtil.set(CacheKeyBuilder.getCustInfoKey(userId), payload,
		// SystemConstants.ONE_DAY_OF_SECONDS);

		return token;
	}

	@Override
	public AreaUserInfoVo getAreaUserInfo(Long userId, Long projectId) {
		return this.baseMapper.getAreaUserInfo(userId, projectId);
	}

}

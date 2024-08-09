package com.rosenzest.electric.miniapp.owner.executor;

import java.util.Map;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosenzest.base.LoginUser;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.electric.service.IOwnerUnitService;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.annotation.PermissionParam;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.enums.UserType;
import com.rosenzest.server.base.permission.IPermissionExecutor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;

@Component
public class OwnerUnitBelongPermissionExecutor implements IPermissionExecutor {

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Override
	public boolean execute(Permission permission, JoinPoint joinPoint) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();
		if (loginUser != null) {

			Long unitId = null;

			PermissionParam[] params = permission.param();
			if (params != null && params.length > 0) {
				PermissionParam param = params[0];
				Map<String, Object> requestParam = current.getParam(param.type());
				if (MapUtil.isNotEmpty(requestParam)) {
					if (Objects.nonNull(requestParam.get(param.name()))) {
						unitId = Convert.toLong(requestParam.get(param.name()));
					}
				}
			}

			if (unitId == null) {
				throw new BusinessException(ResultEnum.FORBIDDEN);
			}

			if (UserType.OWNER_UNIT.code().equalsIgnoreCase(loginUser.getType())) {
				return ownerUnitService.checkOwnerUnitManager(unitId, loginUser.getUserId());
			} else if (UserType.VISITOR.code().equalsIgnoreCase(loginUser.getType())) {
				return unitId.equals(loginUser.getUnitId());
			} else if (UserType.GRADMAN.code().equalsIgnoreCase(loginUser.getType())) {
				return ownerUnitService.checkOwnerUnitGridman(unitId, loginUser.getUserId());
			}
		}

		return false;
	}

}

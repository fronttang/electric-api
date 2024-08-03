package com.rosenzest.electric.owner.executor;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.rosenzest.base.LoginUser;
import com.rosenzest.electric.enums.UserType;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.permission.IPermissionExecutor;

@Component
public class OwnerUnitPermissionExecutor implements IPermissionExecutor {

	@Override
	public boolean execute(Permission permission, JoinPoint joinPoint) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();
		if (loginUser != null) {
			return UserType.OWNER_UNIT.code().equalsIgnoreCase(loginUser.getType());
		}

		return false;
	}

}

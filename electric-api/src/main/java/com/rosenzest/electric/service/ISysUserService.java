package com.rosenzest.electric.service;

import com.rosenzest.electric.dto.LoginDto;
import com.rosenzest.electric.entity.SysUser;
import com.rosenzest.electric.vo.LoginVo;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface ISysUserService extends IModelBaseService<SysUser> {

	/**
	 * 用户登录
	 * 
	 * @param login
	 * @return
	 */
	LoginVo login(LoginDto login);

	SysUser getByUserName(String userName);

}

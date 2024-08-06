package com.rosenzest.electric.service.impl;

import com.rosenzest.electric.entity.SysUser;
import com.rosenzest.electric.mapper.SysUserMapper;
import com.rosenzest.electric.service.ISysUserService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-08-05
 */
@Service
public class SysUserServiceImpl extends ModelBaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}

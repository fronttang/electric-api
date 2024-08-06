package com.rosenzest.electric.mapper;

import org.apache.ibatis.annotations.Param;

import com.rosenzest.electric.entity.SysUser;
import com.rosenzest.electric.miniapp.vo.AreaUserInfoVo;
import com.rosenzest.model.base.mapper.ModelBaseMapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
public interface SysUserMapper extends ModelBaseMapper<SysUser> {

	AreaUserInfoVo getAreaUserInfo(@Param("projectId") Long userId, @Param("projectId") Long projectId);

}

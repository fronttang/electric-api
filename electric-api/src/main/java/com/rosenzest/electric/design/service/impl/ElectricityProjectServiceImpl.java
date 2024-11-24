package com.rosenzest.electric.design.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosenzest.base.LoginUser;
import com.rosenzest.electric.design.dto.ElectricityProjectQuery;
import com.rosenzest.electric.design.entity.ElectricityProject;
import com.rosenzest.electric.design.mapper.ElectricityProjectMapper;
import com.rosenzest.electric.design.service.IElectricityProjectService;
import com.rosenzest.electric.enums.AccountType;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 电力设计项目 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Service
public class ElectricityProjectServiceImpl extends ModelBaseServiceImpl<ElectricityProjectMapper, ElectricityProject>
		implements IElectricityProjectService {

	@SuppressWarnings("unchecked")
	@Override
	public Page<ElectricityProject> queryList(ElectricityProjectQuery query) {

		IRequestContext current = RequestContextHolder.getCurrent();
		LoginUser loginUser = current.getLoginUser();

		LambdaQueryWrapper<ElectricityProject> queryWrapper = new LambdaQueryWrapper<ElectricityProject>();

		if (StrUtil.isNotBlank(query.getKeyword())) {
			queryWrapper.like(ElectricityProject::getName, query.getKeyword());
		}

		// 操作员只能看自己的数据
		if (AccountType.OPERATOR.code().equalsIgnoreCase(loginUser.getAccountType())) {
			queryWrapper.eq(ElectricityProject::getCreateBy, loginUser.getUserId());
		}

		queryWrapper.orderByDesc(ElectricityProject::getId);

		Page<ElectricityProject> page = this.page(new Page<ElectricityProject>(query.getPage(), query.getPageSize()),
				queryWrapper);

		return page;
	}

}

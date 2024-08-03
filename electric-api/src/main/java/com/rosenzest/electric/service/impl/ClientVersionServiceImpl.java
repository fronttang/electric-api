package com.rosenzest.electric.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rosenzest.electric.entity.ClientVersion;
import com.rosenzest.electric.mapper.ClientVersionMapper;
import com.rosenzest.electric.service.IClientVersionService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * <p>
 * 版本更新 服务实现类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-31
 */
@Service
public class ClientVersionServiceImpl extends ModelBaseServiceImpl<ClientVersionMapper, ClientVersion>
		implements IClientVersionService {

	@SuppressWarnings("unchecked")
	@Override
	public ClientVersion getLatestClientVersion(String client) {
		LambdaQueryWrapper<ClientVersion> queryWrapper = new LambdaQueryWrapper<ClientVersion>();
		queryWrapper.eq(ClientVersion::getClient, client);
		queryWrapper.orderByDesc(ClientVersion::getId);
		queryWrapper.last(" LIMIT 1 ");
		return this.baseMapper.selectOne(queryWrapper);
	}

}

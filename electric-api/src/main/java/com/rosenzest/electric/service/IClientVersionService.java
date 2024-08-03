package com.rosenzest.electric.service;

import com.rosenzest.electric.entity.ClientVersion;
import com.rosenzest.model.base.service.IModelBaseService;

/**
 * <p>
 * 版本更新 服务类
 * </p>
 *
 * @author fronttang
 * @since 2024-07-31
 */
public interface IClientVersionService extends IModelBaseService<ClientVersion> {

	ClientVersion getLatestClientVersion(String client);

}

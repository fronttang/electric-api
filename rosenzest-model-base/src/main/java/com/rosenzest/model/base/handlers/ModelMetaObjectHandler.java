package com.rosenzest.model.base.handlers;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.rosenzest.base.constant.SystemConstants;

/**
 * 自动填充策略
 * 
 * @author fronttang
 * @date 2021/07/26
 */
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject, "CREATE_TIME", Date.class, new Date());
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());

        this.strictInsertFill(metaObject, "CREATE_BY", String.class, SystemConstants.SYSTEM_OPERATOR);
        this.strictInsertFill(metaObject, "createBy", String.class, SystemConstants.SYSTEM_OPERATOR);

        this.strictInsertFill(metaObject, "CRE_DTE", Date.class, new Date());
        this.strictInsertFill(metaObject, "creDte", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "UPDATE_TIME", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());

        this.strictUpdateFill(metaObject, "UPDATE_BY", String.class, SystemConstants.SYSTEM_OPERATOR);
        this.strictUpdateFill(metaObject, "updateBy", String.class, SystemConstants.SYSTEM_OPERATOR);

        this.strictUpdateFill(metaObject, "UPD_DTE", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updDte", Date.class, new Date());
    }

}

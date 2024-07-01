package com.rosenzest.model.base.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ActiveRecord 模式 CRUD
 * <p>
 * 必须存在对应的原始mapper并继承baseMapper并且可以使用的前提下 才能使用此 AR 模式 !!!
 * </p>
 * 
 * @author fronttang
 * @date 2021/06/30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseModel<T extends Model<?>> extends Model<T> implements Serializable {

    /**
    *
    */
    private static final long serialVersionUID = -4103293573147179014L;

}

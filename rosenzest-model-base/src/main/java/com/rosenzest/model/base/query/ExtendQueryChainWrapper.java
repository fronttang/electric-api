package com.rosenzest.model.base.query;

import java.util.function.Predicate;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;

/**
 * 扩展 Mybatis-Plus query
 * 
 * @author fronttang
 * @date 2021/07/20
 */
public class ExtendQueryChainWrapper<T>
    extends AbstractChainWrapper<T, String, ExtendQueryChainWrapper<T>, QueryWrapper<T>>
    implements ExtendChainQuery<T>, Query<ExtendQueryChainWrapper<T>, T, String> {

    /**
     *
     */
    private static final long serialVersionUID = -8055278493471600026L;

    private final BaseMapper<T> baseMapper;

    public ExtendQueryChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        super.wrapperChildren = new QueryWrapper<>();
    }

    @Override
    public ExtendQueryChainWrapper<T> select(String... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public ExtendQueryChainWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    @Override
    public BaseMapper<T> getBaseMapper() {
        return baseMapper;
    }

}

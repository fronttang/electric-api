package com.rosenzest.model.base.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rosenzest.model.base.query.ExtendLambdaQueryChainWrapper;
import com.rosenzest.model.base.query.ExtendQueryChainWrapper;

/**
 * 快捷构造 chain 式调用的工具类
 * 
 * @author fronttang
 * @date 2021/07/20
 */
public final class ExtendChainWrappers {

    private ExtendChainWrappers() {
        // ignore
    }

    /**
     * 链式查询 普通
     *
     * @return QueryWrapper 的包装类
     */
    public static <T> ExtendQueryChainWrapper<T> queryChain(BaseMapper<T> mapper) {
        return new ExtendQueryChainWrapper<>(mapper);
    }

    /**
     * 链式查询 lambda 式
     * <p>
     * 注意：不支持 Kotlin
     * </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    public static <T> ExtendLambdaQueryChainWrapper<T> lambdaQueryChain(BaseMapper<T> mapper) {
        return new ExtendLambdaQueryChainWrapper<>(mapper);
    }
}

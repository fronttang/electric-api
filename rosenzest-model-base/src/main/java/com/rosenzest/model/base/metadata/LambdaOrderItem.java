package com.rosenzest.model.base.metadata;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lambda order 工具类
 * 
 * @author fronttang
 * @date 2021/07/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LambdaOrderItem<T> implements Serializable {

    /**
    *
    */
    private static final long serialVersionUID = 7650191573632452135L;

    /**
     * 需要进行排序的字段
     */
    private SFunction<T, ?> column;
    /**
     * 是否正序排列，默认 true
     */
    private boolean asc = true;

    public static <T> LambdaOrderItem<T> asc(SFunction<T, ?> column) {
        return build(column, true);
    }

    public static <T> LambdaOrderItem<T> desc(SFunction<T, ?> column) {
        return build(column, false);
    }

    @SafeVarargs
    public static <T> List<LambdaOrderItem<T>> ascs(SFunction<T, ?>... columns) {
        return Arrays.stream(columns).map(LambdaOrderItem::asc).collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> List<LambdaOrderItem<T>> descs(SFunction<T, ?>... columns) {
        return Arrays.stream(columns).map(LambdaOrderItem::desc).collect(Collectors.toList());
    }

    private static <T> LambdaOrderItem<T> build(SFunction<T, ?> column, boolean asc) {
        return new LambdaOrderItem<T>(column, asc);
    }

}

package com.rosenzest.server.base.cache;

import java.util.Set;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 项目资源的缓存，存储了项目所有的访问url
 * <p>
 * 一般用在过滤器检测请求是否是项目没有的url
 *
 */
public final class ResourceCache {

    private static final Set<String> resourceCaches = CollectionUtil.newHashSet();

    /**
     * 获取所有缓存资源
     */
    public static Set<String> getAllResources() {
        return resourceCaches;
    }

    /**
     * 直接缓存所有资源
     */
    public static void putAllResources(Set<String> resources) {
        resourceCaches.addAll(resources);
    }

}

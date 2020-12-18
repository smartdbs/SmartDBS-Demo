/*
* Copyright © 2016  Zkteco. All rights reserved.
*
* @Package: com.zkteco.timecube.zkcloud.core.utils
* @Author: <a href="zhaoyong.luo@zkteco.com">zhaoyong.luo</a>
* @Date: 2017-06-12 08:31:37
* @Version: v1.0
*/
package com.zkteco.dbs.common.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @ClassName ResourceUtils
 * @Description: 资源文件读取工具类
 * @Author able.lee
 * @Date 2020/11/17 14:08
 * @Since v1.0.0
 */
public class ResourceUtils {

    private static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    /**
     * 从classpath*路径下获取资源,支持通配符
     *
     * @param locationPattern 模式匹配
     * @return 返回匹配到的文件的Resource数组
     */
    public static Resource[] getResources(String locationPattern) throws IOException {
        if (!locationPattern.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
            locationPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + locationPattern;
        }
        return resolver.getResources(locationPattern);
    }

    /**
     * 从classpath路径下获取资源,不支持通配符
     *
     * @param path 路径
     * @return 返回匹配到的文件的resource
     */
    public static Resource getResource(String path) {
        return resolver.getResource(path);
    }

}

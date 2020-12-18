/**
 * Class Name: ModelUtils
 * Project Name: biocloud2-dev
 * Copyright © 1985-2019 ZKTeco Inc.All right reserved.
 **/
package com.zkteco.dbs.common.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * ModelUtils
 *
 * @author sheldon.wu
 * @date 2020/11/23 18:29
 * @since 1.0.0
 */

public class ModelUtils {

    /**
     * 实体属性拷贝
     *
     * @param source
     * @param target
     */
    public static <T> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 提供忽略Id复制
     *
     * @param source
     * @param target
     * @return T
     * @throws
     * @author sheldon.wu
     * @date 2020/7/14 17:58
     * @since 1.0.0
     */
    public static <T> T copyPropertiesIgnoreId(Object source, T target) {
        BeanUtils.copyProperties(source, target, new String[]{"id"});
        return target;
    }

    /**
     * 提供忽略属性复制
     *
     * @param source
     * @param target
     * @param ignoreProperties
     * @return T
     * @throws
     * @author sheldon.wu
     * @date 2020/7/14 17:58
     * @since 1.0.0
     */
    public static <T> T copyPropertiesWithIgnore(Object source, T target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    /**
     * 拷贝列表，返回新的对象T ，例如model list转 VO list
     *
     * @param list
     * @param classs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> copyListProperties(Collection list, Class<T> classs) throws Exception {
        List<T> targetList = new ArrayList<T>();
        for (Object source : list) {
            targetList.add(copyProperties(source, classs.newInstance()));
        }
        return targetList;
    }

    /**
     * 拷贝列表，返回新的对象T ，例如model list转 VO list
     *
     * @param list
     * @param classs
     * @param ignoreProperties 忽略属性
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> copyListPropertiesWithIgnore(Collection list, Class<T> classs, String... ignoreProperties) throws Exception {
        List<T> targetList = new ArrayList<T>();
        for (Object source : list) {
            targetList.add(copyPropertiesWithIgnore(source, classs.newInstance(), ignoreProperties));
        }
        return targetList;
    }

    /**
     * 获取为空属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source, String... ignoreProperties) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        if (ignoreProperties != null) {
            for (String ig : ignoreProperties) {
                emptyNames.add(ig);
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 拷贝对象属性，忽略null值
     *
     * @param source
     * @param target
     * @return
     */
    public static <T> T copyPropertiesIgnoreNull(Object source, T target) {
        return copyPropertiesWithIgnore(source, target, getNullPropertyNames(source));
    }

    /**
     * 拷贝对象属性，忽略null值,同时可以指定特殊属性忽略
     * @param source
     * @param target
     * @return
     */
    public static <T> T copyPropertiesIgnoreNullWithProperties(Object source, T target, String... ignoreProperties) {
        return copyPropertiesWithIgnore(source, target, getNullPropertyNames(source, ignoreProperties));
    }
}

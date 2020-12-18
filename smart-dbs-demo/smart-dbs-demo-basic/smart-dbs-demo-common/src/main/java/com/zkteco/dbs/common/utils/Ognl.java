package com.zkteco.dbs.common.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Ognl
 * mybatis中使用
 * @author able.lee
 * @date 2020/12/1 17:54
 * @since v1.0.0
 */
@SuppressWarnings("all")
public class Ognl {

    /**
     * test for Map,Collection,String,Array isEmpty
     * @param o
     * @return
     */
   
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if(o == null) {return true;}

        if(o instanceof String) {
            return StringUtils.isEmpty((String)o);
        } else if(o instanceof Collection) {
            if(((Collection)o).isEmpty()){
                return true;
            }
        } else if(o.getClass().isArray()) {
            if(Array.getLength(o) == 0){
                return true;
            }
        } else if(o instanceof Map) {
            if(((Map)o).isEmpty()){
                return true;
            }
        }else {
            return false;
        }

        return false;
    }

    /**
     * test for Map,Collection,String,Array isNotEmpty
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 将字符串转成Map
     * @param args ["key:value", ...]
     * @return {key: value, ...}
     */
    public static Map<String, Object> toMap(Object... args){

        Map<String, Object> map = new HashMap<>();
        for (Object arg : args) {
            String str = String.valueOf(arg);
            String[] split = str.split(":");
            map.put(split[0], split[1]);
        }

        return map;
    }

    /**
     * source是否提供的字符串数组的成员
     * @param source
     * @param values
     * @return
     */
    public static boolean isMember(Object source, Object... values){
        if(null == source || values.length < 1){
            return false;
        }
        String sourceStr = String.valueOf(source);

        for (Object value : values) {
            if (StringUtils.equals(sourceStr, String.valueOf(value))) {
                return true;
            }
        }
        return false;
    }

    private static final String DB_ORACLE = "oracle";
    private static final String DB_MYSQL = "mysql";


    /**
     * 日期转字符串，年月日时分秒
     * @param dataBaseId 数据库类型
     * @param colName 类名
     * @param format 目的格式
     * @return 对应DB类型的sql片段
     */
    public static String toChar(String dataBaseId, String colName){
        return toChar(dataBaseId, colName, "yyyy-mm-dd HH24:MI:SS");
    }

    /**
     * 日期转字符串，支持年月日、年月日时分秒
     * @param dataBaseId 数据库类型
     * @param colName 类名
     * @param format 目的格式，传oracle格式（yyyy-mm-dd hh24:mi:ss）
     * @return 对应DB类型的sql片段
     */
    public static String toChar(String dataBaseId, String colName, String format){

        if (StringUtils.equals(DB_ORACLE, dataBaseId)) {
            return " to_char(" + colName + ", '" + format + "') ";
        }
        if(StringUtils.equals(DB_MYSQL, dataBaseId)){
            format = dateFormatToMysql(format);
            return " date_format(" + colName + ", '" + format + "') ";
        }
        throw new RuntimeException("不支持的数据库类型");
    }

    /**
     * 字符串类型转换
     * @param dataBaseId 数据库类型
     * @param colName 类名
     * @return 对应DB类型的sql片段
     */
    public static String toCharOnly(String dataBaseId, String colName){

        if (StringUtils.equals(DB_ORACLE, dataBaseId)) {
            return " to_char(" + colName + ") ";
        }
        if(StringUtils.equals(DB_MYSQL, dataBaseId)){
            return colName;
        }
        throw new RuntimeException("不支持的数据库类型");
    }

    /**
     * 字符串转日期，年月日时分秒
     * @param dataBaseId 数据库类型
     * @param colName 类名
     * @param format 目的格式
     * @return 对应DB类型的sql片段
     */
    public static String toDate(String dataBaseId, String colName){
        return toDate(dataBaseId, colName, "yyyy-mm-dd HH24:MI:SS");
    }

    /**
     * 字符串转日期，支持年月日、年月日时分秒
     * @param dataBaseId 数据库类型
     * @param colName 类名
     * @param format 目的格式，传oracle格式（yyyy-mm-dd hh24:mi:ss）
     * @return 对应DB类型的sql片段
     */
    public static String toDate(String dataBaseId, String colName, String format){

        if (StringUtils.equals(DB_ORACLE, dataBaseId)) {
            return " to_date('" + colName + "', '" + format + "') ";
        }
        if(StringUtils.equals(DB_MYSQL, dataBaseId)){
            format = dateFormatToMysql(format);
            return " str_to_date('" + colName + "', '" + format + "') ";
        }
        throw new RuntimeException("不支持的数据库类型");
    }

    /**
     * 获取判断值是否为空的函数名称
     * @param dataBaseId
     * @return
     */
    public static String nvl(String dataBaseId){

        if (StringUtils.equals(DB_ORACLE, dataBaseId)) {
            return "nvl";
        }
        if (StringUtils.equals(DB_MYSQL, dataBaseId)) {
            return "IFNULL";
        }
        throw new RuntimeException( "不支持的数据库类型");
    }
    //=========================================== private =======================================
    /**
     * 转换日期格式转化MySql方式
     * @param format
     * @return
     */
    private static String dateFormatToMysql(String format){

        format = format.toUpperCase();
        //将日期和小时之间的分隔符统一转为空格
        format = format.replaceAll("DD(.*?)HH", "DD HH");

        format = format.replaceAll("YYYY","%Y")
                .replaceAll("MM", "%m")
                .replaceAll("DD", "%d")
                .replaceAll("HH24", "%H")
                .replaceAll("MI", "%i")
                .replaceAll("SS", "%s")
                .replaceAll(",", " "); //若是逗号分隔，则转为空格
        return format;
    }

    /**
     * 字符列表转成 in语法的字符串
     * @param list id列表，如单位id列表
     * @return in语法的字符串
     */
    public static String toInStr(List<String> list){
        return Optional.ofNullable(list)
                .filter(CollectionUtils::isNotEmpty)
                .map(obj -> "'"+StringUtils.join(obj,"','")+"'")
                .orElse("'"+StringUtils.EMPTY+"'");
    }
}
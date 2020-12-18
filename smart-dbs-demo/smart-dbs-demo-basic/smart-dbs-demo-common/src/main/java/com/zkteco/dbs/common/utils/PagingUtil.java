package com.zkteco.dbs.common.utils;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zkteco.dbs.common.base.vo.BaseQueryVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * @ClassName PagingUtil
 * @Description: 分页工具类
 * @Author able.lee
 * @Date 2020/11/16 16:09
 * @Since v1.0.0
 */
public class PagingUtil {
    public static final Integer DEFAULT_PAGE_SIZE = 99999;
    public static final Integer DEFAULT_CUR_PAGE = 1;

    /**
     * 获取分页对象——JPA
     * @param curPage 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    public static Pageable initPageable(Integer curPage,Integer pageSize){
        //默认取第一页
        if(curPage==null){
            curPage=1;
        }
        //默认取10万行数据
        if(pageSize==null){
            pageSize=99999;
        }
        //分页从0开始，传入的1，表示取第一页，但实际是要从0开始
        return PageRequest.of(curPage-1,pageSize);
    }

    /**
     * 获取分页对象——Mybatis分页
     * @param vo
     * @return mybatis Page
     */
    public static <T> Page<T> initPage(BaseQueryVO vo){
        return initPage(vo.getCurPage(), vo.getPageSize());
    }

    /**
     * 获取分页对象——Mybatis分页
     * @param curPage 当前页数
     * @param pageSize 每页记录数
     * @return mybatis Page
     */
    public static <T> Page<T> initPage(Integer curPage, Integer pageSize){
        return new Page<T>(Optional.ofNullable(curPage).orElse(DEFAULT_CUR_PAGE)
                ,Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE));
    }

    /**
     * 获取分页对象,带排序-mongoDB专用
     * @param curPage 当前页数
     * @param pageSize 每页记录数
     * @param sort 排序
     * @return
     */
    public static Pageable initPageable(Integer curPage,Integer pageSize,Sort sort){
        //默认取第一页
        if(curPage==null){
            curPage=1;
        }
        //默认取10万行数据
        if(pageSize==null){
            pageSize=99999;
        }
        //分页从0开始，传入的1，表示取第一页，但实际是要从0开始
        return PageRequest.of(curPage-1,pageSize,sort);
    }

}

package com.luo.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口 (新接入数据源必须实现该接口)
 * @author lkx
 * @version 1.0.0
 */
public interface DataSource<T> {
    /**
     * 搜索
     * @param searchText 搜索关键词
     * @param pageNum 页码
     * @param pageSize 当前页条数
     * @return 返回分页列表
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}

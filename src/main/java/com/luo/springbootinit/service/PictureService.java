package com.luo.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luo.springbootinit.model.entity.Picture;

/**
 * @author lkx
 * @version 1.0.0
 */
public interface PictureService {

    /**
     * 搜索图片
     *
     * @param searchText 搜索关键词
     * @param pageNum    当前页数
     * @param pageSize   当前页数量
     * @return 返回图片列表
     */
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}

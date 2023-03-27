package com.luo.springbootinit.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luo.springbootinit.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 * @author lkx
 * @version 1.0.0
 */
@Data
public class SearchVO implements Serializable {

    private static final long serialVersionUID = -6063299152491313514L;

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<?> dataList;
}

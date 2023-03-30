package com.luo.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luo.springbootinit.model.dto.picture.PictureQueryRequest;
import com.luo.springbootinit.common.BaseResponse;
import com.luo.springbootinit.common.ErrorCode;
import com.luo.springbootinit.common.ResultUtils;
import com.luo.springbootinit.exception.ThrowUtils;
import com.luo.springbootinit.model.entity.Picture;
import com.luo.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/picture")
@CrossOrigin(origins = {"http://192.168.101.129:8002"})
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest 搜索图片关键词
     * @param request             网络请求
     * @return 返回搜索到的图片
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                         HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long pageSize = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, pageSize);
        return ResultUtils.success(picturePage);
    }


}

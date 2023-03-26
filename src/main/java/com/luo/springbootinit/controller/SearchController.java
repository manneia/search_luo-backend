package com.luo.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luo.springbootinit.model.entity.Picture;
import com.luo.springbootinit.model.vo.PostVO;
import com.luo.springbootinit.model.vo.UserVO;
import com.luo.springbootinit.service.PictureService;
import com.luo.springbootinit.service.PostService;
import com.luo.springbootinit.common.BaseResponse;
import com.luo.springbootinit.common.ErrorCode;
import com.luo.springbootinit.common.ResultUtils;
import com.luo.springbootinit.exception.BusinessException;
import com.luo.springbootinit.model.dto.post.PostQueryRequest;
import com.luo.springbootinit.model.dto.search.SearchRequest;
import com.luo.springbootinit.model.dto.user.UserQueryRequest;
import com.luo.springbootinit.model.vo.SearchVO;
import com.luo.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 图片接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            return userService.listUserVoByPage(userQueryRequest);
        });
        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            return postService.listPostVOByPage(postQueryRequest, request);
        });
        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> pictureService.searchPicture(searchText, 1, 10));
        CompletableFuture.allOf(userTask, postTask, pictureTask).join();
        Page<UserVO> userPage;
        Page<PostVO> postPage;
        Page<Picture> picturePage;
        try {
            userPage = userTask.get();
            postPage = postTask.get();
            picturePage = pictureTask.get();
            SearchVO searchVO = new SearchVO();
            searchVO.setUserList(userPage.getRecords());
            searchVO.setPostList(postPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return ResultUtils.success(searchVO);
        } catch (Exception e) {
            log.error("查询异常", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询失败");
        }

    }

}

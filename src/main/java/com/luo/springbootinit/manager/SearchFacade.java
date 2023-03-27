package com.luo.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luo.springbootinit.common.ErrorCode;
import com.luo.springbootinit.datasource.*;
import com.luo.springbootinit.exception.BusinessException;
import com.luo.springbootinit.exception.ThrowUtils;
import com.luo.springbootinit.model.dto.post.PostQueryRequest;
import com.luo.springbootinit.model.dto.search.SearchRequest;
import com.luo.springbootinit.model.dto.user.UserQueryRequest;
import com.luo.springbootinit.model.entity.Picture;
import com.luo.springbootinit.model.enums.SearchTypeEnum;
import com.luo.springbootinit.model.vo.PostVO;
import com.luo.springbootinit.model.vo.SearchVO;
import com.luo.springbootinit.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * @author lkx
 * @version 1.0.0
 */
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String type = searchRequest.getType();
        String searchText = searchRequest.getSearchText();
        long pageNum = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();

        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        if (searchTypeEnum == null) {
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                return userDataSource.doSearch(searchText, pageNum, pageSize);
            });
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                return postDataSource.doSearch(searchText, pageNum, pageSize);
            });
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> pictureDataSource.doSearch(searchText, 1, 10));
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
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询失败");
            }
        } else {

            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, pageNum, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }

}

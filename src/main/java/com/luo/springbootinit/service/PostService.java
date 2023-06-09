package com.luo.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luo.springbootinit.model.vo.PostVO;
import com.luo.springbootinit.model.dto.post.PostQueryRequest;
import com.luo.springbootinit.model.entity.Post;

import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface PostService extends IService<Post> {

    /**
     * 校验
     *
     * @param post 帖子
     * @param add 是否增加
     */
    void validPost(Post post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest 帖子查询参数
     * @return 返回查询条件
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 从 ES 查询
     *
     * @param postQueryRequest 文章查询参数
     * @return 返回分页的文章
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param post 文章
     * @param request 当前用户
     * @return 返回帖子封装
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param postPage 分页帖子
     * @param request 请求参数
     * @return 返回分页帖子封装
     */
    Page<PostVO> getPostVoPage(Page<Post> postPage, HttpServletRequest request);

    /**
     * 获取文章列表
     * @param postQueryRequest 文章查询参数
     * @param request 请求参数
     * @return 返回文章列表
     */
    Page<PostVO> listPostVoByPage(PostQueryRequest postQueryRequest, HttpServletRequest request);
}

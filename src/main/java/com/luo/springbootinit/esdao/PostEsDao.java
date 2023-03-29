package com.luo.springbootinit.esdao;

import com.luo.springbootinit.model.dto.post.PostEsDTO;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    /**
     * 根据用户id查找文章列表
     * @param userId 用户id
     * @return 返回当前用户对应的文章列表
     */
    List<PostEsDTO> findByUserId(Long userId);

    /**
     * 根据标题获取文章
     * @param title 标题
     * @return 返回当前标题对应的文章列表
     */
    List<PostEsDTO> findByTitle(String title);
}
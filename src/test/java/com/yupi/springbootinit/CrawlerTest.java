package com.yupi.springbootinit;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lkx
 * @version 1.0.0
 */

@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "http://cn.bing.com/images/search?q=小黑子&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements newsHeadlines = doc.select(".iuscp.isv");
        ArrayList<Picture> pictureArrayList = new ArrayList<>();
        for (Element element : newsHeadlines) {
            // 取图片地址
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            // 取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictureArrayList.add(picture);
        }
    }

    @Test
    void testFetchPassage() {
        // 1. 获取数据
        String json = "{\n" +
                "    \"current\": 1,\n" +
                "    \"pageSize\": 8,\n" +
                "    \"sortField\": \"createTime\",\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"category\": \"文章\",\n" +
                "    \"reviewStatus\": 1" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url).body(json).execute().body();
        // 2. json转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        ArrayList<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
        // 3. 数据入库
        boolean b = postService.saveBatch(postList);
        System.out.println(b);
        System.out.println(postList);
    }
}

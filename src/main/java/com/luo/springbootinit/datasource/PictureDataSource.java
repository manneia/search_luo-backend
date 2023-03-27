package com.luo.springbootinit.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luo.springbootinit.common.ErrorCode;
import com.luo.springbootinit.exception.BusinessException;
import com.luo.springbootinit.model.entity.Picture;
import com.luo.springbootinit.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 图片服务实现类
 *
 * @author lkx
 * @version 1.0.0
 */
@Service
public class PictureDataSource implements DataSource<Picture> {

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
        String url = "http://cn.bing.com/images/search?q=" + searchText + "&first=" +
                current;
        Document doc = null;
        ArrayList<Picture> pictureArrayList = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取失败");
        }
        Elements newsHeadlines = doc.select(".iuscp.isv");
        for (Element element : newsHeadlines) {
            if (pictureArrayList.size() > pageSize) {
                break;
            }
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
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictureArrayList);
        return picturePage;
    }
}

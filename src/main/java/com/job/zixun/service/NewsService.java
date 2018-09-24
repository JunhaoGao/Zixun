package com.job.zixun.service;

import com.job.zixun.dao.NewsDAO;
import com.job.zixun.model.News;
import com.job.zixun.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userid, int offset, int limit){
        return newsDAO.selectByUserIdAndOffset(userid,offset,limit);
    }

    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getId();
    }

    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        if(doPos < 0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(doPos+1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt))
            return null;

        String fileName = UUID.randomUUID().toString().replaceAll("-","")+ "." + fileExt;
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        return ToutiaoUtil.TOUTIAO_DOMAIN + "iamge?name=" + fileName;
    }

    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }
    public int updateLikeCount(int id, int count) {
        return newsDAO.updateLikeCount(id, count);
    }

}

package com.job.zixun.service;

import com.job.zixun.dao.NewsDAO;
import com.job.zixun.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userid, int offset, int limit){
        return newsDAO.selectByUserIdAndOffset(userid,offset,limit);
    }
}

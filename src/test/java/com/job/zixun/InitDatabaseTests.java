package com.job.zixun;


import com.job.zixun.dao.NewsDAO;
import com.job.zixun.dao.UserDAO;
import com.job.zixun.model.News;
import com.job.zixun.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;


    @Test
    public void initData(){
        Random random = new Random();
        News news = new News();
        for(int i =0;i<10;i++){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt()));
            user.setName(String.format("USER%d",i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setLink(String.format("http://www.nowcoder.com/link/{%d}.html", i));
            news.setTitle(String.format("Title {%d} ", i));
            news.setUserId(i+1);
            newsDAO.addNews(news);
            System.out.println(news.getId());

            user.setPassword("newpassword");
            userDAO.updatePassword(user);
        }

        Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

    }
}

package com.job.zixun.service;

import com.job.zixun.aspect.LogAspect;
import com.job.zixun.dao.UserDAO;
import com.job.zixun.model.User;
import com.job.zixun.util.ZixunUtil;
import org.apache.commons.lang.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUser(int id){
        return userDAO.selectById(id);
    }
    public Map<String,Object> register(String username, String password){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","User Name is Empty");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("msgpwd","Password is Empty");
            return map;
        }

        User user = userDAO.selectByName(username);
        if(user != null){
            map.put("msgname","User Name is Already Exist");
            return map;
        }

        //register

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt()));
        user.setPassword(ZixunUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        //log in

        return map;
    }
}

package com.job.zixun.service;

import com.job.zixun.dao.LoginTicketDAO;
import com.job.zixun.dao.UserDAO;
import com.job.zixun.model.LoginTicket;
import com.job.zixun.model.User;
import com.job.zixun.util.ToutiaoUtil;
import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

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
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        //log in

        return map;
    }
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msgname", "用户名不存在");
            return map;
        }

        if (!ToutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }
}

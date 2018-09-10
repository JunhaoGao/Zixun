package com.job.zixun.controller;


import com.job.zixun.service.UserService;
import com.job.zixun.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String register(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                        Model model) {
        try{
            Map<String,Object> map = new HashMap<>();
            map = userService.register(username,password);

            if(map.isEmpty()){
                return ZixunUtil.getJSONString(0,"Register Succeed");
            }else{
//                System.out.println(ZixunUtil.getJSONString());
                return ZixunUtil.getJSONString(1,map);
            }
        }catch (Exception e){
            logger.error("Register Error", e.getMessage());
            return ZixunUtil.getJSONString(1,"Register Error");
        }
    }

}

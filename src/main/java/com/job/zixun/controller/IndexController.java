package com.job.zixun.controller;


import com.job.zixun.model.User;
import com.job.zixun.service.ZixunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ZixunService zixunser;

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession session) {

//        return "Hello world  " + session.getAttribute("msg");
        logger.info("lgooglgogloglo");
        return "Hello world  " + zixunser.say();
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "key", defaultValue = "learning") String key,
                          @RequestParam(value = "type", defaultValue = "123") int type) {
        return String.format("{%s},{%d},{%s},{%d}", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"})
    public String news(Model model) {
        model.addAttribute("value1", "vavava");

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("name", "姓名");
        m.put("age", 18);
        m.put("sex", "男");
        model.addAttribute("map", m);

        User u = new User("Jim");
        model.addAttribute("user", u);
        return "/news";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerName = request.getHeaderNames();
        while (headerName.hasMoreElements()) {
            String name = headerName.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        for (Cookie cookie : request.getCookies()) {
            sb.append("Coockie:");
            sb.append(cookie.getName() + cookie.getValue());
            sb.append("<br>");
        }
        return sb.toString();
    }

    @RequestMapping(path = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "coockieId", defaultValue = "a") String coockieId,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);


        return "CoockieId:" + coockieId;
    }

    //    @RequestMapping(path = {"/redirect/{code1}"})
//    public RedirectView redirect(@PathVariable("code1") int code){
//        RedirectView red = new RedirectView("/",true);
//        if (code == 301){
//            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//        }
//        return red;
//    }
//
    @RequestMapping(path = {"/redirect/{code1}"})
    public String redirect(@PathVariable("code1") int code,
                           HttpSession session) {
        session.setAttribute("msg", "Jump from redirect");
        return "redirect:/";
    }

    @RequestMapping(path = {"/admin"})
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false) String key){
        if ("admin1".equals(key)){
            return "Hello Admin";
        }


        throw new IllegalArgumentException("Key Error");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error " + e.getMessage();
    }
}

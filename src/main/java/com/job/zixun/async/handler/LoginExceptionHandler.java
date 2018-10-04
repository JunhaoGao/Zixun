package com.job.zixun.async.handler;

import com.job.zixun.async.EventHandler;
import com.job.zixun.async.EventModel;
import com.job.zixun.async.EventType;
import com.job.zixun.model.Message;
import com.job.zixun.service.MessageService;
import com.job.zixun.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
//        message.setConversationId();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆IP异常");
        // SYSTEM ACCOUNT
        message.setConversationId(String.format("%d_%d", -1, model.getActorId()));
        message.setFromId(-1);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);

        Map<String, Object> map = new HashMap();
        map.put("username", model.getExt("username"));
//        mailSender.sendWithHTMLTemplate(model.getExt("to"), "登陆异常",
//                "mails/welcome.html", map);
        mailSender.sendWithHTMLTemplate("garengao@foxmail.com", "登陆异常",
                "mails/welcome.html", map);
    }
}

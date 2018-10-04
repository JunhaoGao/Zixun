package com.job.zixun.async.handler;

import com.job.zixun.async.EventHandler;
import com.job.zixun.async.EventModel;
import com.job.zixun.async.EventType;
import com.job.zixun.model.Message;
import com.job.zixun.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MessageService messageService;



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
    }
}

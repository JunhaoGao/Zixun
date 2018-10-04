package com.job.zixun.async.handler;

import com.job.zixun.async.EventHandler;
import com.job.zixun.async.EventModel;
import com.job.zixun.async.EventType;
import com.job.zixun.model.Message;
import com.job.zixun.model.User;
import com.job.zixun.service.MessageService;
import com.job.zixun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        System.out.println("Liked");
        Message message = new Message();
        User user = userService.getUser(model.getActorId());
        message.setToId(model.getEntityOwnerId());
        message.setContent("用户" + user.getName() +
                " 赞了你的资讯,http://127.0.0.1:8080/news/"
                + String.valueOf(model.getEntityId()));
        // SYSTEM ACCOUNT
        message.setConversationId(String.format("%d_%d", -1, model.getActorId()));
        message.setFromId(-1);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}

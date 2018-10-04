package com.job.zixun.async;

import com.alibaba.fastjson.JSONObject;
import com.job.zixun.util.JedisAdapter;
import com.job.zixun.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);


    public boolean fireEvent(EventModel model) {
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
//            System.out.println("fireEvent");
            return false;
        } catch (Exception e) {
            logger.error("事件添加异常" + e.getMessage());
            return false;
        }
    }
}

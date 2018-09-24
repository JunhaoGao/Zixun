package com.job.zixun.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool = null;
//        public static void print(int index, Object obj){
//        System.out.println(String.format("{%d}: %s",index,obj.toString()));
//    }
//    public static void main(String[] args){
//        Jedis jedis = new Jedis();
//        jedis.flushAll();
//
//        jedis.setex("hello",15,"world");
//
//    }

    @Override
    public void afterPropertiesSet() {
        pool = new JedisPool("localhost", 6379);

    }

    private Jedis getJedis() {
        return pool.getResource();
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("Jedis异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("Jedis异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public boolean sismember(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.sismember(key,value);
        } catch (Exception e) {
            logger.error("Jedis异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("Jedis异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }
}

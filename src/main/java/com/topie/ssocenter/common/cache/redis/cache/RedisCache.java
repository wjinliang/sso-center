package com.topie.ssocenter.common.cache.redis.cache;

import com.topie.ssocenter.common.cache.BasicCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by cgj on 2016/4/13.
 */
public class RedisCache implements BasicCache<String, Object> {

    @Autowired
    private RedisTemplate<String, Object> template;

    @Override
    public void set(String key, Object value) {
        template.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public void del(String key) {
        template.opsForValue().set(key, null);
    }

    @Override
    public void expire(String key, int seconds) {
        template.expire(key, seconds, TimeUnit.SECONDS);
    }
}

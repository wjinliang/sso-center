package com.topie.ssocenter.common.cache.redis.mq;

public interface Callback {
    public void onMessage(String message);
}

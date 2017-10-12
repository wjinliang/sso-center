package com.topie.ssocenter.common.cache;


/**
 * Created by cgj on 2016/4/13.
 */
public interface BasicCache<K, V> {
    void set(K key, V value);

    V get(K key);

    void del(K key);

    void expire(K key, int seconds);
}

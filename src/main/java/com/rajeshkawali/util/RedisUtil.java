package com.rajeshkawali.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Rajesh_Kawali
 *
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    ObjectMapper objectMapper = null;

    public <T> T getFromCache(String key, Class<T> entity) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o == null) {
                log.warn("Cache miss for key: {}", key);
                return null;
            }
            objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(), entity);
        } catch (Exception e) {
            log.error("Exception ", e);
            return null;
        }
    }

    public void setIntoCache(String key, Object o, Long ttl) {
        try {
            objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }
}

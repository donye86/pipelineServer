package com.rym.module.redis;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Calendar;

/**
 * Jedis实例工厂
 *
 * @author: zqy
 * @date: 2018/4/11 15:43
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
@Setter
public class JedisFactory implements InitializingBean {
    static final Logger logger = LogManager.getLogger(JedisFactory.class);

    JedisPool jedisPool;

    String hosts;

    Integer port;

    String pass;

    Integer maxActive;

    Integer maxIdle;

    Integer maxWait;

    Integer connectionTimeout;

    Integer sessionTimeout;

    Integer dbNum;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("redis hosts: {}", hosts);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        try {
            if (!StringUtils.hasText(pass)) {
                jedisPool = new JedisPool(config, hosts.split(",")[0], port, connectionTimeout);
            } else {
                jedisPool = new JedisPool(config, hosts.split(",")[0], port, connectionTimeout, pass);
            }
        } catch (Exception e) {
            logger.error("Unable to connect the first redis server, error: ", e);
            try {
                if (!StringUtils.hasText(pass)) {
                    jedisPool = new JedisPool(config, hosts.split(",")[1], port, connectionTimeout);
                } else {
                    jedisPool = new JedisPool(config, hosts.split(",")[1], port, connectionTimeout, pass);
                }
            } catch (Exception e2) {
                logger.error("Unable to connect the second redis server, error: ", e2);
                throw e2;
            }
        } finally {
            if (null == jedisPool) {
                throw new BeanInitializationException("Create JedisPool instance failed");
            }
        }
    }

    /**
     * 同步获取Jedis实例
     *
     * @return Jedis
     */
    private Jedis getJedis() throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (null != dbNum) {
                jedis.select(dbNum);
            }
            logger.debug("The redis db num: {}", jedis.getDB());
        } catch (Exception e) {
            logger.error("Get jedis error: ", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            } else {
                throw new IllegalAccessException("Unable to get a datasource instance from JedisPool");
            }
        }
        return jedis;
    }

    /**
     * 设置 String
     *
     * @param key
     * @param value
     */
    public synchronized boolean setString(String key, String value, int timeoutInSeconds) throws Exception {
        value = StringUtils.isEmpty(value) ? "" : value;
        try {
            String replyStatusCode = getJedis().setex(key, timeoutInSeconds, value);
            logger.debug("Jedis setex replyStatusCode: " + replyStatusCode);
        } catch (Exception ex) {
            logger.error("Jedis setString error: ", ex);
            return false;
        }
        return true;
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public synchronized String getString(String key) throws Exception {
        return getJedis().get(key);
    }

    /**
     * 删除String值
     *
     * @param key
     * @return value
     */
    public synchronized Boolean delString(String key) throws Exception {
        Long begin = Calendar.getInstance().getTimeInMillis();
        boolean rd = false;
        long i = getJedis().del(key);
        if (i > 0) {
            Long end = Calendar.getInstance().getTimeInMillis();
            logger.debug("删除缓存key" + key + "结束，总耗时" + (end - begin));
            rd = true;
        } else {
            Long end = Calendar.getInstance().getTimeInMillis();
            logger.debug("删除缓存key" + key + "结束，总耗时" + (end - begin));
            rd = false;
        }
        return rd;
    }
}

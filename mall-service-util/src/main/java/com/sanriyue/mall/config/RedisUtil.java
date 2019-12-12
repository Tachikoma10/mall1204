package com.sanriyue.mall.config;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    /*1.创建JedisPool
      2.获取Jedis
    */
    private JedisPool jedisPool;

    //初始化连接池
    public void initJedisPool(String host,int port,int timeOut,int database){
        //创建配置连接池的参数类
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //排列等待
        jedisPoolConfig.setBlockWhenExhausted(true);
        //等待时间
        jedisPoolConfig.setMaxWaitMillis(10*1000);
        //最大连接数
        jedisPoolConfig.setMaxTotal(100);
        //最少剩余数
        jedisPoolConfig.setMinIdle(10);
        // 设置当用户获取到jedis 时，做自检看当前获取到的jedis 是否可以使用！
        jedisPoolConfig.setTestOnBorrow(true);

        jedisPool=new JedisPool(jedisPoolConfig,host,port,timeOut);
    }

    //获取Jedis
    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}

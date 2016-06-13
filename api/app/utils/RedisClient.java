package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.api.libs.concurrent.Promise;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.util.Pool;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by nfaure on 5/14/16.
 */
@Component
public class RedisClient {

    private static final String INITIAL_AND_FINAL_CURSOR_VALUE = "0";
    private static final int MAX_RESULTS_SIZE_HINT = Integer.MAX_VALUE;


    @Autowired
    private Pool<Jedis> jedisPool;

    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incr(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.get(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public List<String> mget(Set<String> keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.mget(keys.toArray(new String[keys.size()]));
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void set(String key, String value){
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key,value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    public void zadd(String key, String value, Double score){
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.zadd(key, score, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public Set<String> zRangeByScore(String key, Double minScore,Double maxScore){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScore(key, minScore,maxScore);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hgetAll(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void hset(String key,String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset(key, field, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }



    public Set<String> getAllKeysMatching(String pattern) {
        String cursor = INITIAL_AND_FINAL_CURSOR_VALUE;
        Set<String> allKeys = new HashSet<String>();
        Jedis jedis = jedisPool.getResource();
        try {
            do {
                ScanResult<String> scanResult = jedis.scan(cursor, new ScanParams().match(pattern).count(MAX_RESULTS_SIZE_HINT));
                cursor = scanResult.getStringCursor();
                allKeys.addAll(scanResult.getResult());

            } while (!cursor.equals(INITIAL_AND_FINAL_CURSOR_VALUE));
        } finally {
            jedisPool.returnResource(jedis);
        }
        return allKeys;
    }


}

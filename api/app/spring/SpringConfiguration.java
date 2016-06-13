package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import play.Play;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.ConfigurationAccessor;

import java.util.HashSet;
import java.util.Set;

import static utils.ConfigurationAccessor.i;
import static utils.ConfigurationAccessor.s;
import static utils.ConfigurationAccessor.stringList;

/**
 * Created by nfaure on 5/14/16.
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public JedisPool jedis(){
       return new JedisPool(s("redis.host"), i("redis.port"));
    }

    @Bean(name="stopped.codes")
    public Set<String> stoppedCodes(){
        return new HashSet<String>(stringList("stopped.codes"));
    }
}

package services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.libs.F;
import play.mvc.Result;
import play.mvc.Results;
import utils.RedisClient;

import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static play.libs.F.Promise.promise;
import static play.libs.Json.toJson;
import static play.mvc.Results.*;

/**
 * Created by nfaure on 5/17/16.
 */
@Slf4j
@Component
public class UsersService {

    public static final String USER_KEY = "user:%s";
    public static final String OEE_IDS_KEY = "oee:ids";
    @Autowired
    private RedisClient redisClient;



    public F.Promise<Result> findUser(final String userName){
        return promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                try {
                    Map<String,String> user = redisClient.hgetAll(buildKey(userName));
                    if(user==null){
                        return notFound();
                    }
                    return ok(toJson(user));
                }catch(Exception e){
                    log.error("An exception was thrown while find an user in redis",e);
                    return internalServerError();
                }
            }
        });
    }

    private String buildKey(String userName) {
        return format(USER_KEY, userName);
    }

    public F.Promise<Result> create(final JsonNode userAsJson) {
        return promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                try {


                    String id = redisClient.incr(OEE_IDS_KEY).toString();
                    String userName = userAsJson.get("user_name").asText();
                    String name = userAsJson.get("name").asText();
                    String lastName = userAsJson.get("last_name").asText();
                    String password = userAsJson.get("password").asText();
                    String isActive = userAsJson.get("is_active").asText();
                    String userTypeId = userAsJson.get("user_type_id").asText();
                    String userTypeName = userAsJson.get("user_type_name").asText();
                    String key=buildKey(userName);

                    Map<String, String> user = redisClient.hgetAll(key);
                    if(user !=null && !user.isEmpty()){
                        return badRequest("User Already exists");
                    }
                    redisClient.hset(key,"id",id);
                    redisClient.hset(key,"user_name",userName);
                    redisClient.hset(key,"name",name);
                    redisClient.hset(key,"last_name",lastName);
                    redisClient.hset(key,"password",password);
                    redisClient.hset(key,"is_active",isActive);
                    redisClient.hset(key,"user_type_name",userTypeName);
                    redisClient.hset(key,"user_type_id",userTypeId);
                    return created();
                }catch(Exception e){
                    log.error("An exception was thrown while save an user in redis",e);
                    return internalServerError();
                }

            }
        });
    }
}

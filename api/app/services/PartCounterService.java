package services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.libs.F;
import play.mvc.Result;
import utils.RedisClient;

import static play.libs.F.Promise.promise;
import static play.mvc.Results.created;
import static play.mvc.Results.internalServerError;

/**
 * Created by nfaure on 5/30/16.
 */
@Component
@Slf4j
public class PartCounterService {

    public static final String PART_KEY_TEMPLATE = "%s:%s";

    @Autowired
    private RedisClient redisClient;

    public F.Promise<Result> savePart(final JsonNode part, final String partType) {
        return promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                Long tms = part.get("tms").asLong();
                String machine = part.get("machine").asText();
                try {
                    String key = String.format(PART_KEY_TEMPLATE,machine,partType);
                    redisClient.zadd(key,part.toString(),tms.doubleValue());
                    return created();
                } catch (Exception e) {
                    log.error("An exception was thrown while save an event in redis", e);
                    return internalServerError();
                }
            }
        });
    }
}
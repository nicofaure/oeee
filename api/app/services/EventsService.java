package services;

import lombok.extern.slf4j.Slf4j;
import models.Event;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.libs.F;
import play.mvc.Result;
import play.mvc.Results;
import utils.RedisClient;

import java.util.Set;

import static java.lang.String.format;
import static org.joda.time.DateTime.now;
import static play.libs.F.Promise.promise;
import static play.mvc.Results.created;

/**
 * Created by nfaure on 5/14/16.
 */

@Component
@Slf4j
public class EventsService {

    public static final String LAST_EVENT_KEY = "%s:%s:%s:lastevent";
    public static final String EVENT_KEY = "%s:%s:event";
    @Autowired
    private RedisClient redisClient;

    public F.Promise<Result> saveEvent(final Event event, final String eventType) {
        return promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                try {
                    String lastEventKey = buildLastEventKey(event, eventType);
                    String lastEvent = redisClient.get(lastEventKey);
                    long lastEventTms = lastEvent != null ? Long.parseLong(lastEvent) : 0L;
                    if (lastEventTms > 0) {
                        event.setElapsedTime(event.getTms() - lastEventTms);
                    } else {
                        event.setElapsedTime(0L);
                    }
                    String eventAsJson = event.toJson();
                    redisClient.zadd(buildEventKey(event.getName(), eventType), eventAsJson, (double) (event.getTms()));
                    redisClient.set(lastEventKey, event.getTms().toString());
                    return created();
                } catch (Exception e) {
                    log.error("An exception was thrown while save an event in redis", e);
                    return Results.internalServerError();
                }
            }
        });
    }

    private String buildEventKey(String name, String eventType) {
        return format(EVENT_KEY, name, eventType);
    }

    private String buildLastEventKey(Event event, String eventType) {
        return format(LAST_EVENT_KEY, event.getName(), event.getValue(),eventType);
    }


    public F.Promise<Set<String>> getEvents(String name, final Long instantFrom, final Long instantTo, String eventType) {
        final String key = buildEventKey(name, eventType);
        return promise(new F.Function0<Set<String>>() {
            @Override
            public Set<String> apply() throws Throwable {
                System.out.println(instantFrom);
                System.out.println(instantTo);
                System.out.println(instantTo-instantFrom);
                return redisClient.zRangeByScore(key, instantFrom.doubleValue(), instantTo.doubleValue());
            }
        });
    }
}

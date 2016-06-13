package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nfaure on 5/30/16.
 */
@org.springframework.stereotype.Controller
public class SuccessEventController extends EventController{
    @Resource(name="stopped.codes")
    private Set<String> stoppedCodes;

    @Override
    protected String eventSufix() {
        return "success";
    }


    public F.Promise<Result> findStoppedEvents(String name,Long dateFrom, Long dateTo) {
        System.out.println("DATEFROM:" + dateFrom);
        System.out.println("DATETO:" + dateTo);

        return eventsService.getEvents(name, dateFrom, dateTo,eventSufix()).map(new F.Function<Set<String>, Result>() {
            @Override
            public Result apply(Set<String> events) throws Throwable {
                if(events.isEmpty()){
                    return noContent();
                }
                Set<String> stoppedEvents = new HashSet<String>();

                for (String event:events){
                    JsonNode eventAsJson = Json.parse(event);
                    if(stoppedCodes.contains(eventAsJson.get("value").asText())){
                        stoppedEvents.add(event);
                    }
                }


                return ok(play.libs.Json.toJson(stoppedEvents));
            }
        });
    }
}

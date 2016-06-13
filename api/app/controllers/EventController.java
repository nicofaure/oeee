package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import models.Event;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import play.api.libs.json.Json;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import services.EventsService;
import utils.RedisClient;

import java.util.Set;

@Getter
public abstract class EventController extends Controller {
    private static final String DATE_FORMAT = "yyyyMMddHHmm";
    private static DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_FORMAT);

    @Autowired
    protected EventsService eventsService;

    @SneakyThrows
    public F.Promise<Result> addEvent() {
        JsonNode json = request().body().asJson();
        System.out.println("DATA:" + request().body().asJson());
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(json.toString(), Event.class);
        return eventsService.saveEvent(event,eventSufix());
    }


    @SneakyThrows
    public Result addEvents() {
        for (long i=0;i<1;i++){
            eventsService.saveEvent(new Event(i,i,"test","event:test",String.valueOf(i),String.valueOf(i)),eventSufix());
        }
        return ok("done");
    }


    @SneakyThrows
    public F.Promise<Result> getEvents(String name,String dateFrom, String dateTo) {
        System.out.println("DATEFROM:" + dateFrom);
        System.out.println("DATETO:" + dateTo);
        Long instantFrom = buildDate(dateFrom).getMillis();
        Long instantTo = buildDate(dateTo).getMillis();
        System.out.println("INSTANT_FROM:" + instantFrom);
        return eventsService.getEvents(name, instantFrom, instantTo,eventSufix()).map(new F.Function<Set<String>, Result>() {
            @Override
            public Result apply(Set<String> events) throws Throwable {
                if(events.isEmpty()){
                    return noContent();
                }
                System.out.println("LISTO, respuesta enviada");
                return ok(play.libs.Json.toJson(events));
            }
        });
    }

    protected DateTime buildDate(String dateAsString) {
        if (StringUtils.isEmpty(dateAsString)) {
            return DateTime.parse(DateTime.now().toString(dtf), dtf);
        }
        return DateTime.parse(dateAsString, dtf);
    }

    protected abstract String eventSufix();
}

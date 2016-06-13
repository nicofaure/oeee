package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import models.Event;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

import java.util.*;

/**
 * Created by nfaure on 5/30/16.
 */
@org.springframework.stereotype.Controller
public class FailEventController extends EventController{
    @Override
    protected String eventSufix() {
        return "fail";
    }

    @SneakyThrows
    public F.Promise<Result> pareto(String name,Long dateFrom, Long dateTo) {
        return this.getEventsService().getEvents(name,dateFrom,dateTo,eventSufix())
                .map(new F.Function<Set<String>,Result>(){
                    @Override
                    public Result apply(Set<String> events) throws Throwable {
                        Map<String,Long> inneficienceMap = new HashMap<String,Long>();
                        System.out.println(events);
                        for(String eventAsString : events){
                            System.out.println("event:" + eventAsString);
                            Event event = Json.fromJson(Json.parse(eventAsString),Event.class);
                            if(inneficienceMap.containsKey(event.getValue())){
                                Long acum = inneficienceMap.get(event.getValue());
                                inneficienceMap.put(event.getValue(),acum + event.getElapsedTime());
                            }else{
                                inneficienceMap.put(event.getValue(),event.getElapsedTime());
                            }
                        }
                        return Results.ok(Json.toJson(sortByValues(inneficienceMap)));
                    }
                });

    }

    private Map<String, Long> sortByValues(Map<String, Long> unsortMap)
    {

        List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>()
        {
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2)
            {

                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        for (Map.Entry<String, Long> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}

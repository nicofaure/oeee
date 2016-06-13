package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
import play.libs.Json;

/**
 * Created by nfaure on 5/14/16.
 */
@Getter
public class Event {
    private Long tms;
    private Long elapsedTime;
    private String name;
    private String description;
    private String value;
    private String id;

    public Event(){}

    public Event(Long tms,Long elapsedTime,String name,String description,String value, String id){
        this.tms=tms;
        this.elapsedTime=elapsedTime;
        this.name=name;
        this.description=description;
        this.value=value;
        this.id=id;
    }

    public void setElapsedTime(Long elapsedTime){
        this.elapsedTime = elapsedTime;
    }

    public String toJson(){
        return Json.toJson(this).toString();
    }

}

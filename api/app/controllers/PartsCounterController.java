package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import services.PartCounterService;

/**
 * Created by nfaure on 5/30/16.
 */
public abstract class PartsCounterController extends Controller {

    @Autowired
    private PartCounterService partCounterService;

    protected abstract String getPartType();

    @SneakyThrows
    public F.Promise<Result> addEvent() {
        JsonNode part = request().body().asJson();
        System.out.println("DATA:" + part);
        ObjectMapper mapper = new ObjectMapper();

        return partCounterService.savePart(part,getPartType());
    }

}

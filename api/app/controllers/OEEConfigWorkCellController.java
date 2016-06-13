package controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.F;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.OEEConfigWorkCellService;

import java.util.List;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

/**
 * Created by nfaure on 5/21/16.
 */
@Slf4j
//@org.springframework.stereotype.Controller
public class OEEConfigWorkCellController {

    //@Autowired
    //private OEEConfigWorkCellService oeeConfigWorkCellService;

    /*
    public F.Promise<Result> getAll(){
        return oeeConfigWorkCellService.getAll().map(new F.Function<List<String>, Result>() {
            @Override
            public Result apply(List<String> oeeConfigWorkCells) throws Throwable {
                if (oeeConfigWorkCells.isEmpty()) {
                    return Results.noContent();
                }
                return ok(Json.toJson(oeeConfigWorkCells));
            }
        }).recover(new F.Function<Throwable, Result>() {
            @Override
            public Result apply(Throwable t) throws Throwable {
                log.error("An exception was thrown while obtain oeeconfigworkcells", t);
                return internalServerError();
            }
        });
    }

    public F.Promise<Result> create(){
        return oeeConfigWorkCellService.create(request().body().asJson());

    }
    */

}

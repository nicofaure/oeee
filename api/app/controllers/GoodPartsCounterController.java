package controllers;

import play.libs.F;
import play.mvc.Result;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by nfaure on 5/30/16.
 */
@org.springframework.stereotype.Controller
public class GoodPartsCounterController extends PartsCounterController {
    @Override
    protected String getPartType() {
        return "good";
    }

}

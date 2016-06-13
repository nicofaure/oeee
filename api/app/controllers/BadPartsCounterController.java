package controllers;

/**
 * Created by nfaure on 5/30/16.
 */
@org.springframework.stereotype.Controller
public class BadPartsCounterController extends PartsCounterController {
    @Override
    protected String getPartType() {
        return "bad";
    }
}

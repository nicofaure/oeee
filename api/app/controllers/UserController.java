package controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import services.UsersService;

/**
 * Created by nfaure on 5/17/16.
 */
@org.springframework.stereotype.Controller
public class UserController extends Controller {


    @Autowired
    private UsersService usersService;

    public F.Promise<Result> create(){
        return usersService.create(request().body().asJson());
    }

    public F.Promise<Result> findUser(String userid){
        return usersService.findUser(userid);
    }

}

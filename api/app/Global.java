import static utils.ConfigurationAccessor.s;

import java.lang.reflect.Method;
import java.util.Map;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import spring.SpringApplicationContext;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        super.onStart(app);
        Logger.info("Starting up the application...");
        SpringApplicationContext.initialize();
    }

    @Override
    public Action onRequest(Http.Request request, Method actionMethod) {
        System.out.println("before each request..." + request.toString());
        return super.onRequest(request, actionMethod);
    }

    @Override
    public void onStop(Application app) {
        super.onStop(app);
    }

    @Override
    public <C> C getControllerInstance(Class<C> clazz) {
        return SpringApplicationContext.getBean(clazz);
    }
}
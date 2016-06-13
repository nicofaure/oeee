package spring;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Created by nfaure on 5/14/16.
 */

public class SpringApplicationContext {

    private SpringApplicationContext() {
    }

    private static AnnotationConfigApplicationContext ctx;

    public static void initialize() {
        try {
            ctx = new AnnotationConfigApplicationContext();
            ctx.scan("controllers", "services", "utils","spring");
            ctx.refresh();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T getBean(Class<T> beanClass) {
        return ctx.getBean(beanClass);
    }

    public static <T> Map<String, T> getBeans(Class<T> beanClass) {
        return ctx.getBeansOfType(beanClass);
    }

    public static <T> T getBeanNamed(String beanName, Class<T> beanClass) {
        return ctx.getBean(beanName, beanClass);
    }

    public static void close() {
        if (ctx != null) {
            ctx.close();
            ctx.destroy();
            ctx = null;
        }
    }
}
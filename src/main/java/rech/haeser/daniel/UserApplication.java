package rech.haeser.daniel;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import rech.haeser.daniel.service.ResourcePath;

@ApplicationPath(UserApplication.VERSION)
public class UserApplication extends Application {

    public static final String VERSION = "v1";

    public UserApplication() {

        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle(UserApplication.class.getSimpleName());
        beanConfig.setVersion(VERSION);
        beanConfig.setResourcePackage(ResourcePath.class.getPackage().getName());
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }
}

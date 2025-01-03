package base;

import java.lang.reflect.Constructor;

public class PageInstance {
private  final DriverContext driverContext;

    public PageInstance(DriverContext driverContext) {
        this.driverContext = driverContext;
    }

    public <Page> Page As(Class<Page> pageClass){
        Page testPage;
        try{
            Constructor<Page> constructor = pageClass.getConstructor(DriverContext.class);
            testPage = constructor.newInstance(driverContext);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return testPage;
    }


}

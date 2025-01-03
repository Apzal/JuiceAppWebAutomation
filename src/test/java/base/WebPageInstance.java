package base;

import java.lang.reflect.Constructor;

public class WebPageInstance {
private  final WebDriverContext webDriverContext;

    public WebPageInstance(WebDriverContext webDriverContext) {
        this.webDriverContext = webDriverContext;
    }

    public <Page> Page As(Class<Page> pageClass){
        Page testPage;
        try{
            Constructor<Page> constructor = pageClass.getConstructor(WebDriverContext.class);
            testPage = constructor.newInstance(webDriverContext);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return testPage;
    }


}

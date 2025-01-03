package hooks;

import base.DataContext;
import base.DriverContext;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Base64;

public class Hooks {

    private final DriverContext driverContext;
    private final DataContext dataContext;

    static Logger logger = LogManager.getLogger(Hooks.class);
    public Hooks(DriverContext driverContext, DataContext dataContext) {
        this.driverContext = driverContext;
        this.dataContext = dataContext;
    }

    @BeforeAll
    public static void beforeAll(){
        logger.info("---------*************Test Execution Started**********************--------------");
    }

    @Before
    public void before(Scenario scenario){
        logger.info("Execution started for scenario:{}", scenario.getName());
    }


    @After
    public void after(Scenario scenario) {
        dataContext.clearDictionary();
        if (this.driverContext.driver != null) {
            ExtentCucumberAdapter.getCurrentStep().log(getStatus(scenario),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot()).build());
            this.driverContext.driver.quit();
        }
        logger.info("Execution completed for scenario:{},Status:{}", scenario.getName(), getStatus(scenario));
    }

    @AfterAll
    public static void afterAll(){
        logger.info("Test Execution Finished");
        logger.info("---------***********************************--------------");
    }


    public String takeScreenshot() {
        byte[] screenshot = ((TakesScreenshot) driverContext.driver).getScreenshotAs(OutputType.BYTES);
        return Base64.getEncoder().encodeToString(screenshot);
    }

    private Status getStatus(Scenario scenario) {
        return switch (scenario.getStatus()) {
            case PASSED -> Status.PASS;
            case FAILED -> Status.FAIL;
            case SKIPPED -> Status.SKIP;
            case PENDING -> Status.WARNING;
            default -> Status.INFO;
        };
    }
}

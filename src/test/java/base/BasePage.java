package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductPage;
import utilities.ReadPropertyFile;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BasePage extends PageInstance{

    private final ReadPropertyFile readProperty;
    private final DriverContext driverContext;
    private String browser = "";
    private String url = "";
    private int defaultTimeOutInSeconds = 0;

    Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(DriverContext driverContext) {
        super(driverContext);
        this.driverContext = driverContext;
        readProperty = new ReadPropertyFile();
        setConfigurations();
    }

    private void setConfigurations(){
        browser = readProperty.readProperty("browser");
        url = readProperty.readProperty("url");
        defaultTimeOutInSeconds = Integer.parseInt(readProperty.readProperty("defaultTimeOutInSeconds"));
    }

    public void launchBrowser(){
        switch (browser.toLowerCase()){
            case "chrome":
                ChromeOptions options = getChromeOptions();
                this.driverContext.driver= new ChromeDriver(options);
                 break;
            case "edge":
                this.driverContext.driver = new EdgeDriver();
                break;

            case "firefox":
                this.driverContext.driver = new FirefoxDriver();
                break;
            default:
                this.driverContext.driver= new ChromeDriver();

        }

        this.driverContext.driver.manage().window().maximize();
        this.driverContext.driver.navigate().to(url);
        driverContext.currentPage.As(ProductPage.class).dismissWelcomeMessage();
        driverContext.currentPage.As(ProductPage.class).dismissCookieConsent();
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("disable-notifications");


//        options.addArguments("--disable-notifications");
//        options.addArguments("--disable-popup-blocking");
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
//        prefs.put("profile.default_content_setting_values.notifications", 2);
//
//        options.setExperimentalOption("prefs", prefs);
        return options;
    }


    private WebDriverWait loadWaitTimer(int timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(this.driverContext.driver, Duration.ofSeconds(timeOutInSeconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementClickInterceptedException.class);
        wait.ignoring(ElementNotInteractableException.class);
        return wait;
    }


    public void waitForPageLoad(){
        new WebDriverWait(this.driverContext.driver,
                Duration.ofSeconds(defaultTimeOutInSeconds)).until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState"), "complete")
        );

    }
    private void highlightElement(WebElement element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driverContext.driver;
        jsExecutor.executeScript("arguments[0].style.border='2px solid red'", element);
        try{
            Thread.sleep(500);
        }
        catch (InterruptedException e){
            logger.info(e.getMessage());
        }
        jsExecutor.executeScript("arguments[0].style.border='0px solid red'", element);
    }

    public WebElement getElement(By locator, int timeOutInSeconds){
        WebDriverWait wait = loadWaitTimer(timeOutInSeconds);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("Located Element, Locator Used : {}", element);
        highlightElement(element);
        return element;

    }

    public WebElement getElement(By locator){
        return getElement(locator,defaultTimeOutInSeconds);
    }

    public void click(By locator){
        int counter = 3;
        while (counter > 0)
            try {
                getElement(locator, defaultTimeOutInSeconds).click();
                logger.info("Clicked Element, Locator Used : {}", locator);
                break;
            } catch (WebDriverException e) {
                counter--;
                if (counter == 0) {
                    logger.info("Click element failed, Locator Used : {},Exception :{}", locator, e.getMessage());
                    throw new WebDriverException(e.getMessage());
                }
                else{
                    logger.info("Click element failed,Retrying click, Attempt left:{}, Locator Used : {},Exception :{}", counter, locator, e.getMessage());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

    }

    public String getText(By locator){
        return getElement(locator).getText();
    }


    public void scrollIntoView(By by) {
        WebElement element = getElement(by, defaultTimeOutInSeconds);
        ((JavascriptExecutor) this.driverContext.driver).executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled into view for element:{}", element);
    }

    public List<WebElement> getElements(By locator) {
        WebDriverWait wait = loadWaitTimer(defaultTimeOutInSeconds);
        logger.info("Retrieve Multiple Elements, Locator Used : {}", locator);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void setText(By locator,String text){
        getElement(locator).sendKeys(text);
    }

    public void clickElementContainsText(String text){
        click(By.xpath("//*[contains(text(),'"+text+"')]"));
    }

    public WebElement getElementWithText(String text){
        return getElement(By.xpath("//*[contains(text(),'"+text+"')]"));
    }

    public void pause(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean waitForElementToDisAppear(By locator) {
        WebDriverWait wait = loadWaitTimer(defaultTimeOutInSeconds);
        logger.info("Waited for Element to Disappear, Locator Used : {}", locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void navigateToPage(String page){
        this.driverContext.driver.navigate().to(url+"#/"+page);
    }

    public void selectByIndex(By locator, int index){
        Select expiryMonth = new Select(getElement(locator));
        expiryMonth.selectByIndex(index);
    }

    public WebElement getElementViaJS(By locator){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driverContext.driver;
        return (WebElement) jsExecutor.executeScript(
                "return document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;",
                locator
        );
    }

}

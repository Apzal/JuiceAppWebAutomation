package pages;


import base.WebBasePage;
import base.WebDriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ProductPage extends WebBasePage {

    Logger logger = LogManager.getLogger(ProductPage.class);

    By txtWelcomeMessage = By.xpath("//span[text()='Welcome to OWASP Juice Shop!']");
    By btnDismiss = By.xpath("//span[text()='Dismiss']");
    By btnAcceptCookieConsent = By.linkText("Me want it!");
    By txtItemsPerPage = By.xpath("//div[contains(text(),'Items per page')]");
    By paginationLabel = By.className("mat-paginator-range-label");
    By itemPerPageSelection = By.xpath("//*[@role='combobox' and @aria-label='Items per page:']");
    By allSelectionOptionPerPage = By.xpath("//*[@aria-label='Items per page:']//mat-option[@role='option']");
    By allCards = By.xpath("//mat-grid-tile");
    By txtExpandReview = By.xpath("//*[@aria-label='Expand for Reviews']");
    By txtReviewCount = By.xpath("//*[@aria-label='Expand for Reviews']//span[contains(text(),'(')]");
    By btnClose = By.xpath("//button//span[contains(text(),'Close')]");

    By yourBasketCount = By.xpath("//button[@aria-label='Show the shopping cart']//span[contains(@class,'notification')]");

    public ProductPage(WebDriverContext webDriverContext) {
        super(webDriverContext);
    }

    public void dismissWelcomeMessage(){
        getElement(txtWelcomeMessage);
        click(btnDismiss);
        logger.info("Dismissed Welcome Message Popup");
    }

    public void dismissCookieConsent(){
        click(btnAcceptCookieConsent);
        logger.info("Accepted Cookie Consent");
    }

    public void selectMaxItems(){
        scrollIntoView(txtItemsPerPage);
        click(itemPerPageSelection);
        getElements(allSelectionOptionPerPage).getLast().click();
        scrollIntoView(txtItemsPerPage);
    }

    public int getTotalItemsCount(){
        return Integer.parseInt(getText(paginationLabel).split("of")[1].trim());
    }

    public int getAllItemsFromCurrentPage(){
        return getElements(allCards).size();
    }

    public boolean isProductPopUpDisplayed(String text){
        return getElement(By.xpath("//app-product-details//*[contains(text(),'"+text+"')]"))
                .isDisplayed();
    }

    public boolean isProductImageDisplayed(String text){
        return getElement(By.xpath("//app-product-details//img[@alt='"+text+"' and contains(@src,'.jpg')]"))
                .isDisplayed();

    }
    public void expandReviewIfPresent(){
        if(getReviewCount()!=0){
            click(txtExpandReview);
        }
        else{
            logger.info("No review available for the product. Skipping expanding review");
        }
    }

    public int getReviewCount(){
        return Integer.parseInt(getText(txtReviewCount)
                .replace("(","").replace(")","").trim());
    }

    public void closeProductPopUp(){
        scrollIntoView(btnClose);
        click(btnClose);
    }

    public boolean isProductPopUpClosed(){
        return waitForElementToDisAppear(btnClose);
    }
    public void addToBasket(String product){
        click(By.xpath("//*[contains(text(),'"+product+"')]//ancestor::mat-card//*[text()='Add to Basket']"));
    }

    public int getBasketCount(){
        return Integer.parseInt(getElement(yourBasketCount).getText());
    }


}

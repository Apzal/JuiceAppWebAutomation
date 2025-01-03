package steps;

import base.web.WebBasePage;
import base.DataContext;
import base.web.WebDriverContext;
import base.web.WebPageInstance;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import pages.*;
import utilities.RandomGenerator;

import java.util.List;
import java.util.Objects;

public class WebStepDef {

    Logger logger = LogManager.getLogger(WebStepDef.class);

    private final WebDriverContext webDriverContext;
    private final DataContext dataContext;

    private final ProductPage productPage;
    private final WebBasePage webBasePage;
    private final RegistrationPage registrationPage;
    private final LogInPage logInPage;
    private final BasketPage basketPage;
    private final CheckoutPage checkoutPage;

    RandomGenerator generator;

    String email = "", password = "", zipcode = "";

    public WebStepDef(WebDriverContext webDriverContext, DataContext dataContext) {
        this.webDriverContext = webDriverContext;
        this.dataContext = dataContext;
        this.webDriverContext.currentPage = new WebPageInstance(webDriverContext);
        generator = new RandomGenerator();
        webBasePage = webDriverContext.currentPage.As(WebBasePage.class);
        productPage = webDriverContext.currentPage.As(ProductPage.class);
        registrationPage = webDriverContext.currentPage.As(RegistrationPage.class);
        logInPage = webDriverContext.currentPage.As(LogInPage.class);
        basketPage = webDriverContext.currentPage.As(BasketPage.class);
        checkoutPage = webDriverContext.currentPage.As(CheckoutPage.class);
    }

    @Given("I open the application in the browser")
    public void iOpenTheApplicationInTheBrowser() {
        webBasePage.launchBrowser();
    }

    @And("I set the maximum item per page as the max value")
    public void iSetTheMaximumItemPerPageAsTheMaxValue() {
        productPage.selectMaxItems();
    }

    @Then("I should see all items displayed in the Home Page")
    public void iShouldSeeAllItemsDisplayedInTheHomePage() {
        Assert.assertEquals(productPage.getAllItemsFromCurrentPage(),
                productPage.getTotalItemsCount(), "All items not displayed on Home Page");
    }

    @And("I click on the {string}")
    public void iClickOnThe(String product) {
        this.dataContext.addToDictionary("productName", product);
        webBasePage.clickElementContainsText(product);
    }

    @Then("I validate the popup display")
    public void iValidateThePopupDisplay() {
        // to demonstrate the data context using Dependency Injection
        String productName = this.dataContext.readFromDictionary("productName").toString();
        Assert.assertTrue(productPage.isProductPopUpDisplayed(productName),
                "No pop up displayed for " + productName);
    }

    @And("I validate the product has an image")
    public void iValidateTheProductHasAnImage() {
        // to demonstrate the data context using Dependency Injection
        String productName = this.dataContext.readFromDictionary("productName").toString();
        Assert.assertTrue(productPage.isProductImageDisplayed(productName),
                "No image displayed for " + productName);
    }

    @And("I click on the review if exists")
    public void iClickOnTheReviewIfExists() {
        productPage.expandReviewIfPresent();
    }

    @And("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        webBasePage.pause(seconds);
    }

    @Then("I close the product form")
    public void iCloseTheProductForm() {
        productPage.closeProductPopUp();
        Assert.assertTrue(productPage.isProductPopUpClosed(), "Product PopUp window could not be closed");
    }

    @And("I navigate to {string} page")
    public void iNavigateToPage(String page) {
        productPage.navigateToPage(page);
    }

    @And("I leave all fields blank")
    public void iLeaveAllFieldsBlank() {
        registrationPage.leaveAllFieldsBlank();
    }

    @Then("I validate below error messages against each fields")
    public void iValidateBelowErrorMessagesAgainstEachFields(List<String> messages) {
        for (int i = 0; i < messages.size(); i++) {
            Assert.assertEquals(registrationPage.getErrorMessage(i), messages.get(i), "Error message not matched");
        }

    }


    @And("I fill the registration page with valid data")
    public void iFillTheRegistrationPageWithValidData() {
        email = generator.getRandomEmail();
        password = generator.getRandomPassword();
        zipcode = generator.getRandomZipCode();
        registrationPage.fillRegistrationForm(email, password, zipcode);
    }

    @And("I verify all the password advice are fulfilled")
    public void iVerifyAllThePasswordAdviceAreFulfilled() {
        registrationPage.togglePasswordAdvice();
        registrationPage.validatePasswordChecks();
    }

    @And("I click on register and verify the successful message as below")
    public void iClickOnRegisterAndVerifyTheSuccessfulMessageAsBelow(List<String> message) {
        registrationPage.clickRegister();
        Assert.assertTrue(webBasePage.getElementWithText(message.getFirst()).isDisplayed(),
                "No success message displayed");
    }

    @Then("I login using the credentials generated")
    public void iLoginUsingTheCredentialsGenerated() {
        logInPage.login(email,password);
    }

    @And("I add below products to basket")
    public void iAddBelowProductsToBasket(List<String> products) {
        int counter = 0;
        for(String product:products){
            productPage.addToBasket(product);
            Assert.assertTrue(webBasePage.getElementWithText("Placed "+product).isDisplayed(),
                    String.format("No message displayed for %s after adding to basket",product));
            logger.info("Placed {}", product);
            Assert.assertEquals(productPage.getBasketCount(),++counter,"Number of Items in Basket not updated");
            logger.info("Basket updated with count {}", counter);
        }
    }

    @And("I add {int} more {string}")
    public void iAddMore(int count, String product) {
        while (count > 0){
            basketPage.increaseItem(product);
            count--;
        }
        double currentTotal = basketPage.getPrice();
        dataContext.addToDictionary("currentTotal",currentTotal);
        logger.info("Current Total After increasing {},total:{}", product, currentTotal);
    }

    @And("I delete the {string} from the basket")
    public void iDeleteTheFromTheBasket(String product) {
        basketPage.deleteProduct(product);
    }

    @Then("I validate the total price change")
    public void iValidateTheTotalPriceChange() {
        double afterDeletionTotal = basketPage.getPrice();
        Assert.assertNotEquals(afterDeletionTotal,
                dataContext.readFromDictionary("currentTotal"),"Price not changed after removing product");

        logger.info("Current Total After deleting a product,total:{}", afterDeletionTotal);
    }


    @And("I click on checkout")
    public void iClickOnCheckout() {
        webBasePage.clickElementContainsText("Checkout");
    }


    @And("I add a new Address with country as {string} and submit")
    public void iAddANewAddressWithCountryAsAndSubmit(String country) {
        checkoutPage.addNewAddress();
        String name = generator.getRandomName();
        dataContext.addToDictionary("name",name);

        checkoutPage.fillAddressAndSubmit(country,name,generator.getRandomMobileNumber(),
                generator.getRandomZipCode(), generator.getRandomStreetAddress(),
                generator.getRandomCity(), generator.getRandomState());

        checkoutPage.selectAddress(name);
        webBasePage.clickElementContainsText("Continue");
    }

    @And("I select delivery speed as {string}")
    public void iSelectDeliverySpeedAs(String deliverySpeed) {
        checkoutPage.selectDeliverySpeed(deliverySpeed);
        webBasePage.clickElementContainsText("Continue");
    }

    @And("I verify wallet balance as {double}")
    public void iVerifyWalletBalanceAs(double balance) {
        double walletBalance = checkoutPage.getWalletBalance();
        Assert.assertEquals(walletBalance,balance,"Wallet balance is not zero, Current Balance:"+walletBalance);
    }
    
    @And("I add a new card details")
    public void iAddANewCardDetails() {
        webBasePage.clickElementContainsText("Add a credit or debit card");
        String name = dataContext.readFromDictionary("name").toString();
        checkoutPage.fillCardDetails(name,generator.generateRandomNumber(16),0,0);
        checkoutPage.selectCard(name);
        webBasePage.clickElementContainsText("Continue");
    }

    @Then("I complete the purchase")
    public void iCompleteThePurchase() {
        webBasePage.clickElementContainsText("Place your order and pay");
        Assert.assertTrue(webBasePage.getElementWithText("Thank you for your purchase!").isDisplayed(),"Order not placed");
        String[] urlComponent = Objects.requireNonNull(this.webDriverContext.driver.getCurrentUrl()).split("/");
        logger.info("Order id:{}", urlComponent[urlComponent.length - 1]);
    }
}

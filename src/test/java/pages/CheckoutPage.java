package pages;

import base.WebBasePage;
import base.WebDriverContext;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CheckoutPage extends WebBasePage {

    By btnAddNewAddress = By.xpath("//button[@aria-label='Add a new address']");
    By inputCountry = By.xpath("//input[@placeholder='Please provide a country.']");
    By inputName = By.xpath("//input[@placeholder='Please provide a name.']");
    By inputMobileNumber = By.xpath("//input[@placeholder='Please provide a mobile number.']");
    By inputZipCode = By.xpath("//input[@placeholder='Please provide a ZIP code.']");
    By inputAddress = By.xpath("//textarea[@placeholder='Please provide an address.']");
    By inputCity = By.xpath("//input[@placeholder='Please provide a city.']");
    By inputState = By.xpath("//input[@placeholder='Please provide a state.']");
    By btnSubmit = By.id("submitButton");

    By walletBalance = By.xpath("//*[contains(text(),'Wallet Balance')]/following-sibling::span");
    By selectExpiryMonth = By.xpath("//*[contains(text(),'Expiry Month')]/ancestor::div/select");
    By selectExpiryYear = By.xpath("//*[contains(text(),'Expiry Year')]/ancestor::div/select");

    public CheckoutPage(WebDriverContext webDriverContext) {
        super(webDriverContext);
    }

    public void addNewAddress(){
        click(btnAddNewAddress);
    }

    public void fillAddressAndSubmit(String country,String name,String mobileNumber,String zipCode,
                            String address,String city,String state){
        setText(inputCountry,country);
        setText(inputName,name);
        setText(inputMobileNumber,mobileNumber);
        setText(inputZipCode,zipCode);
        setText(inputAddress,address);
        setText(inputCity,city);
        setText(inputState,state);
        click(btnSubmit);
    }

    public void selectAddress(String name){
        click(By.xpath("//*[contains(text(),'"+name+"')]/..//input[@type='radio']/.."));
    }

    public void selectDeliverySpeed(String delivery){
        click(By.xpath("//*[contains(text(),'"+delivery+"')]/..//input/.."));
    }

    public double getWalletBalance(){
        return Double.parseDouble(getText(walletBalance));
    }

    public void fillCardDetails(String name,String cardNumber, int expiryMonthIndex,int expiryYearIndex){
        setText(By.xpath("(//*[@role='region']//input)[1]"),name);
        setText(By.xpath("(//*[@role='region']//input)[2]"),cardNumber);
        selectExpiryMonth(expiryMonthIndex);
        selectExpiryYear(expiryYearIndex);

        click(btnSubmit);
        Assert.assertTrue(getElementWithText("has been saved for your convenience.").isDisplayed(),"Card not saved");
    }

    public void selectCard(String name){
        click(By.xpath("//*[contains(text(),'"+name+"')]/..//input[@type='radio']/.."));
    }

    public void selectExpiryMonth(int index){
       selectByIndex(selectExpiryMonth,index);
    }

    public void selectExpiryYear(int index){
        selectByIndex(selectExpiryYear,index);
    }
}

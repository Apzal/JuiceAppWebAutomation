package pages;

import base.web.WebBasePage;
import base.web.WebDriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.List;

public class RegistrationPage extends WebBasePage {

    Logger logger = LogManager.getLogger(RegistrationPage.class);

    By inputEmail = By.id("emailControl");
    By inputPassword = By.id("passwordControl");
    By inputRepeatPassword = By.id("repeatPasswordControl");
    By inputSecurityAnswer = By.id("securityAnswerControl");
    By selectSecurityQuestion = By.xpath("//*[@aria-label='Selection list for the security question']");
    By optionZipCode = By.xpath("//*[@role='listbox']//span[contains(text(),'ZIP')]");
    By toggleShowPasswordAdvise = By.xpath("//*[@type='checkbox']/..");
    By passwordChecks = By.xpath("//*[@role='img' and text()='done']");
    By btnRegister = By.id("registerButton");

    List<String> allElementsId = List.of("emailControl","passwordControl","repeatPasswordControl",
                                    "securityAnswerControl");

    private static final int PASSWORD_CHECKS = 5;



    public RegistrationPage(WebDriverContext webDriverContext) {
        super(webDriverContext);
    }

    public void leaveAllFieldsBlank(){
        setText(inputEmail,""+Keys.TAB);
        setText(inputPassword,""+Keys.TAB);
        setText(inputRepeatPassword,""+Keys.TAB);
        setText(inputSecurityAnswer,""+Keys.TAB);
        setText(inputSecurityAnswer, ""+Keys.TAB);
        logger.info("Left all fields blank");
    }

    public String getErrorMessage(int fieldIndex){
       return getText(By.xpath("//input[@id='"+allElementsId.get(fieldIndex)+"']/ancestor::mat-form-field//mat-error"))
               .trim();
    }

    public void fillRegistrationForm(String email,String password, String zipCode){
        setText(inputEmail,email);
        setText(inputPassword,password);
        setText(inputRepeatPassword,password);
        click(selectSecurityQuestion);
        click(optionZipCode);
        setText(inputSecurityAnswer,zipCode);
    }

    public void togglePasswordAdvice(){
        click(toggleShowPasswordAdvise);
    }

    public void validatePasswordChecks(){
        Assert.assertEquals(getElements(passwordChecks).size(),PASSWORD_CHECKS,"Not all password advice are fulfilled");
    }

    public void clickRegister(){
        click(btnRegister);
    }
}

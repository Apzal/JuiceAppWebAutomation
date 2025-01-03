package pages;

import base.WebBasePage;
import base.WebDriverContext;
import org.openqa.selenium.By;

public class LogInPage extends WebBasePage {

    By inputEmail = By.id("email");
    By inputPassword = By.id("password");
    By btnLogin = By.id("loginButton");

    public LogInPage(WebDriverContext webDriverContext) {
        super(webDriverContext);
    }

    public void login(String email,String password){
        setText(inputEmail,email);
        setText(inputPassword,password);
        click(btnLogin);
    }
}

package pages;

import base.BasePage;
import base.DriverContext;
import org.openqa.selenium.By;

public class LogInPage extends BasePage {

    By inputEmail = By.id("email");
    By inputPassword = By.id("password");
    By btnLogin = By.id("loginButton");

    public LogInPage(DriverContext driverContext) {
        super(driverContext);
    }

    public void login(String email,String password){
        setText(inputEmail,email);
        setText(inputPassword,password);
        click(btnLogin);
    }
}

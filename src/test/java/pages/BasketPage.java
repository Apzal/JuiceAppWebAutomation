package pages;

import base.BasePage;
import base.DriverContext;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasketPage extends BasePage {

    By price = By.id("price");

    public BasketPage(DriverContext driverContext) {
        super(driverContext);
    }


    public double getPrice(){
        String regex = "\\d+\\.\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getText(price));
        return matcher.find() ? Double.parseDouble(matcher.group()):-1;
    }

    public void increaseItem(String product){
        click(By.xpath("//*[contains(text(),'"+product+"')]/..//button[2]"));
    }

    public void deleteProduct(String product){
        getElements(By.xpath("//*[contains(text(),'"+product+"')]/..//button"))
                .getLast().click();

        waitForPageLoad();
    }
}

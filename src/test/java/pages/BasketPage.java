package pages;

import base.WebBasePage;
import base.WebDriverContext;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasketPage extends WebBasePage {

    By price = By.id("price");

    public BasketPage(WebDriverContext webDriverContext) {
        super(webDriverContext);
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
        waitForMinimumCountOfElements(By.xpath("//*[contains(text(),'"+product+"')]/..//button"),
                3)
                .getLast().click();
        waitForPageLoad();
    }
}

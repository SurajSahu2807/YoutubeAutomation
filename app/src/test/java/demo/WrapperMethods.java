package demo;

import org.openqa.selenium.WebDriver;

public class WrapperMethods {

    public void NavigateToUrl(WebDriver driver , String url){
        if(!driver.getCurrentUrl().equals(url)){
            driver.get(url);
        }
    }
}

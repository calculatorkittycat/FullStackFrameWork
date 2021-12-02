package pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.ConfigReader;
import utility.Driver;

import java.util.List;

public class LoginPage {

    @FindBy(xpath = "//input")
    private WebElement emailInput;

    @FindBy(xpath = "(//input)[2]")
    private WebElement passwordInput;

    @FindBy(xpath = "//button")
    private WebElement loginButton;

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    public void goTo(){
        Driver.getDriver().get(ConfigReader.read("library_url"));
    }

    public void login(String userName, String password){
        this.emailInput.sendKeys(userName);
        this.passwordInput.sendKeys(password);
        this.loginButton.click();
    }



}

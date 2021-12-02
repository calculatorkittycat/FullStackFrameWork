package utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {

    private static WebDriver obj;


    private Driver(){

    }

    /**
     * Return obj with only one WebDriver instance
     * @return
     */

    public static WebDriver getDriver(){
        //read the browser type you want to launch from properties file
        String browserName= ConfigReader.read("browser");

        if(obj==null) {
            switch (browserName.toLowerCase().trim()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    obj = new ChromeDriver();
                    obj.manage().window().maximize();
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    obj = new FirefoxDriver();
                    break;
                case"safari":
                    WebDriverManager.safaridriver().setup();
                    obj=new SafariDriver();
                    break;
                default:
                    obj = null;
                    //System.out.println("Unknown browser type! " + browserName);
            }

            return obj;


        }else {
            // System.out.println("You have it just use existing one");
            return obj;
        }
    }

    /**
     * Quitting the browser and setting the value of
     * WebDriver instance to null because you can re-use already quitted driver
     */
    public static void closeBrowser(){
        /**check if we have WebDriver instance or not
         *basically checking if obj is null or not
         *if not null
         *quit the browser
         *make it null, because once quit it can not be used
         */

        if(obj!=null){
            obj.quit();
            obj = null;
        }

    }




}

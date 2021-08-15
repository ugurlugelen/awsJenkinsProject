package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {

            switch (ConfigReader.getProperty("browser")) {
                case "chrome":
                    //Handle with SSL certificates

                    // Desired capabilites
                    // general chrome profiles
                    DesiredCapabilities desiredCapabilities  = DesiredCapabilities.chrome();
                    desiredCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                    desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);

                    // Below for your local browser
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.merge(desiredCapabilities);

                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class);
                    driver = new SafariDriver();
                    break;

                case "chromeHeadless":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
                    break;
                case "firefoxHeadless":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
                    break;

                case "awsJenkins":
                    System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                    //options.addArguments("start-maximized"); // open Browser in maximized mode
                    options.addArguments("disable-infobars"); // disabling infobars
                    options.addArguments("--disable-extensions"); // disabling extensions
                    //options.addArguments("--disable-gpu"); // applicable to windows os only
                    options.addArguments("--no-sandbox"); // Bypass OS security model
                    options.setHeadless(true);
                    //options.addArguments("disable-gpu");
                    options.addArguments("--no-sandbox");
                    driver = new ChromeDriver(options);

            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }
        public static void closeDriver(){
            if (driver != null) {
                driver.close();
                driver=null;
            }

        }

    }


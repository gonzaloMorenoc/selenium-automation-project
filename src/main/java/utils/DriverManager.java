package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public class DriverManager {
    
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    private DriverManager() {
        // Constructor privado para evitar instanciación
    }
    
    public static void setDriver(String browserName) {
        if (browserName == null) {
            browserName = "chrome";
        }
        
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                
                // Configuración para headless en CI/CD
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    chromeOptions.addArguments("--headless");
                }
                
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--remote-allow-origins=*");
                
                driver.set(new ChromeDriver(chromeOptions));
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                
                if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                    firefoxOptions.addArguments("--headless");
                }
                
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
                
            default:
                throw new IllegalArgumentException("Navegador no soportado: " + browserName);
        }
        
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
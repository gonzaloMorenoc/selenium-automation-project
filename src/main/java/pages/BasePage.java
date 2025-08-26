package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.TestData;

import java.time.Duration;

public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateTo(String url) {
        System.out.println("🌐 Navigating to: " + url);
        driver.get(url);
        
        String currentUrl = driver.getCurrentUrl();
        System.out.println("🔍 Current URL after navigation: " + currentUrl);
        
        TestData.URLs.validateURL(currentUrl, url);
        
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> 
            ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
    
    protected void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
    
    protected void sendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    protected String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }
    
    protected boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}
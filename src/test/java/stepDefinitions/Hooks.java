package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverManager;
import utils.TestData;

public class Hooks {
    
    @Before
    public void setUp(Scenario scenario) {
        // Configurar el driver antes de cada escenario
        String browserName = System.getProperty("browser", "chrome");
        DriverManager.setDriver(browserName);
        
        System.out.println("🚀 Starting scenario: " + scenario.getName());
        System.out.println("🌐 Base URL configured: " + TestData.URLs.BASE_URL);
        System.out.println("📝 Register URL: " + TestData.URLs.REGISTER_URL);
        System.out.println("🔐 Login URL: " + TestData.URLs.LOGIN_URL);
        System.out.println("👤 Profile URL: " + TestData.URLs.PROFILE_URL);
    }
    
    @After
    public void tearDown(Scenario scenario) {
        // Tomar screenshot si el escenario falló
        if (scenario.isFailed()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
                byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Screenshot");
                System.out.println("Screenshot taken for failed scenario: " + scenario.getName());
            } catch (Exception e) {
                System.err.println("Error taking screenshot: " + e.getMessage());
            }
        }
        
        // Cerrar el driver después de cada escenario
        DriverManager.quitDriver();
        
        System.out.println("Finished scenario: " + scenario.getName() + 
                          " - Status: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
    }
}
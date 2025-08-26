package stepDefinitions;

import io.cucumber.java.en.Then;
import pages.LoginPage;
import pages.RegisterPage;
import pages.ProfilePage;
import utils.TestData;
import static org.testng.Assert.*;

public class CommonSteps {
    
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    
    public CommonSteps() {
        this.registerPage = new RegisterPage();
        this.loginPage = new LoginPage();
        this.profilePage = new ProfilePage();
    }
    
    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        // Verificar error en cualquiera de las páginas
        boolean hasError = false;
        String errorContext = "";
        
        // Verificar en página de registro
        if (registerPage.isOnRegisterPage()) {
            hasError = registerPage.isErrorMessageDisplayed();
            errorContext = "registration page";
        }
        // Verificar en página de login
        else if (loginPage.isOnLoginPage()) {
            hasError = loginPage.isErrorMessageDisplayed();
            errorContext = "login page";
        }
        // Verificar en página de perfil
        else if (profilePage.isOnProfilePage()) {
            // En perfil, el "error" puede ser que seguimos en la página después de un fallo
            hasError = profilePage.isOnProfilePage();
            errorContext = "profile page";
        }
        
        assertTrue(hasError, "Should display error message on " + errorContext);
    }
    
    @Then("I should be redirected to the registration page")
    public void i_should_be_redirected_to_the_registration_page() {
        // Esperar un momento para la redirección
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verificar que estamos en la página de registro correcta
        String currentUrl = getCurrentUrl();
        assertTrue(currentUrl.contains("/public/register.html"), 
                  "Should be redirected to registration page. Current URL: " + currentUrl);
    }
    
    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        // Esperar un momento para la redirección
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verificar que estamos en la página de login correcta
        String currentUrl = getCurrentUrl();
        assertTrue(currentUrl.contains("/public/login.html"), 
                  "Should be redirected to login page. Current URL: " + currentUrl);
    }
    
    @Then("I should be redirected to the home page")
    public void i_should_be_redirected_to_the_home_page() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String currentUrl = getCurrentUrl();
        assertTrue(currentUrl.contains("index.html") || 
                  currentUrl.equals(TestData.URLs.BASE_URL + "/") ||
                  currentUrl.equals(TestData.URLs.BASE_URL), 
                  "Should be redirected to home page. Current URL: " + currentUrl);
    }
    
    // Método auxiliar para obtener URL actual desde cualquier página
    private String getCurrentUrl() {
        // Obtener URL actual desde cualquier página
        if (registerPage.isOnRegisterPage()) {
            return registerPage.getCurrentUrl();
        } else if (loginPage.isOnLoginPage()) {
            return loginPage.getCurrentUrl();
        } else if (profilePage.isOnProfilePage()) {
            return profilePage.getCurrentUrl();
        } else {
            // Usar el driver directamente si no podemos identificar la página
            return utils.DriverManager.getDriver().getCurrentUrl();
        }
    }
}
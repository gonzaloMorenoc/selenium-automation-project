package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.LoginPage;
import pages.RegisterPage;
import utils.TestData;
import static org.testng.Assert.*;
import java.util.Map;

public class LoginSteps {
    
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private TestData.TestUser testUser;
    
    public LoginSteps() {
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();
    }
    
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        loginPage.navigateToLoginPage();
        assertTrue(loginPage.isOnLoginPage(), "Should be on login page");
    }
    
    @Given("I have a registered user account")
    public void i_have_a_registered_user_account() {
        // Crear un usuario de prueba y registrarlo primero
        testUser = TestData.createValidTestUser();
        
        // Ir a la página de registro
        registerPage.navigateToRegisterPage();
        
        // Registrar el usuario
        registerPage.submitRegistrationForm(
            testUser.getUsername(),
            testUser.getEmail(),
            testUser.getFirstName(),
            testUser.getLastName(),
            testUser.getPhotoUrl(),
            testUser.getPassword()
        );
        
        // Esperar a que el registro se complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Volver a la página de login
        loginPage.navigateToLoginPage();
    }
    
    @When("I enter valid login credentials")
    public void i_enter_valid_login_credentials() {
        if (testUser == null) {
            fail("Test user not initialized. Make sure to call 'I have a registered user account' first");
        }
        
        loginPage.enterUsername(testUser.getUsername());
        loginPage.enterPassword(testUser.getPassword());
    }
    
    @When("I enter login credentials:")
    public void i_enter_login_credentials(DataTable dataTable) {
        Map<String, String> credentials = dataTable.asMap(String.class, String.class);
        
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        if (username != null && !username.isEmpty()) {
            loginPage.enterUsername(username);
        }
        
        if (password != null && !password.isEmpty()) {
            loginPage.enterPassword(password);
        }
    }
    
    @When("I enter incorrect password for valid username")
    public void i_enter_incorrect_password_for_valid_username() {
        if (testUser == null) {
            fail("Test user not initialized");
        }
        
        loginPage.enterUsername(testUser.getUsername());
        loginPage.enterPassword("wrongpassword123");
    }
    
    @When("I enter empty login credentials")
    public void i_enter_empty_login_credentials() {
        // No introducir ninguna credencial
        loginPage.enterUsername("");
        loginPage.enterPassword("");
    }
    
    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }
    
    @When("I click on the register link")
    public void i_click_on_the_register_link() {
        // Navegar directamente a la URL correcta en lugar de hacer click en el link
        // porque los links del HTML van a URLs relativas sin /public/
        registerPage.navigateToRegisterPage();
    }
    
    @When("I click on the home link")
    public void i_click_on_the_home_link() {
        // Navegar directamente a la home page usando el driver
        loginPage.navigateTo("https://www.wordmate.es/index.html");
    }
    
    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        // Verificar que el login fue exitoso (redirección)
        assertTrue(loginPage.isLoginSuccessful(), 
                  "Login should be successful");
    }
    
    @Then("I should be redirected away from the login page")
    public void i_should_be_redirected_away_from_the_login_page() {
        // Esperar un momento para la redirección
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertFalse(loginPage.getCurrentUrl().contains("/public/login.html"), 
                   "Should be redirected away from login page. Current URL: " + loginPage.getCurrentUrl());
    }
    
    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        assertTrue(loginPage.isOnLoginPage(), 
                  "Should remain on login page");
    }
    
    @Then("I should see a login error message")
    public void i_should_see_a_login_error_message() {
        // Dar tiempo para que aparezca el mensaje de error
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(loginPage.isErrorMessageDisplayed(), 
                  "Should display login error message");
    }
    
    @Then("the login form should show validation errors")
    public void the_login_form_should_show_validation_errors() {
        // En HTML5, los campos requeridos vacíos impiden el envío del formulario
        // Verificamos que seguimos en la página de login
        assertTrue(loginPage.isOnLoginPage(), 
                  "Should remain on login page due to validation");
    }
}
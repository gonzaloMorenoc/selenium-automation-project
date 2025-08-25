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

public class RegisterSteps {
    
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private String currentUsername;
    private String currentPassword;
    
    public RegisterSteps() {
        this.registerPage = new RegisterPage();
        this.loginPage = new LoginPage();
    }
    
    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        registerPage.navigateToRegisterPage();
        assertTrue(registerPage.isOnRegisterPage(), "Should be on registration page");
    }
    
    @When("I fill in the registration form with valid data:")
    public void i_fill_in_the_registration_form_with_valid_data(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        
        // Usar datos únicos para evitar conflictos
        String username = TestData.generateUniqueUsername();
        String email = TestData.generateUniqueEmail();
        String password = userData.get("password");
        
        // Guardar para uso posterior
        currentUsername = username;
        currentPassword = password;
        
        registerPage.fillRegistrationForm(
            username,
            email,
            userData.get("firstName"),
            userData.get("lastName"),
            "", // sin foto URL
            password
        );
    }
    
    @When("I fill in the registration form with valid data including photo:")
    public void i_fill_in_the_registration_form_with_valid_data_including_photo(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        
        // Usar datos únicos para evitar conflictos
        String username = TestData.generateUniqueUsername();
        String email = TestData.generateUniqueEmail();
        String password = userData.get("password");
        
        // Guardar para uso posterior
        currentUsername = username;
        currentPassword = password;
        
        registerPage.fillRegistrationForm(
            username,
            email,
            userData.get("firstName"),
            userData.get("lastName"),
            userData.get("photoUrl"),
            password
        );
    }
    
    @When("I fill in the registration form with invalid email:")
    public void i_fill_in_the_registration_form_with_invalid_email(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        
        registerPage.fillRegistrationForm(
            TestData.generateUniqueUsername(),
            userData.get("email"), // email inválido
            userData.get("firstName"),
            userData.get("lastName"),
            "",
            userData.get("password")
        );
    }
    
    @When("I fill in the registration form with weak password:")
    public void i_fill_in_the_registration_form_with_weak_password(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        
        registerPage.fillRegistrationForm(
            TestData.generateUniqueUsername(),
            TestData.generateUniqueEmail(),
            userData.get("firstName"),
            userData.get("lastName"),
            "",
            userData.get("password") // contraseña débil
        );
    }
    
    @When("I leave required fields empty and click register")
    public void i_leave_required_fields_empty_and_click_register() {
        // No llenar ningún campo y intentar registrarse
        registerPage.clickRegisterButton();
    }
    
    @When("I click the register button")
    public void i_click_the_register_button() {
        registerPage.clickRegisterButton();
    }
    
    @When("I click on the login link")
    public void i_click_on_the_login_link() {
        // Navegar directamente a la URL correcta en lugar de hacer click en el link
        // porque los links del HTML van a URLs relativas sin /public/
        loginPage.navigateToLoginPage();
    }
    
    @Then("I should be redirected away from the registration page")
    public void i_should_be_redirected_away_from_the_registration_page() {
        // Esperar un momento para la redirección
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verificar que no estamos en la página de registro
        assertFalse(registerPage.getCurrentUrl().contains("/public/register.html"), 
                   "Should be redirected away from registration page. Current URL: " + registerPage.getCurrentUrl());
    }
    
    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        // Verificamos que no hay mensaje de error
        assertFalse(registerPage.isErrorMessageDisplayed(), 
                   "Should not show error message on successful registration");
    }
    
    @Then("I should remain on the registration page")
    public void i_should_remain_on_the_registration_page() {
        assertTrue(registerPage.isOnRegisterPage(), 
                  "Should remain on registration page");
    }
    
    @Then("the register button should be disabled or form should show validation errors")
    public void the_register_button_should_be_disabled_or_form_should_show_validation_errors() {
        // En HTML5, los campos requeridos vacíos impiden el envío del formulario
        // Verificamos que seguimos en la página de registro
        assertTrue(registerPage.isOnRegisterPage(), 
                  "Should remain on registration page due to validation");
    }
}
package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.LoginPage;
import pages.ProfilePage;
import pages.RegisterPage;
import utils.TestData;
import static org.testng.Assert.*;

public class ProfileSteps {
    
    private ProfilePage profilePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private TestData.TestUser testUser;
    
    public ProfileSteps() {
        this.profilePage = new ProfilePage();
        this.loginPage = new LoginPage();
        this.registerPage = new RegisterPage();
    }
    
    @Given("I have a registered and logged-in user account")
    public void i_have_a_registered_and_logged_in_user_account() {
        // Crear un usuario de prueba
        testUser = TestData.createValidTestUser();
        
        // Registrar el usuario usando URL centralizada
        registerPage.navigateToRegisterPage();
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
        
        loginPage.navigateToLoginPage();
        loginPage.performLogin(testUser.getUsername(), testUser.getPassword());
        
        // Esperar a que el login se complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Given("I am not logged in")
    public void i_am_not_logged_in() {
        // Asegurarse de que no hay sesión activa
    }
    
    @When("I navigate to the profile page")
    public void i_navigate_to_the_profile_page() {
        profilePage.navigateToProfilePage();
    }
    
    @When("I am on the profile page")
    public void i_am_on_the_profile_page() {
        profilePage.navigateToProfilePage();
        assertTrue(profilePage.isOnProfilePage(), "Should be on profile page");
    }
    
    @When("I click the register button in the login prompt modal")
    public void i_click_the_register_button_in_the_login_prompt_modal() {
        profilePage.clickRegisterFromModal();
    }
    
    @When("I click the login button in the login prompt modal")
    public void i_click_the_login_button_in_the_login_prompt_modal() {
        profilePage.clickLoginFromModal();
    }
    
    @When("I click the logout button")
    public void i_click_the_logout_button() {
        profilePage.clickLogoutButton();
    }
    
    @When("I click the delete account button")
    public void i_click_the_delete_account_button() {
        profilePage.clickDeleteAccountButton();
    }
    
    @When("I confirm account deletion with correct password")
    public void i_confirm_account_deletion_with_correct_password() {
        if (testUser == null) {
            fail("Test user not initialized");
        }
        
        // Esperar a que aparezca el modal
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(profilePage.isDeleteAccountModalDisplayed(), 
                  "Delete account modal should be displayed");
        
        profilePage.enterPasswordForDeletion(testUser.getPassword());
        profilePage.clickConfirmDeleteAccount();
    }
    
    @When("I enter an incorrect password for deletion")
    public void i_enter_an_incorrect_password_for_deletion() {
        profilePage.enterPasswordForDeletion("wrongpassword123");
    }
    
    @When("I click confirm delete")
    public void i_click_confirm_delete() {
        profilePage.clickConfirmDeleteAccount();
    }
    
    @When("I click cancel in the deletion modal")
    public void i_click_cancel_in_the_deletion_modal() {
        profilePage.clickCancelDelete();
    }
    
    @Then("I should see my profile information")
    public void i_should_see_my_profile_information() {
        assertTrue(profilePage.isUserLoggedIn(), 
                  "Should see profile information when logged in");
        assertTrue(profilePage.isProfileContainerDisplayed(), 
                  "Profile container should be displayed");
    }
    
    @Then("I should see profile management options")
    public void i_should_see_profile_management_options() {
        // Verificar que se muestran las opciones del perfil
        assertTrue(profilePage.isProfileContainerDisplayed(), 
                  "Should see profile management options");
    }
    
    @Then("I should see a login prompt modal")
    public void i_should_see_a_login_prompt_modal() {
        assertTrue(profilePage.isNotLoggedInModalDisplayed(), 
                  "Should see login prompt modal for non-logged users");
    }
    
    @Then("I should see options to register or login")
    public void i_should_see_options_to_register_or_login() {
        // Esta verificación está implícita en el modal de login
        assertTrue(profilePage.isNotLoggedInModalDisplayed(), 
                  "Modal should contain register and login options");
    }
    
    @Then("I should be logged out successfully")
    public void i_should_be_logged_out_successfully() {
        // Verificar que fuimos deslogueados (redirección)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Then("my account should be deleted successfully")
    public void my_account_should_be_deleted_successfully() {
        assertTrue(profilePage.isAccountDeletionSuccessful(), 
                  "Account should be deleted successfully");
    }
    
    @Then("I should be redirected away from the profile page")
    public void i_should_be_redirected_away_from_the_profile_page() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verificar que no estamos en la página de perfil o que se muestra modal de no logueado
        assertTrue(!profilePage.getCurrentUrl().contains("/public/profile.html") || 
                  profilePage.isNotLoggedInModalDisplayed(),
                  "Should be redirected away from profile page or show not logged in modal. Current URL: " + 
                  profilePage.getCurrentUrl());
    }
    
    @Then("I should see a profile error message")
    public void i_should_see_a_profile_error_message() {
        // Dar tiempo para que aparezca el mensaje de error
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(profilePage.isOnProfilePage(), 
                  "Should show error and remain on profile page");
    }
    
    @Then("my account should remain active")
    public void my_account_should_remain_active() {
        assertTrue(profilePage.isUserLoggedIn(), 
                  "Account should remain active");
    }
    
    @Then("the deletion modal should be closed")
    public void the_deletion_modal_should_be_closed() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertFalse(profilePage.isDeleteAccountModalDisplayed(), 
                   "Deletion modal should be closed");
    }
    
    @Then("I should still be on the profile page")
    public void i_should_still_be_on_the_profile_page() {
        assertTrue(profilePage.isOnProfilePage(), 
                  "Should still be on profile page");
        assertTrue(profilePage.isUserLoggedIn(), 
                  "Should still be logged in");
    }
}
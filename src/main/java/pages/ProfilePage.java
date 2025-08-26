package pages;

import org.openqa.selenium.By;
import utils.TestData;

public class ProfilePage extends BasePage {
    
    private final By profileContainer = By.id("profile-container");
    private final By profileName = By.id("profile-name");
    private final By profileUsername = By.id("profile-username");
    private final By logoutButton = By.id("logout-btn");
    private final By deleteAccountButton = By.id("delete-account-btn");
    private final By editProfileButton = By.id("edit-profile-btn");
    
    // Modal de confirmación de borrado
    private final By deleteAccountModal = By.id("delete-account-modal");
    private final By deleteAccountPassword = By.id("delete-account-password");
    private final By confirmDeleteAccountButton = By.id("confirm-delete-account-btn");
    private final By cancelDeleteButton = By.xpath("//button[@data-dismiss='modal' and text()='Cancel']");
    
    // Modal de usuario no logueado
    private final By notLoggedInModal = By.id("not-logged-in-modal");
    private final By registerFromModalButton = By.xpath("//a[@href='register.html' and contains(@class, 'btn-primary')]");
    private final By loginFromModalButton = By.xpath("//a[@href='login.html' and contains(@class, 'btn-success')]");
    
    // Navegación usando URLs centralizadas
    public void navigateToProfilePage() {
        navigateTo(TestData.URLs.PROFILE_URL);
    }
    
    // Navegación desde modals usando URLs centralizadas
    public void clickRegisterFromModal() {
        navigateTo(TestData.URLs.REGISTER_URL);
    }
    
    public void clickLoginFromModal() {
        navigateTo(TestData.URLs.LOGIN_URL);
    }
    
    // Verificaciones básicas
    public boolean isOnProfilePage() {
        return getCurrentUrl().contains("/public/profile.html");
    }
    
    public boolean isProfileContainerDisplayed() {
        return isElementDisplayed(profileContainer);
    }
    
    public boolean isNotLoggedInModalDisplayed() {
        return isElementDisplayed(notLoggedInModal);
    }
    
    // Acciones del perfil
    public void clickLogoutButton() {
        clickElement(logoutButton);
    }
    
    public void clickEditProfileButton() {
        clickElement(editProfileButton);
    }
    
    public void clickDeleteAccountButton() {
        clickElement(deleteAccountButton);
    }
    
    // Acciones del modal de borrado de cuenta
    public void enterPasswordForDeletion(String password) {
        waitForElementToBeVisible(deleteAccountPassword);
        sendKeys(deleteAccountPassword, password);
    }
    
    public void clickConfirmDeleteAccount() {
        clickElement(confirmDeleteAccountButton);
    }
    
    public void clickCancelDelete() {
        clickElement(cancelDeleteButton);
    }
    
    public boolean isDeleteAccountModalDisplayed() {
        return isElementDisplayed(deleteAccountModal);
    }
    
    // Método principal para borrar cuenta
    public void deleteAccount(String password) {
        clickDeleteAccountButton();
        waitForElementToBeVisible(deleteAccountModal);
        enterPasswordForDeletion(password);
        clickConfirmDeleteAccount();
    }
    
    // Obtener información del perfil
    public String getProfileName() {
        if (isElementDisplayed(profileName)) {
            return getText(profileName);
        }
        return "";
    }
    
    public String getProfileUsername() {
        if (isElementDisplayed(profileUsername)) {
            return getText(profileUsername);
        }
        return "";
    }
    
    // Verificar si el usuario está logueado
    public boolean isUserLoggedIn() {
        return isProfileContainerDisplayed() && !isNotLoggedInModalDisplayed();
    }
    
    // Verificar si la cuenta fue borrada exitosamente (redirección)
    public boolean isAccountDeletionSuccessful() {
        try {
            Thread.sleep(3000); // Esperar a la redirección
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Si fuimos redirigidos fuera del perfil, asumimos que el borrado fue exitoso
        return !getCurrentUrl().contains("/public/profile.html") || isNotLoggedInModalDisplayed();
    }
}
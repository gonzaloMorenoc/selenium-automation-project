package pages;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage {
    
    // URL
    private static final String REGISTER_URL = "https://www.wordmate.es/public/register.html";
    
    // Localizadores basados en el HTML proporcionado
    private final By usernameField = By.id("reg-username");
    private final By emailField = By.id("reg-email");
    private final By nameField = By.id("reg-name");
    private final By lastNameField = By.id("reg-lastname");
    private final By photoUrlField = By.id("reg-photo");
    private final By passwordField = By.id("reg-password");
    private final By registerButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.id("register-error");
    private final By loginLink = By.xpath("//a[contains(@href, 'login.html')]");
    
    // Navegación
    public void navigateToRegisterPage() {
        navigateTo(REGISTER_URL);
    }
    
    // Acciones de la página
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
    
    public void enterEmail(String email) {
        sendKeys(emailField, email);
    }
    
    public void enterFirstName(String firstName) {
        sendKeys(nameField, firstName);
    }
    
    public void enterLastName(String lastName) {
        sendKeys(lastNameField, lastName);
    }
    
    public void enterPhotoUrl(String photoUrl) {
        sendKeys(photoUrlField, photoUrl);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }
    
    public void clickRegisterButton() {
        clickElement(registerButton);
    }
    
    public void clickLoginLink() {
        clickElement(loginLink);
    }
    
    // Método principal para completar el registro
    public void fillRegistrationForm(String username, String email, String firstName, 
                                   String lastName, String photoUrl, String password) {
        enterUsername(username);
        enterEmail(email);
        enterFirstName(firstName);
        enterLastName(lastName);
        if (photoUrl != null && !photoUrl.isEmpty()) {
            enterPhotoUrl(photoUrl);
        }
        enterPassword(password);
    }
    
    public void submitRegistrationForm(String username, String email, String firstName, 
                                     String lastName, String photoUrl, String password) {
        fillRegistrationForm(username, email, firstName, lastName, photoUrl, password);
        clickRegisterButton();
    }
    
    // Verificaciones
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return getText(errorMessage);
        }
        return "";
    }
    
    public boolean isOnRegisterPage() {
        return getCurrentUrl().contains("register.html");
    }
    
    public boolean isRegisterButtonEnabled() {
        try {
            return driver.findElement(registerButton).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}
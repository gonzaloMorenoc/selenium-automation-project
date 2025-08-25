package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    
    // URL
    private static final String LOGIN_URL = "https://www.wordmate.es/public/login.html";
    
    // Localizadores basados en el HTML proporcionado
    private final By usernameField = By.id("login-username");
    private final By passwordField = By.id("login-password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.id("login-error");
    private final By registerLink = By.xpath("//a[contains(@href, 'register.html')]");
    private final By homeLink = By.xpath("//a[contains(@href, '../index.html')]");
    
    // Navegación
    public void navigateToLoginPage() {
        navigateTo(LOGIN_URL);
    }
    
    // Acciones de la página
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }
    
    public void clickLoginButton() {
        clickElement(loginButton);
    }
    
    public void clickRegisterLink() {
        clickElement(registerLink);
    }
    
    public void clickHomeLink() {
        clickElement(homeLink);
    }
    
    // Método principal para hacer login
    public void performLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
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
    
    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("login.html");
    }
    
    public boolean isLoginButtonEnabled() {
        try {
            return driver.findElement(loginButton).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Verificar si el login fue exitoso (redirección)
    public boolean isLoginSuccessful() {
        // Esperar un momento para la redirección
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Si no estamos en login.html después del login, asumimos que fue exitoso
        return !getCurrentUrl().contains("login.html");
    }
}
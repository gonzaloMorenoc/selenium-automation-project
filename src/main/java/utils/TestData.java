package utils;

import java.util.Random;

public class TestData {
    
    private static final Random random = new Random();
    
    // URLs de la aplicación
    public static class URLs {
        public static final String BASE_URL = "https://www.wordmate.es";
        public static final String REGISTER_URL = BASE_URL + "/public/register.html";
        public static final String LOGIN_URL = BASE_URL + "/public/login.html";
        public static final String PROFILE_URL = BASE_URL + "/public/profile.html";
        public static final String HOME_URL = BASE_URL + "/index.html";
        public static final String GAME_SESSION_URL = BASE_URL + "/public/game-session.html";
        public static final String WORD_LIST_URL = BASE_URL + "/public/word-list.html";

        public static void validateURL(String actualUrl, String expectedUrl) {
        if (!actualUrl.contains("/public/") && expectedUrl.contains("/public/")) {
            throw new AssertionError("URL redirect detected! Expected: " + expectedUrl + " but got: " + actualUrl);
        }
    }
    }
    
    // Datos de usuario válidos
    public static class ValidUser {
        public static final String USERNAME = "testuser" + System.currentTimeMillis();
        public static final String EMAIL = "testuser" + System.currentTimeMillis() + "@example.com";
        public static final String FIRST_NAME = "Test";
        public static final String LAST_NAME = "User";
        public static final String PHOTO_URL = "https://via.placeholder.com/150";
        public static final String PASSWORD = "TestPass123";
    }
    
    // Datos de usuario inválidos para pruebas negativas
    public static class InvalidUser {
        public static final String INVALID_EMAIL = "invalid-email";
        public static final String SHORT_PASSWORD = "123";
        public static final String WEAK_PASSWORD = "password";
        public static final String EMPTY_USERNAME = "";
        public static final String EMPTY_PASSWORD = "";
    }
    
    // Mensajes esperados
    public static class Messages {
        public static final String REGISTRATION_SUCCESS = "Registration successful";
        public static final String LOGIN_SUCCESS = "Login successful";
        public static final String INVALID_CREDENTIALS = "Invalid credentials";
        public static final String ACCOUNT_DELETED = "Account deleted";
    }
    
    // Método para generar datos únicos
    public static String generateUniqueUsername() {
        return "testuser" + System.currentTimeMillis() + random.nextInt(1000);
    }
    
    public static String generateUniqueEmail() {
        return "testuser" + System.currentTimeMillis() + random.nextInt(1000) + "@example.com";
    }
    
    // Método para crear un usuario de prueba completo
    public static TestUser createValidTestUser() {
        return new TestUser(
            generateUniqueUsername(),
            generateUniqueEmail(),
            "Test",
            "User",
            "https://via.placeholder.com/150",
            "TestPass123"
        );
    }
    
    // Clase interna para representar un usuario de prueba
    public static class TestUser {
        private final String username;
        private final String email;
        private final String firstName;
        private final String lastName;
        private final String photoUrl;
        private final String password;
        
        public TestUser(String username, String email, String firstName, 
                       String lastName, String photoUrl, String password) {
            this.username = username;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.photoUrl = photoUrl;
            this.password = password;
        }
        
        // Getters
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getPhotoUrl() { return photoUrl; }
        public String getPassword() { return password; }
        
        @Override
        public String toString() {
            return "TestUser{username='" + username + "', email='" + email + "'}";
        }
    }
}
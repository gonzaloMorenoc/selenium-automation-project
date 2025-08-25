@regression @register
Feature: User Registration
  As a new user
  I want to register for a Wordmate account
  So that I can access the learning platform

  Background:
    Given I am on the registration page

  @positive @smoke
  Scenario: Successful user registration with all required fields
    When I fill in the registration form with valid data:
      | username  | testuser123        |
      | email     | test@example.com   |
      | firstName | John               |
      | lastName  | Doe                |
      | password  | TestPass123        |
    And I click the register button
    Then I should be redirected away from the registration page
    And the registration should be successful

  @positive
  Scenario: Successful user registration with photo URL
    When I fill in the registration form with valid data including photo:
      | username  | testuser456              |
      | email     | test456@example.com      |
      | firstName | Jane                     |
      | lastName  | Smith                    |
      | photoUrl  | https://via.placeholder.com/150 |
      | password  | TestPass456              |
    And I click the register button
    Then I should be redirected away from the registration page
    And the registration should be successful

  @negative
  Scenario: Registration fails with invalid email format
    When I fill in the registration form with invalid email:
      | username  | testuser789    |
      | email     | invalid-email  |
      | firstName | Test           |
      | lastName  | User           |
      | password  | TestPass123    |
    And I click the register button
    Then I should remain on the registration page
    And I should see an error message

  @negative
  Scenario: Registration fails with weak password
    When I fill in the registration form with weak password:
      | username  | testuser101    |
      | email     | test@test.com  |
      | firstName | Test           |
      | lastName  | User           |
      | password  | weak           |
    And I click the register button
    Then I should remain on the registration page

  @negative
  Scenario: Registration fails with empty required fields
    When I leave required fields empty and click register
    Then I should remain on the registration page
    And the register button should be disabled or form should show validation errors

  @navigation
  Scenario: Navigate to login page from registration page
    When I click on the login link
    Then I should be redirected to the login page
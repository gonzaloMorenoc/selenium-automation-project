@regression @login
Feature: User Login
  As a registered user
  I want to login to my Wordmate account
  So that I can access my personalized learning experience

  Background:
    Given I am on the login page

  @positive @smoke
  Scenario: Successful login with valid credentials
    Given I have a registered user account
    When I enter valid login credentials
    And I click the login button
    Then I should be logged in successfully
    And I should be redirected away from the login page

  @negative
  Scenario: Login fails with invalid username
    When I enter login credentials:
      | username | invaliduser    |
      | password | TestPass123    |
    And I click the login button
    Then I should remain on the login page
    And I should see a login error message

  @negative
  Scenario: Login fails with invalid password
    Given I have a registered user account
    When I enter incorrect password for valid username
    And I click the login button
    Then I should remain on the login page
    And I should see a login error message

  @negative
  Scenario: Login fails with empty credentials
    When I enter empty login credentials
    And I click the login button
    Then I should remain on the login page
    And the login form should show validation errors

  @negative
  Scenario: Login fails with empty username
    When I enter login credentials:
      | username |              |
      | password | TestPass123  |
    And I click the login button
    Then I should remain on the login page

  @negative
  Scenario: Login fails with empty password
    When I enter login credentials:
      | username | testuser123  |
      | password |              |
    And I click the login button
    Then I should remain on the login page

  @navigation
  Scenario: Navigate to registration page from login page
    When I click on the register link
    Then I should be redirected to the registration page

  @navigation
  Scenario: Navigate to home page from login page
    When I click on the home link
    Then I should be redirected to the home page
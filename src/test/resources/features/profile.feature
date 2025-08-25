@regression @profile
Feature: User Profile Management
  As a logged-in user
  I want to manage my profile and account
  So that I can control my personal information and account status

  @positive @smoke
  Scenario: Access profile page when logged in
    Given I have a registered and logged-in user account
    When I navigate to the profile page
    Then I should see my profile information
    And I should see profile management options

  @negative
  Scenario: Access profile page when not logged in
    Given I am not logged in
    When I navigate to the profile page
    Then I should see a login prompt modal
    And I should see options to register or login

  @navigation
  Scenario: Navigate to registration from profile login prompt
    Given I am not logged in
    And I am on the profile page
    When I click the register button in the login prompt modal
    Then I should be redirected to the registration page

  @navigation
  Scenario: Navigate to login from profile login prompt
    Given I am not logged in
    And I am on the profile page
    When I click the login button in the login prompt modal
    Then I should be redirected to the login page

  @positive @smoke
  Scenario: Successfully logout from profile page
    Given I have a registered and logged-in user account
    And I am on the profile page
    When I click the logout button
    Then I should be logged out successfully
    And I should be redirected to the home page

  @positive @destructive
  Scenario: Successfully delete user account with correct password
    Given I have a registered and logged-in user account
    And I am on the profile page
    When I click the delete account button
    And I confirm account deletion with correct password
    Then my account should be deleted successfully
    And I should be redirected away from the profile page

  @negative
  Scenario: Account deletion fails with incorrect password
    Given I have a registered and logged-in user account
    And I am on the profile page
    When I click the delete account button
    And I enter an incorrect password for deletion
    And I click confirm delete
    Then I should see an error message
    And my account should remain active

  @negative
  Scenario: Account deletion cancelled by user
    Given I have a registered and logged-in user account
    And I am on the profile page
    When I click the delete account button
    And I click cancel in the deletion modal
    Then the deletion modal should be closed
    And my account should remain active
    And I should still be on the profile page
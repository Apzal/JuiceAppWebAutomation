@web
Feature: E2E Web Automation Tasks for OWASP Juice Shop

  Scenario: Task1-Verify Home Page displays all Items when max item per page is set as max value
    Given I open the application in the browser
    And I set the maximum item per page as the max value
    Then I should see all items displayed in the Home Page

  Scenario: Task2-Verify Product Image and Review Present
    Given I open the application in the browser
    And I click on the "Apple Juice (1000ml)"
    Then I validate the popup display
    And I validate the product has an image
    And I click on the review if exists
    And I wait for 3 seconds
    Then I close the product form

  Scenario: Task3-Verify user registration is successful after form validation
    Given I open the application in the browser
    And I navigate to "register" page
    And I leave all fields blank
    Then I validate below error messages against each fields
      | Please provide an email address.                    |
      | Please provide a password.                          |
      | Please repeat your password.                        |
      | Please provide an answer to your security question. |
    And I fill the registration page with valid data
    And I verify all the password advice are fulfilled
    And I click on register and verify the successful message as below
      | Registration completed successfully. You can now log in. |
    Then I login using the credentials generated

  Scenario: Task4-Verify the checkout flow with payment
    Given I open the application in the browser
    And I navigate to "register" page
    And I fill the registration page with valid data
    And I click on register and verify the successful message as below
      | Registration completed successfully. You can now log in. |
    And I navigate to "login" page
    Then I login using the credentials generated
    And I add below products to basket
    |Apple Juice|
    |Apple Pomace|
    |Banana Juice|
    |Carrot Juice|
    |Eggfruit Juice|
    And I navigate to "basket" page
    And I add 4 more "Apple Juice"
    And I delete the "Apple Juice" from the basket
    Then I validate the total price change
    And I click on checkout
    And I add a new Address with country as "India" and submit
    And I select delivery speed as "One Day Delivery"
    And I verify wallet balance as 0.00
    And I add a new card details
    Then I complete the purchase




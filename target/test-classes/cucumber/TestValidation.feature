@tag
Feature: Check for Login Validation

  @tag1
  Scenario Outline: user login to online web store : positive scenario
    Given I navigate to online store landing page
    When I login with <userEmailID> and <userPassword>
    Then "Incorrect email or password." messgae is displayed
 

    Examples: 
      | userEmailID            | userPassword   |   
      | aarnikj.tomar@gmail.com| RahulAcademy@123|
      | mayajoshi6652@gmail.com| RahulAcademy@12345|
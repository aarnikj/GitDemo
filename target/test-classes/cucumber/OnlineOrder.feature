#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Submit an order on an online store
  This feature file submits a purchase order on an online store

##Background keyword allows us to list pre-requisites that are common to all test scenarios
##it executes before every Scenario (just like @BeforeMethod in TESTNG)
	Background:
	Given I navigate to online store landing page
	
  @Regression
  Scenario Outline: Submiting the order : Positive Test
    Given I login with <userEmailID> and <userPassword>
    When I click on addToCart button for the product <productName>
    And I checkout <productName>, select <country> and click on submit button
    Then "THANK YOU FOR THE ORDER." message is displayed on the OrderConfirmation Page
       
    Examples: 
      | userEmailID            | userPassword      | productName     | country				|
      | aarnikj.tomar@gmail.com| RahulAcademy@123  | ZARA COAT 3     | United States	|
      | mayajoshi6652@gmail.com| RahulAcademy@12345| ADIDAS ORIGINAL | United States	|
    
##when using Scenario Outline, data is taken from the Examples table
##tags are used for grouping scenario(s)/scenarior outline(s)
##Regression is a tag here  
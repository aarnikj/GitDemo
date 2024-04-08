//THIS IS A STEP DEFINITION FILE FOR CUCUMBER FRAMEWORK

//all the test steps are referered from OnlineCartTestHashMap and OnlieCartTest classes

//after we are done writing the code, we just need to run the .feature file and it will execute our test case

//in case we want to save ourselves from writing the code below, we can install TIDY JHERKIN plugin (available in Eclipse) 
//and it will write all the coding for us as soon as we provide it with the .feature file scenarios

package abhatestlabs.StepDefinition;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import abhatestlabs.PageObjectModel.CartPage;
import abhatestlabs.PageObjectModel.CheckoutPage;
import abhatestlabs.PageObjectModel.LandingPage;
import abhatestlabs.PageObjectModel.OrderConfirmationPage;
import abhatestlabs.PageObjectModel.ProductCatalog;
import abhatestlabs.TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImplemetation extends BaseTest{

	/*
	 *	Given I navigate to online store landing page
	  Given I login with <userEmailID> and <userPassword>
    When I click on addToCart button for the product <productName>
     And I checkout <productName> and click on submit button
    Then "THANK YOU FOR THE ORDER." message is displayed on the OrderConfirmation Page
	 */
	
	public LandingPage landingPage; 
	public ProductCatalog productCatalog;
	public OrderConfirmationPage orderConfirmationPage;
	

	@Given("I navigate to online store landing page")
	public void i_navigate_to_online_store_landing_page() throws IOException {
		landingPage = launchingWebPage(); //calling launchingWebPage() method from BaseTest class
	}
	
	
	//for dynamic values we use regular expression to tell Cucumber that no matter what String 
	//value you see inside this, you have to accept it
	//we declare regular expression statements with ^$
	@Given("^I login with (.+) and (.+)$")
	public void i_login_with_userEmailID_and_userPassword(String userEmailID, String userPassword) {
		productCatalog = landingPage.userLogin(userEmailID, userPassword);
	}
	
	
	@When("^I click on addToCart button for the product (.+)$") 
	public void i_click_on_addToCart_button_for_the_product(String productName) {
		productCatalog.getItemList(); 
		productCatalog.addProductToCart(productName);
		
	}
	
	
	@And("^I checkout (.+), select (.+) and click on submit button$")
	public void checkout_and_submit(String productName, String country) {
		CartPage cartPage = productCatalog.goToCartPage(); 
		boolean productMatch = cartPage.verifyProductName(productName);
		Assert.assertTrue(productMatch, "The given poduct does not match any of the products in the cart list");
		CheckoutPage checkoutPage = productCatalog.goToCheckoutPage();
		checkoutPage.selectCountry(country);
		orderConfirmationPage = checkoutPage.placeOrder();
	}
	
	//in case the dynamic variable/parameter is not coming from feature file tables we can use {parameterType}
	//no need for ^$ in this case
	@Then("{string} message is displayed on the OrderConfirmation Page")
	public void order_Confimation_Message(String string) {
		String userOrderStatus = orderConfirmationPage.orderStatusMessage();
		System.out.println(userOrderStatus);
		Assert.assertTrue(userOrderStatus.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
	}
			
	@Then("{string} messgae is displayed") 
	public void login_error_message(String string) {
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
}


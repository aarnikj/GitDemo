//ONLINE SHOPPING CART FLOW USING PAGE OBJECT MODEL

package abhatestlabs.TestCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.IOException;
import abhatestlabs.PageObjectModel.CartPage;
import abhatestlabs.PageObjectModel.CheckoutPage;
import abhatestlabs.PageObjectModel.OrderConfirmationPage;
import abhatestlabs.PageObjectModel.OrderHistoryPage;
import abhatestlabs.PageObjectModel.ProductCatalog;
import abhatestlabs.TestComponents.BaseTest;
import abhatestlabs.TestComponents.Retry;


//every test class should extend the parent BaseTest class
public class OnlineCartTest extends BaseTest{
	public static String countryName = "United States";
	
	//test to submit and verify the order
	//dataProvider helper attribute helps to direct the test to the Data Provider test method
	@Test (dataProvider="getTestData", groups={"SubmitOrder"}, retryAnalyzer=Retry.class)
	public void submitOnlineOrder(String userEmailID, String userPassword, String productName ) throws IOException {
		//we have to pass the data that we got from the Data Provider as parameter(s)
		//STEP 1 - WE FIRST INVOKE THE WEB DRIVER USING BASETEST CLASS and GO TO LANDING PAGE
		
		//STEP 2 - NOW WE LOGIN AND LAND TO PRODCUT CATALAOG
		//logging in and landing to product catalog page
		ProductCatalog productCalatog = landingPage.userLogin(userEmailID, userPassword);
		
		
		
		//STEP 3 - GETTING LIST OF ALL ITEMS IN THE CART and ADDING USER PRODUCT TO THE CART
		productCalatog.getItemList(); //storing all the cart items in a list of web elements
		
		//adding user product
		productCalatog.addProductToCart(productName);
		
		
		
		//STEP 4 - NOW WE GO TO THE CART PAGE and VERIFY THAT THE USER PRODUCT THAT WE ADDED IS PRESENT IN THE CART
		CartPage cartPage = productCalatog.goToCartPage(); //accessing method from productCalatog child class 
		boolean productMatch = cartPage.verifyProductName(productName);
		
		//test validations should not go into page object files (they should only have the code to perform actions)
		//hence we write assertions/test validations inside test cases only
		
		Assert.assertTrue(productMatch, "The given poduct does not match any of the products in the cart list");
		
		//STEP 5. NOW WE CLICK ON CHECKOUT BUTTON AND LAND ON CHECKOUT PAGE
		//ON THE CHECKOUT PAGE WE SELECT OUR COUNTRY AND CLICK ON PLACE ORDER BUTTON
		CheckoutPage checkoutPage = productCalatog.goToCheckoutPage();
		
		checkoutPage.selectCountry(countryName);
		OrderConfirmationPage orderConfirmationPage = checkoutPage.placeOrder();
		
		//STEP 6 - IN THE END WE LAND ON THE ORDER CONFRIMATION PAGE AND VERIFY THAT THE ORDER IS SUCCESSFULLY PLACED
		String userOrderStatus = orderConfirmationPage.orderStatusMessage();
		Assert.assertTrue(userOrderStatus.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	
	/*
	 * EXTRACTING DATA CAN BE DONE USING FOLLOWING WAYS
	 * -- USING ARRAYS
	 * -- USING EXTERNAL DATA SOURCES LIKE EXCEL FILE, JASON FILE
	 * -- USING HASHMAP
	 */
	
	
	//creating a test to validate that our placed order is displayed on the Order History Page
	//this method depends on submitOnlineOrder() method
	@Test (dataProvider="getTestData", dependsOnMethods = {"submitOnlineOrder"})
	public void orderHistory(String userEmailID, String userPassword, String productName) {
		ProductCatalog productCatalog = landingPage.userLogin(userEmailID, userPassword);
		OrderHistoryPage orderHistoryPage = productCatalog.gotoOrderHistoryPage();
		orderHistoryPage.verifyOrderDisplay(productName);
		Assert.assertTrue(true, "Your order is not listed on the orders history");
	}
	
	
	//@DataProvider annotation of TESTNG helps us to drive the data and feed it in our test cases
	//object data type accepts any kind of data type (string, integer, double, float etc)
	//we have provided two sets of data here, data is one pair of {} is one set of data
	@DataProvider
	public Object[][] getTestData() {
		return new Object[][] {{"aarnikj.tomar@gmail.com", "RahulAcademy@123", "ZARA COAT 3"}
		, {"mayajoshi6652@gmail.com", "RahulAcademy@12345", "ADIDAS ORIGINAL"} } ; 
		//here we are creating a two-dimensional array and initializing it at the same time
		
		
		
		
		
		
		
		
	}
}
	
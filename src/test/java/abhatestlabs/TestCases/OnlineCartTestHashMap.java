//ONLINE SHOPPING CART FLOW USING PAGE OBJECT MODEL
//here we use hap mash to pass to the arrays in the Data Provider and then catch it in the test

package abhatestlabs.TestCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.IOException;
import java.util.HashMap;

import abhatestlabs.PageObjectModel.CartPage;
import abhatestlabs.PageObjectModel.CheckoutPage;
import abhatestlabs.PageObjectModel.OrderConfirmationPage;
import abhatestlabs.PageObjectModel.OrderHistoryPage;
import abhatestlabs.PageObjectModel.ProductCatalog;
import abhatestlabs.TestComponents.BaseTest;


//every test class should extend the parent BaseTest class
public class OnlineCartTestHashMap extends BaseTest{
	public static String countryName = "United States";
	
	//test to submit and verify the order
	//dataProvider helper attribute helps to direct the test to the Data Provider test method
	@Test (dataProvider="getTestData", groups={"SubmitOrderHashMap"})
	public void submitOnlineOrder(HashMap<String, String> input ) throws IOException {
		//we have to pass the hashmap values that we got from the Data Provider as parameter(s)
		//the hasmap values will flow in to the input keyword
		//STEP 1 - WE FIRST INVOKE THE WEB DRIVER USING BASETEST CLASS and GO TO LANDING PAGE
		
		//STEP 2 - NOW WE LOGIN AND LAND TO PRODCUT CATALAOG
		//logging in and landing to product catalog page
		ProductCatalog productCalatog = landingPage.userLogin(input.get("userEmailID"), input.get("userPassword"));
		//we use get() method on input key word to extract the value of a key from hashmap at runtime
		
		
		//STEP 3 - GETTING LIST OF ALL ITEMS IN THE CART and ADDING USER PRODUCT TO THE CART
		productCalatog.getItemList(); //storing all the cart items in a list of web elements
		
		//adding user product
		productCalatog.addProductToCart(input.get("productName"));
		
		
		
		//STEP 4 - NOW WE GO TO THE CART PAGE and VERIFY THAT THE USER PRODUCT THAT WE ADDED IS PRESENT IN THE CART
		CartPage cartPage = productCalatog.goToCartPage(); //accessing method from productCalatog child class 
		boolean productMatch = cartPage.verifyProductName(input.get("productName"));
		
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
	//here we are using HASH MAP so that feeding data into ARRAYS looks more organized
	//we need to import java.util.HashMap package for this
	//we are using hashmap of strings since userLogin method in landingPage accepts strings and will throw an error
	@DataProvider
	public Object[][] getTestData() {
		HashMap<String, String> map1 = new HashMap<String, String>(); //creating a hash map variable and feeding key-value pairs into it
		map1.put("userEmailID", "aarnikj.tomar@gmail.com");
		map1.put("userPassword", "RahulAcademy@123");
		map1.put("productName", "ZARA COAT 3");
		//put() method helps us to feed data into hash map, it contains the data in form of key value pairs
		
		HashMap<String, String>  map2 = new HashMap<String, String>();
		map2.put("userEmailID", "mayajoshi6652@gmail.com");
		map2.put("userPassword", "RahulAcademy@12345");
		map2.put("productName", "ADIDAS ORIGINAL");
		
		return new Object[][] {{map1}, {map2}};
		//here we are creating a two-dimensional array and initializing it at the same time
		
		
		
		
		
		
		
		
	}
}
	
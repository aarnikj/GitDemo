//THIS FILE CONTAINS ALL THE TEST VALIDATIONS

package abhatestlabs.TestCases;

import org.testng.annotations.Test;

import java.io.IOException;

import org.testng.Assert;

import abhatestlabs.TestComponents.BaseTest;
import abhatestlabs.TestComponents.Retry;

public class TestValidations extends BaseTest{
	
	//assigning a group to this test method
	//defining retryAnalyzer for this method
	@Test (groups={"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void submitOrder(String userEmailID, String userPassword) throws IOException, InterruptedException{	
		//String productName = "ZARA COAT 3"; //saving our desired product to a variable
		landingPage.userLogin(userEmailID, userPassword);
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
		/*
		 we are not writing any code to launch the browser 
		or initialize the web driver because this will be taken care by testng annotations
		 */
		 
	}
	
	
	
}

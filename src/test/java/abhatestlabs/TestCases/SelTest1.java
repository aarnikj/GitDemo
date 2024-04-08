//WRITING END TO END ECOMMERCE FLOW

//this flow has been designed as per Page Object Model under a different package

/*
 -- src/man/java - is used to store all the reusable utilities, page object files
 -- src/test/java - is used to create test cases
 */

package abhatestlabs.TestCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class SelTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String productName = "ZARA COAT 3"; //saving our desired product to a variable
		
		//STEP 1 - INVOKING WEB BROWSER
		WebDriver driver = (WebDriver) new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		
		
		//STEP 2 - LOGGING IN WITH USER CREDENTIALS
		//we also use explicit wait for "LOGIN SUCCESSFULLY" message to appear on the page
		driver.findElement(By.id("userEmail")).sendKeys("aarnikj.tomar@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("RahulAcademy@123");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#toast-container")));
		
		
		//STEP 3 - ADDING A PROCUCT TO CART
		//here we grab the product list and iterate through it to see our product of choice and then click ADD TO CART button against it
		//we also wait for the products to load on the web page
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".col-lg-4")));
		List<WebElement> itemList = driver.findElements(By.cssSelector(".col-lg-4")); //using css locator with .classname
		
		//now we use java stream to iterate through the List of web elements and filter our desired item
		//here to reach to the product name and get the exact text, we have to execute driver.findElemennt for each product
		//as we use the driver.findElement for a single block, the scope of selenium searching for the web element is limited to the product block (and not to the entire web page)
		//we ask the stream here to return us the first product it encounters with the specified text (since our example cart does not contain multiple products with the same name)
		//we also ask java stream to return null for a no match
		//the WebElement product will store our desired item or null in case of a no match
		WebElement product = itemList.stream().filter(item -> item.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		
		
		//now we have to add the product that we got from above statement to the cart
		//we now product as base to locate the addToCart button
		//cssSelector allows us to traverse to last child using parent:last-of-type
		WebElement addToCartBtn = product.findElement(By.cssSelector(".card-body button:last-of-type"));
		addToCartBtn.click();
		
		
		//STEP 4 - WE HAVE TO MAKE SURE THAT 'PRODUCT ADDED TO CART' MESSAGE IS DISPLAYED AFTER WE CLICK ON 'ADD TO CART' BUTTON
		//here we use explicit wait to ask the web driver to wait until the message is displayed on the screen as it takes some time to appear
		//we also wait until the loading icon we get after click on ADD TO CART button disappears
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container"))); //wait till visibility of 'PRODUCT ADDED TO CART' message
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //wait till invisibility of loading icon
		//we use invisibilityOf and give the full WebElement locator for invisibility cases instead of using invisibilityOfElementLocated(locator) or other invisibility functions because it produces faster results
		//this is not a problem with visibility of element cases
		
		//STEP 5 - WE NOW CLICK ON "CART" ICON TO VERIFY THAT THE SELECTED PRODUCT IS SUCCESSFULLY ADDED
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click(); //clicking on cart button
		
		
		
		//STEP 6 - NOW WE GRAB ALL THE CART ITEMS INTO A LIST AND VERIFY IF IT CONTAINS OUR ADDED PRODUCT
		List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		//List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3")); -- this is also valid
		
		//now we iterate through the cart items list to see if it contains our added product
		boolean productMatch = cartItems.stream().anyMatch(cartItem -> cartItem.getText().equals(productName));
		
		//now we used assertion to verify the product added in the cart to our desired productName
		Assert.assertTrue("The given poduct does not match any of the products in the cart list", productMatch);

		
		//STEP 7 - NOW WE PROCEED TO THE CHECKOUT PAGE and click on PLACE ORDER after submitting all the details
		driver.findElement(By.cssSelector(".totalRow button")).click(); //locating and clicking on CHECKOUT BUTTON
	
		//here we are just focusing on selecting the country because that is mandatory for this example cart
		//we can also use Actions class for sending text to country field - just covering this topic too
		//we have to use build() and perform() methods at the end to get the steps executed
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "United").build().perform();
		
		/*this is also valid to send text to country text box
		WebElement countryInput = driver.findElement(By.xpath("//input[@placeholder='Select Country']"));
		countryInput.sendKeys("United");		
		*/
		
		//we now wait for the drop down to be visible on the web page before we iterate through it and click on our country
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@class='ta-results list-group ng-star-inserted']")));
		driver.findElement(By.xpath("(//span[@class='ng-star-inserted']/i)[4]")).click(); //clicking on United States
		driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click(); //clicking on PLACE ORDER button
		
		
		//STEP 8 - LASTLY WE MAKE SURE THAT THE ORDER IS PLACED SUCCESSFULLY
		//we first make sure the the 'Order Placed Successfully' toast container appears after we place the order
		//we then verify that we see 'Thank You For The Order' message appears on the web page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container"))); //asking web driver to wait for toast container to appear
		String orderStatus = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(orderStatus.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("ORDER STATUS : " + orderStatus);
		
		driver.quit();
		
	}

}

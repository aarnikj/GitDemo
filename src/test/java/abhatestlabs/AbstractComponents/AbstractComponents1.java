//CREATING A SEPARATE CLASS FOR ABSTRACT COMPONENTS AND INCLUDING ALL THE RESUSABLE METHODS FOR ONLINE SHOPPING CART IN THIS

//every page object file should inherit this class to be able to inherit all its properties


package abhatestlabs.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import abhatestlabs.PageObjectModel.CartPage;
import abhatestlabs.PageObjectModel.CheckoutPage;
import abhatestlabs.PageObjectModel.OrderHistoryPage;

public class AbstractComponents1 {
	
	WebDriver driver;
	WebDriverWait wait;
	
	//now we create a constructor to accept the driver variable from child classes
	public AbstractComponents1(WebDriver driver) {
		this.driver = driver; //assigning the private variable to local variable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}
	
	/*
	 -- marking web elements as private and omplemneting Encapsulation so that any other class 
	 that inherits this class will  not be able to call its web elements directly
	 
	  -- Encapsulation allows us to hide our class feilds and only expose the action methods to public
	 */
	
	@FindBy(xpath="//button[@routerlink='/dashboard/cart']")
	private WebElement cartHeader;
	

	@FindBy(css=".totalRow button")
	private WebElement checkoutBtn;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/myorders']")
	private WebElement ordersHeader;
	
	//we create a method for web driver wait
	//we are passing the By locator as an argument to this class, By locator will flow from our test case
	public void elementVisibilityWait(By findBy) { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	
	//method to wait for the web element to appear, passing web element as an argument
	public void elementVisibilityWait2(WebElement element) { 
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	
	//creating a webdriverwait object to wait for a web element to disappear
	public void elementInvisibilityWait(WebElement element) { 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	
	//as the top header of the online cart is same for all pages, we define all its components in this class
	public CartPage goToCartPage() {
		elementVisibilityWait2(cartHeader);
		cartHeader.click();
		CartPage cartPage = new CartPage(driver); //as we are sure that this method will take us to the cart page
		return cartPage; //here we return CartPage as an object
	}
	
	
	 
	//method for going to checkout page
	public CheckoutPage goToCheckoutPage() {
		checkoutBtn.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver); //we are sure that clicking on Checkout Button will take us to the checkout page
		return checkoutPage; //we return checkout page as an object
	}
	
	
	//method for going to the order history page
	public OrderHistoryPage gotoOrderHistoryPage() {
		ordersHeader.click();
		OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
		return orderHistoryPage; //returning order history page as an object
	}

}

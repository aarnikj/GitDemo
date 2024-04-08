package abhatestlabs.PageObjectModel;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import abhatestlabs.AbstractComponents.AbstractComponents1;

public class CartPage extends AbstractComponents1 {
	
	WebDriver driver;
	
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		}
	
	/*
	 -- marking web elements as private and omplemneting Encapsulation so that any other class 
	 inheriting this Page Object (class) will not be able to call its web elements directly
	 
	 -- Encapsulation allows us to hide our class feilds and only expose the action methods to public
	 */
	@FindBy(xpath="//div[@class='cartSection']/h3")
	private List<WebElement> cartItems;
	
	public boolean verifyProductName(String productName) {
		boolean productMatch = cartItems.stream().anyMatch(cartItem -> cartItem.getText().equals(productName));
		return productMatch;
	}


}
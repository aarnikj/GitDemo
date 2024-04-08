package abhatestlabs.PageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abhatestlabs.AbstractComponents.AbstractComponents1;

public class OrderConfirmationPage extends AbstractComponents1 {
	
	WebDriver driver;
	
	public OrderConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".hero-primary")
	private WebElement orderStatus; //getting order status message
	
	
	public String orderStatusMessage() {
		String userOrderStatus = orderStatus.getText();
		System.out.println("ORDER STATUS : " + userOrderStatus); //printing just for our reference, this is not required
		return userOrderStatus;
	}

}

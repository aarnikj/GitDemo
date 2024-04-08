package abhatestlabs.PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abhatestlabs.AbstractComponents.AbstractComponents1;
import abhatestlabs.TestComponents.Retry;

public class CheckoutPage extends AbstractComponents1{
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	private WebElement countryInputBox;	 //locating country text box

	private By countryDropdown = By.xpath("//section[@class='ta-results list-group ng-star-inserted']");
	//locating country down down
	
	@FindBy(xpath="(//button[contains(@class,'ta-item')])[1]")
	private WebElement country; //locating country name
	
	@FindBy(xpath="//a[@class='btnn action__submit ng-star-inserted']")
	private WebElement placeOrderBtn;
	
	
	private By placeOrderToastContainer = By.id("toast-container"); //locating order placed successfully toast container
	
	
	//method to select the country
	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(countryInputBox, countryName).build().perform();
		elementVisibilityWait(countryDropdown); //waiting for country drop down to appear
		country.click();
	}
	
	
	public OrderConfirmationPage placeOrder() {
		placeOrderBtn.click();
		elementVisibilityWait(placeOrderToastContainer); //waiting for order placed successfully toast container to appear
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver); //clicking on place order button will take us to the order confirmation page
		return orderConfirmationPage; //returning orderConfirmationPage as an object
	}
}

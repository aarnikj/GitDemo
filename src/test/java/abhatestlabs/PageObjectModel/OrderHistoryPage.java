package abhatestlabs.PageObjectModel;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import abhatestlabs.AbstractComponents.AbstractComponents1;

public class OrderHistoryPage extends AbstractComponents1 {
	
	WebDriver driver;
	
	public OrderHistoryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	
	@FindBy(css="tr td:nth-child(3)")
	private List<WebElement> ordersList;
	
	public Boolean verifyOrderDisplay(String productName) {
		Boolean orderMatch = ordersList.stream().anyMatch(orders -> orders.getText().equalsIgnoreCase(productName));
		return orderMatch;
	}

}

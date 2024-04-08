//PRODUCT CATALOG

package abhatestlabs.PageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import abhatestlabs.AbstractComponents.AbstractComponents1;

public class ProductCatalog extends AbstractComponents1{

	WebDriver driver;
	
	public ProductCatalog(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	//List<WebElement> itemList = driver.findElements(By.cssSelector(".col-lg-4"));
	//locating and storing cart items to a list
	@FindBy(css=".col-lg-4")
	private List<WebElement> storeProducts;
	
	
	//page factory is only for driver.findElement(By locator) web elements construction
	//so in order to wait for the product list to appear we use the below syntax as AbstactComponent1 class is using 'By locator' only
	private By cartItems = By.cssSelector(".col-lg-4");
	
	//adding addtoCartBtn using By locator
	private By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
	
	private By addToCartToastContainer = By.cssSelector("#toast-container"); //locating product added to cart toast container
	
	//locating cart load icon
		@FindBy (css=".ng-animating")
		private WebElement cartLoadIcon;
	
	//creating a method to fetch us the product list in the shopping cart
	public List<WebElement> getItemList() {
		elementVisibilityWait(cartItems); //here we wait for all the cart items to load
		return storeProducts; //now we return the list of cart items
	}
	
	
	//creating a method to fetch the user's desired product from the cart product list
	//we pass the user supplied product name to this method
	//return type for this method is WebElement
	public WebElement getUserProduct(String productName) {
		WebElement userProduct = getItemList().stream().filter(item -> item.findElement(By.cssSelector("b"))
				.getText().equals(productName)).findFirst().orElse(null);
		
		return userProduct;
	}
	
	
	//now we create a method to add the user supplied product to shopping cart
	public void addProductToCart(String productName) {
		//as scope of search for the userProduct is within its method, we have to access it here to be able to use the product name
		WebElement product = getUserProduct(productName);
		
		//here we locate the addtoCart button with reference to product name, hence we can't we page factory locator identification here
		//it is not possible to apply page factory within WebElement.findElement, so we use By locator
		product.findElement(addToCartBtn).click(); //locating + clicking on add to cart button
		
		elementVisibilityWait(addToCartToastContainer); //waiting for 'product added successfully' toast container to appear
		elementInvisibilityWait(cartLoadIcon); //waiting for cart load icon to disappear
	}
	
	
	
	}
	
	
	

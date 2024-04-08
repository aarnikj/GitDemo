//THIS FILE HAS LOGIN PAGE DETAILS 
//WE USE PAGE FACTORY MODEL HERE

package abhatestlabs.PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abhatestlabs.AbstractComponents.AbstractComponents1;
//NOTE : WHEN WE ACCESS A CLASS RESIDING IN ANOTHER PACKAGE,, WE HAVE TO IMPORT IT AS  A PACKAGE

//this class inherits AbstractComponents1, hence it acquires all its properties
public class LandingPage extends AbstractComponents1 {
		
		WebDriver driver;
		
		//all the statements that we need to run before executing the other class methods should be put in the constructor
		//as we need to send the driver from an another file to the login file, we will built a constructor which helps us to grab the driver
		public LandingPage(WebDriver driver) { //we have to pass the driver that is being received from another class to this constructor
			super(driver); //super helps us to pass the driver to the parent class
			this.driver = driver; //initializing the driver, i.e. assigning the constructor (private) variable to class (local) variable
			PageFactory.initElements(driver, this); //this statement initializes all the elements in this class
													//it takes two arguments, driver passed on from another class, this to assign it to the local class variable
		}
		
	
		//storing login credentials
		//WebElement userEmail = driver.findElement(By.id("userEmail"));
		//the above step can be written in a different way using : PAGE FACTORY MODEL
		@FindBy(id="userEmail") WebElement userEmail; 
		//the above step saves us from writing driver.findelement statement, however, it runs the same logic during runtime
		
		@FindBy(id="userPassword") 
		private WebElement userPassword;
		
		@FindBy(id="login") 
		private WebElement loginBtn;
		
		@FindBy(css="[class*='flyInOut']")
		private WebElement loginErrorMsg;
		
		private By toastContainerMessage = By.cssSelector("#toast-container"); //locating toast container
		
		
		//method to load the web page
		public void goToPage() {
			driver.get("https://rahulshettyacademy.com/client");
		}
		
		
		//method to grab the error message text
		public String getErrorMessage() {
			elementVisibilityWait2(loginErrorMsg); //we first wait for the login error message to appear before grabbing its text
			return loginErrorMsg.getText(); //returning the text of the error message
			
		}
		
		//now we create another method that performs all the login actions
		public ProductCatalog userLogin(String emailID, String password) {
			userEmail.sendKeys(emailID);
			userPassword.sendKeys(password);
			loginBtn.click();
			//we should not declare hard coded values in Page Factory Model
			//Page Object should not hold any data, all of it should come from test cases. 
			//It should only be focused about web elements and actions
			
			//we also wait for the 'login successfully' toast container to appear
			elementVisibilityWait(toastContainerMessage);
			
			
			//as we know and are sure that successful completion of the userLogin() method will take us to the ProductCatalog page,
			//we will create an object for ProductCatalog here only and initiate it (we are encapsulating the process of landing to product catalog inside this method)
			//this way we can reduce our code of creating and initializing object of ProductCatalog class every time we want to sign in and reach the product catalog
			ProductCatalog productCatalog = new ProductCatalog(driver);
			return productCatalog; //we return the ProductCatalog object here
			
		}
		
		
		
	
	}



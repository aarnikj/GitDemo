//THIS CLASS CONTAINS REUSABLE METHODS THAT CAN BE USED IN TEST CASES
package abhatestlabs.TestComponents;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import abhatestlabs.PageObjectModel.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage; 
	//defining landingPage variable on global level so that it can be used by child classes of BaseTest class
	
	public WebDriver webDriverInitialization() throws IOException {
		
		//now we use properties class to access global.preoperties file, we have to import java properties package for this
		Properties prop = new Properties(); //but this class accepts file as input stream
		
		//this class converts our file to input stream so that it can be used by Properties class
		//it takes in the file path
		//System.getProperty("user.dir") gives us the path where file is located during runtime
		//we have to use double slashes for path starting from src folder for selenium to be able to understand that we are referring to a file path
		FileInputStream fileIS = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//abhatestlabs//resources//Global.properties"); 

		prop.load(fileIS); //load() method helps us to access the file, but we have to pass on the file as an input stream		
		
		//all system-level variables in java are accessed using System.getProperty() method
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		/*
		 -- we are using TERNARY OPERATOR in place of if-else condition above
		 
		 -- the ternary operator checks true for the given condition and executes the first statement (before :) in case found true
		 otherwise it executes the second statement (after :)
		 
		 -- for using ternary operator, left side of the assigment (=) must be a variable
		 */
		
		//String browserName = prop.getProperty("browser"); //we use the getProperty() method to fetch the desired property
		
		
	      driver = new ChromeDriver();
		
	      
	     /*
	      -- here we are turning on the headless mode option for Chrome in addition to the 
	      normal execution behavior
	      
	      -- using  ChromeOptions class, we can set any configuration for the Chrome.
	      -- we have to import this class
	      
	      -- We create an object of this class in order to weak the existing behavior of the Chrome execution.
		  
		  -- Next we add "headless" argument to it using addArgument() method so that the chrome 
		  driver knows that we want the test to be run in headless mode.

          -- To make the chrome driver aware of our execution choice, we need to send it to 
          ChromeDriver class as an argument.

		  -- Now when the browser tries to execute the test it will give priority to the ChromeOptions class object and run the tests in headless mode 
	      */
		if(browserName.contains("Chrome")) //using contains so that we can also pass only chrome as browser via our Maven command
		{
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless")) { 
				options.addArguments("headless"); //turning on headless mode if the browserName for chrome contains headless
			
				/*
				 -- in case we see a lot of flakiness during test run in headless mode, we can add 
				 the below statement to so as to make sure that our tests run in full screen mode in backend
				 
				 -- we have to import the Dimesion package for this
				 -- this is teh recommended size for a full screen test run
				 */
				
				driver.manage().window().setSize(new Dimension(1440, 900)); //optional		 
			}
			driver = new ChromeDriver(options);
		}
			
		else if(browserName.equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("Edge"))
		{
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
		
		
		return driver; //we return web driver as an object
	}
	
	
	//creating a method to  launch the web page
	//we add @BeforeMethod testng annotation to this method so that it runs before any method in the test case
	/*
	we have added alwaysRun helper to this method because we want this test method to run always
	irrespective of what is defined within testng.xml file
	*/
	@BeforeMethod (alwaysRun=true)
	public LandingPage launchingWebPage() throws IOException {
		driver = webDriverInitialization(); //invoking method to initialize web driver, it returns the web driver
		landingPage = new LandingPage(driver); //activating the landingPage variable as an object of LandingPage class
		landingPage.goToPage();
		return landingPage; //here we are returning LandingPage as an object
	}
	
	
	//creating @AfterMethod to run this method after all the methods in a test case are done 
	/*
	we have added alwaysRun helper to this method because we want this test method to run always
	irrespective of what is defined within testng.xml file
	*/
	@AfterMethod (alwaysRun=true)
	public void testTearDown() {
		driver.quit();
	}
	
	
	//test method to take screenshot 
	//this method is designed to take testName and web driver instance as arguments
	@Test(alwaysRun=true)
	public String getErrorScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "reports" + "testCaseName" + ".png");
		FileUtils.copyFile(sourceFile, destFile);
		return System.getProperty("user.dir") + "reports" + "testCaseName" + ".png"; //returning the file path
		
	}
	//TESTNG looks for annotations in the child class and its parents class and then executes the methods accordingly

}


//THIS CLASS HOLDS TESTNG LISTENERS
//listeners are used to listen to test case events



package abhatestlabs.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import abhatestlabs.resources.ExtentReporterNG;

/*
 -- ITestResult class describes the result of a test run
 -- ITestListener class provides a set of listeners that we can use to listen to test events  
(start, success, failure, skip etc)

-- we are making this case to extend BaseTest class so that it inherits all the methods of the BaseTest class
*/


public class Listeners extends BaseTest implements ITestListener {
	
	static ExtentTest test; //saving the test entry in a class variable 
	
	//making the return type static makes it easier to access the method, 
	//we just have to create an object and then initialize it by accessingClass.methodToBeAccesed
	static ExtentReports extent = ExtentReporterNG.getReportObject();
	
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); 
	/*
	 -- creating an instance of ThreadLocal class. 
	
	 -- This instance is thread safe, means even if the tests are run cocurrently each test object 
	 pushed inside extentTest instance will have its own thread so that no other test would be able to override the test object
	*/
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName()); 
		
		extentTest.set(test); 
		//each test object pushed into extentTest will be assigned a unique thread ID by ThreadLocal class
		//set() method is used to push the test object
		//TheadLocal sets the pushed test object and maps the thread ID to the pushed test name
		
		/*
		 -- now we are asking extent report object to create a test
		
		-- since we will be running this method executes on start of any test, the result object 
		of ITTestResult class will contain the information about the current running test (name, test result etc).
		So we use result to extract the method and its name
		
		-- now testng kick starts this method before every test execution and the result object of 
		ITTestListener helps us retrieve the running test method and its name dynamically and sets
		its entry in the report
		 */
		
		extentTest.get().log(Status.PASS, "Test Passed");
		//logging the test pass status, this is optional as extent report will show the pass status by default
	}
	
	
	//The @Override annotation in java denotes that the child class method overrides the base class method.
	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable()); 
		/*
		 -- now we retrieve the test by calling get() method on ThreadLocal object as it has 
		 unique thread ID for the test
		 
		 -- every thread ID is mapped to a test object
		 
		 -- here we try to get the error message in case of test failure using getThrowable() method
	
		 -- this method is important because we want the extent report to log the fail status
		 
		 -- in case we don't mention the onTestStart method, extent report will declare the method pass 
		in case it doesnt reach onTestFailure() method block
		*/
		
		/*
		-- now we are asking this method to take screenshot in case of a test failure 
		
		-- we are setting the initial value of filepath variable as null, we have to initiaze it before so 
		that it could be used in places outside try/cath block
		
		-- as this class can implement all the methods of BaseTest class, we implement the screenshot method here
		
		-- the getErrorScreenshot() method requires testName as parameter which we pass it to using result 
		object from extent reports
		
		-- the getErrorScreenshot() method returns the filepath which we have caught in a variable below
		
		-- addScreenCaptureFromPath() method takes the screenshot fom the filepath defined and attaches 
		it to the extent report.
		
		-- second argument accepts the test name to be attched 
		
		-- try/catch blocks tries to execute the getErrorScreenshot() method and if there is no screenshot 
		present, it prints the exception in the output
		
		-- once the screenshot is taken addScreenCaptureFromPath() takes the filepath from our local 
		system and attaches it to the extent report
		
		-- as the result object from ITTestResult class contains all the information about a test, 
		we extract the web driver information using it
		
		-- getTestClass() gets us the running class that is mentioned in the testng.ml, 
		from there it goes to real class that holds the driver information using getRealClass() method, 
		from there it helps us get the field of the web driver, from there we fetch the driver instance
		
		-- fields are associated at class level and not method level
		
		-- we can noe pass this driver instance as an argument to the getErrorScreenshot() method
		 */
		
		String filepath = null;
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			filepath = getErrorScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		extentTest.get().addScreenCaptureFromPath(filepath, result.getMethod().getMethodName());
		
	}
	
	
	//ITestContext class contains all the information about a given test run
	//we use onFinish() method after a test run to flush the extent report
	//flush() method helps us to grab the data and generate a report
	//in absence of flush method ExtentReport class gathers all the test data but fails to generate a report
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	


	
	 @Override
	    public void onTestSkipped(ITestResult result) {

	    }

	    @Override
	    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	    }

	    @Override
	    public void onTestFailedWithTimeout(ITestResult result) {

	    }

	    @Override
	    public void onStart(ITestContext context) {
	        System.out.println("Execution started :"+context.getName());
	    }

	    
	

		
}

//THIS CLASS GENERATES EXTENT REPORTS

/*
 to view extent report >> navigate to the html file (right-click) >> properties >> copy and paste the path in the web browser 
 */


package abhatestlabs.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	 static ExtentReports extent;
	 
	//creating a method to create reports
	//declaring the object returned as spublic + static so that it can be easily accessed by other classes
	public static ExtentReports getReportObject() { 
		/*
		 --  we primarily use two classes to generate extent reports - ExtentReports and ExtendtSparkReporter
		 -- System.getProperty("user.dir")  gives us the current project path dynamically irrespective of the system we are working on
		 -- the file path after (user.dir) is relative to the folder
		 */
		
		String path = System.getProperty("user.dir")+"//test-output//reports//ExtentReport.html"; 
		//we store the extent report file path in a variable
		
		/*
		 -- ExtentSparkReporter class  is responsible to create one html file and do all file configurations 
		 -- it accepts the file path as the argument so that it knows where to create the html file
		-- config() method helps us to do htlm file configurations
		*/
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results"); //changing report name
		reporter.config().setDocumentTitle("Test Results"); //changing the title of the html document
		
		
		/*
		 -- ExtentReports is the main class that drives all the reporting execution
		 
		 -- ExtentSparkReporter is a helper class, which is helping to create some configuration, 
		 and that will finally report to its main class
		 
		 -- once we create report using ExtentSparkReporter class, we have to attach it to the main class,
		 so we have to pass the object of ExtentSparkReporter class to ExtentReports class
		 
		 -- attachReporter() method is used to attach the report
		 
		 -- tester name can be given using setSystemInfo() method
		 */
		
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Abha Joshi");
		return extent; //returning extent report created as an object
	}
}



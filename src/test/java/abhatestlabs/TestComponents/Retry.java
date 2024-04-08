//this class retries the failed tests

// -- we have to pass on this retry class to the methods that we suspects might fail, 
//or the one that fail without any reason or tests which are inconsistent (flaky)

package abhatestlabs.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/*
 -- IRetryAnalyzer class provides the capability to re run a class after it fails, we can decide 
 on the times of re run based on our business requirements
*/
public class Retry implements IRetryAnalyzer{
	
	int runCount = 0; //setting the test run count to 0
	int maxRetry = 1; //setting max retry value to 1
	
	
	/*
	 -- the below method is used to retry a test run in case of a failure
	 -- By default thsi method returns False, so that it won't retry a tets rerun
	 
	 -- we have to make it return True in case we want it to retry a test rerun	 
	 */
	
	@Override
	public boolean retry(ITestResult result) {
		
		if(runCount<maxRetry) {
			runCount++; //increasing the runCount by 1
			return true; //returns true until runCount is not equal to maxRetry
		}
	
		
		return false;
	}

}

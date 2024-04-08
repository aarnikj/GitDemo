//THIS RUNNER CLASS IS USED TO RUN .FEATURE FILE

//THIS IS NOT WORKING

/*
 -- in Cucumber terminology running the test means running the .feature file. We create a Test Runner for that.

-- depending on ur framework we can create JUnit Runner or TestNG Runner,

-- here we create a TESTNG runner because our framework is based on TESTNG + CUCUMBER

-- So if the code is driven by TestNG then we need to create TestNG cucumber runner.

-- we run this runner as TESTNG class
*/

package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/*
 -- we use CucumberOptions tag to run a Cucumber test, it requires importing a package for the same
 
 -- tags attribute is used to group scenario(s)/scenarior outline(s)
 
-- features attribute contains path to the .feature file starting from the folder level 
(excluding the file name)

-- glue attribute contains the path to the glue file (step definition)starting from package level 
(excluding the class name)

-- the output of cucumber is in encoded format, means that results will not be in readable.
 In order to make them readable monochrome attribute

-- Cucumber provides plugin for reporting.In case we want to generate the HTML report we can pull that plugin

-- we have to provide the reporting information in {key:value} pair
key- type of report
value- storage location. this folder will be created automatically when we run the test runner

-- by default cucumber is not able to scan TestNG assertions or TestNG libraries. So TestNG provides 
a wrapper class called AbstractTestNGCucumberTests to provide seamless integration with cucumber.

-- this is not required for JUnt test because cucumber has the inbuilt capability to run the tests 
written in JUnit code but TestNG code reading is not something which comes inbuilt with cucumber
*/

@CucumberOptions(tags="TestValidation", features="src/test/java/cucumber/TestValidation.feature", glue="abhatestlabs.StepDefinition",
monochrome=true, plugin= {"html:target/cucumber-reports/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

	
}
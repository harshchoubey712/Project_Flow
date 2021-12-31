package Project_flow.Project_flow;

public class Project_Flow_skip_scenarios {
	
	Login page.feature with tags
	
	
	
	
	@Login
	Feature: Login page feature

	@Smoke
	Scenario: Login page title
	Given user is on login page
	When user gets the title of the page
	Then page title should be "Login - My Store"

	@Smoke
	Scenario: Forgot Password link
	Given user is on login page
	Then forgot your password link should be displayed

	@Regression @Skip
	Scenario: Login with correct credentials
	Given user is on login page
	When user enters username "dec2020secondbatch@gmail.com"
	And user enters password "Selenium@12345"
	And user clicks on Login button
	Then user gets the title of the page
	And page title should be "My account - My Store"
	
-------------------

Then add ParallelRun.java with below details.
Like tags= "not @Skip"


This will come to parallelrun testrunner.
Look for tags @skip and skip it from loginpage.feature.

((
package parallel;

import java.util.Properties;

import org.junit.Assume;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failedrerun.txt"
			
				}, 
		tags ="not Skip",
		monochrome = true,
		glue = { "parallel" },
    	features = { "src/test/resources/parallel/LoginPage.feature" }
)

public class ParallelRun extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
))

So it should execute only 2 scenarios from above loginfeature file.



Run parallelrun.java and this time only 2 tests ran.

-----------------------

We can skip from maven command too.

Run @login means all scenarios from login feature but not @skip.


(( mvn test -Dcucumber.options ="--tags  '@Login and not @Skip"
		))
----------------------------


So we can skip scenarios based on tags in test runner, maven command and also
Using hooks.((	@Before(value = "@skip", order=0)))

((
		
	public class ApplicationHooks {

	private DriverFactory driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	@Before(value = "@skip", order=0)
	public void skip_scenario(Scenario scenario)
	{
		System.out.println("SKIPPED SCENARIO is " + scenario.getName());
		Assume.assumeTrue(false);
		
	}
	
	@Before(order=1)
	public void getProperty() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
	}

	@Before(order = 2)
	public void launchBrowser() {
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);
/*
 Above will launch the driver with thread local using driverfactory object.
 
 */
		
	}
------------------------------


comment 	//tags ="not Skip", so that hooks can run and scenario can be skipped using hooks not through parallel runner.
Again run parallel run.

((
package parallel;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failedrerun.txt"
			
				}, 
		//tags ="not Skip",
		monochrome = true,
		glue = { "parallel" },
    	features = { "src/test/resources/parallel/LoginPage.feature" }
)

public class ParallelRun extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}


		))

Execute all features under parallel((@DataProvider(parallel = true)))

This scenario in yellow is skipped.

-----------------------


Use tag @skip in account page feature too.

((
Feature: Account Page Feature

Background:
Given user has already logged in to application
|username|password|
|dec2020secondbatch@gmail.com|Selenium@12345|

#We are using the concept of datatables in background .
#We are not using concept of examples using data driven.


Scenario: Accounts page title
Given user is on Accounts page
When user gets the title of the page
Then page title should be "My account - My Store"

@skip
Scenario: Accounts section count
Given user is on Accounts page
Then user gets accounts section
#Here also we are using datatable concept using user gets account section details.
|ORDER HISTORY AND DETAILS|
|MY CREDIT SLIPS|
|MY ADDRESSES|
|MY PERSONAL INFORMATION|
|MY WISHLISTS|
|Home|
And accounts section count should be 6
))

Again run parallel run runner and this time 2 @skip tags will get skipped using @before(order=0)hook.

========================

Add above dependencies in pom.xml

((
		
		))


}

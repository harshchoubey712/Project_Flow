package Project_flow.Project_flow;

public class Project_flow_CucumberPOMSeriesTestNgExcelFailedRerun {
	
	Fail forgot test by changing it to password?111.
	((  LoginPage.java
			
			package com.pages;

			import org.openqa.selenium.By;
			import org.openqa.selenium.WebDriver;

			public class LoginPage {

				private WebDriver driver;

				// 1. By Locators: OR
				private By emailId = By.id("email");
				private By password = By.id("passwd");
				private By signInButton = By.id("SubmitLogin");
				private By forgotPwdLink = By.linkText("Forgot your password?111");



			
			))
	
So  scenario, forgot password link , will get failed

((  Loginpage.feature
		Feature: Login page feature

		Scenario: Login page title
		Given user is on login page
		When user gets the title of the page
		Then page title should be "Login - My Store"

		Scenario: Forgot Password link
		Given user is on login page
		Then forgot your password link should be displayed

		Scenario: Login with correct credentials
		Given user is on login page
		When user enters username "dec2020secondbatch@gmail.com"
		And user enters password "Selenium@12345"
		And user clicks on Login button
		Then user gets the title of the page
		And page title should be "My account - My Store"		
		
		
		))

Add rerun plugin in parallelrun runner file.

Execute only login feature and Run as testng.

((
		package parallel;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"timeline:test-output-thread/",
				"rerun:target/failedrerun.txt"
			
				}, 
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

3 scenarios of loginpage feature
will run in parallel and forgot password 
scenario will get failed.

(("rerun:target/failedrun.txt"
		))



Refresh the project and you can see the new folder with failedrerun.txt.
Below shows that login feature line 8 scenario get failed .



Below are the contents of faieldrun.txt

((file:src/test/resources/parallel/LoginPage.feature:8 ))

Create below runner file Failedrun in src/test/java, parallel package, folder.

((
		package parallel;

		import org.testng.annotations.DataProvider;

		import io.cucumber.testng.AbstractTestNGCucumberTests;
		import io.cucumber.testng.CucumberOptions;

		@CucumberOptions(
				plugin = {"pretty",
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
						"timeline:test-output-thread/",
						"rerun:target/failedrerun.txt"
					
						}, 
				monochrome = true,
				glue = { "parallel" },
				features = { "@target/failedrerun.txt" }
		)

		public class FailedRun extends AbstractTestNGCucumberTests {
			@Override
			@DataProvider(parallel = true)
			public Object[][] scenarios() {
				return super.scenarios();
			}
		}
		
		
		))
Above will take
Features= { "@target/failedrerun.txt" }
This will take feature file failed scenario from 8th line of failererun.txt
And will run only that failed scenario.

@dataprovider section will took all failed scearios and run them in parallel.
Run this failedrun runner and this will now pick only failed forgot password scenario.

Only failed scenario forgot password works and runs this time.

Now we are making signinbutton also failed in loginpage.

So now 2 sceanrios should get failed now.

Again run main parallelRun runner file

This will update our failedrerun.txt file will 
failed line numbers of failed scenarios.

Below are the updated contents of failedrun.txt

((file:src/test/resources/parallel/LoginPage.feature:8:12 ))

Scenario at line 8 and 12 gets failed and updated above.

Now run failedrun runner file to run all failed scenarion of line 8, 12 in parallel mode.


So 1st execute parallelrun runner. 

This will generate the failed txt file.

Then execute the above failedrun.java runner  to run only failed scenarios.

====================================
do git push

create new repository with the nme of project CucumberPOMSeriesTestNgExcelFailedRerun.
cd /Users/harshchoubey/eclipse-workspace/CucumberPOMSeriesTestNgExcelFailedRerun
git init

ls -alt

git remote add origin https://github.com/harshchoubey712/CucumberPOMSeriesTestNgExcelFailedRerun.git
	
	git status
	
	git add .
	
	git status
	
	git commit -m "Adding changes for CucumberPOMSeriesTestNgExcelFailedRerun"
	
	Git push origin master
	

concept ends here for parallel run.
=====================


}

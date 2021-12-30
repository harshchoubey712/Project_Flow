package com.qa.Util;

public class ProjectFlow_CucumberPOMSeriesTestng {

	Accountspage.feature
	
	
	Feature: Account Page Feature

	Background:
	Given user has already logged in to application
	|username|password|
	|dec2020secondbatch@gmail.com|Selenium@12345|

	#We are using the concept of datatables in background .
	#We are not using concept of examples using data driven.

	@accounts
	Scenario: Accounts page title
	Given user is on Accounts page
	When user gets the title of the page
	Then page title should be "My account - My Store"

	@accounts
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

	
//	These scenarios we can run in parallel using Testng 
//	but not through Junit.
	
-------------------

pom.xml

	<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-testng</artifactId>
	<version>${cucumber.version}</version>
	<scope>test</scope>
    </dependency>	
    
	<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>${maven.surefire.version}</version>
	
		<configuration>
		<includes>
			<include>**/ParallelRun.java</include>
		</includes>
		<parallel>methods</parallel>
		<threadCount>4</threadCount>
		<useUnlimitedThreads>false</useUnlimitedThreads>
	</configuration>

</plugin>


//	Add cucumber testng dependency in pom.xml.
// Add surefire plugin.
//Add parallel = methods.  
---------------------

package parallel;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"timeline:test-output-thread/"
				}, 
		monochrome = true,
		glue = { "parallel" },
		features = { "src/test/resources/parallel" }
)

public class ParallelRun extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}

/*
Create parallel package under src/test/java and move step definitions and hooks under them.

This parallel package structure is for testng.

So we need to create Parallel runner class under parallel package and use extends 
Abstracttestngcucumbertests.

Create 2 d array and return 2 dimensional array using super.scenarios().  ((public Object[][] scenarios() , return super.scenarios()))

This will run all scenarios using dataprovider in parallel mode.

Change glue to parallel as both step def and hooks are now present under parallel package.




*/
--------------------

Similarly prepare parallel folder for features under src/test/resources and copy all features.
So we have 3 main folders main, test and resources.
Both src/test/resources and src/test/java will have parallel folder.

src/test/java , parallel folder will contain all step def and hooks.
src/test/resources , parallel folder will contain all features.

Main package Project_flow.Project_flow be for driverfactory and pages.

Right click on project, Click on maven, update project.
This will build our project.

Run the parallelrun.java testrunner file as testng.

All scenarios running in parallel mode.

So we had just changed the runner file as above for testng.
Add cucumbertestng depednencies and change the folder structure using parallel.


Earlier for executing using maven, junit we gave path as above
Of runner under mavenfailsafe plugin.




<artifactId>maven-failsafe-plugin</artifactId> 
<version>3.0.0-M3</version> 
<executions> 
    <execution> 
        <goals> 
            <goal>integration-test</goal> 
        </goals> 
        <configuration> 
        UNCOMMENT - To add any exclusions if required  
        <excludes> <exclude>**/*IT*.java</exclude> </excludes> 
        <includes> 
          UNCOMMENT BELOW LINE - To execute feature files with a single runner 
          <include>**/MyTestRunner.java</include> 
         
        
        
        Now to execute scenarios in parallel using testng we need to use parallelrun.java under 
        maven surefire plgin configuration.
        
    	<plugin>
    	<groupId>org.apache.maven.plugins</groupId>
    	<artifactId>maven-surefire-plugin</artifactId>
    	<version>${maven.surefire.version}</version>
    	
    		<configuration>
    		<includes>
    			<include>**/ParallelRun.java</include>
    		</includes>
    		<parallel>methods</parallel>
    		<threadCount>4</threadCount>
    		<useUnlimitedThreads>false</useUnlimitedThreads>
    	</configuration>
---------------------


    	Go to the project path and execute using maven. 
    	
    	do mvn verify to run the tests.
    	
-----------------

cucumber testng with parallel scenarios  ends . 
    	
}

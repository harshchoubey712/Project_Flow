package com.qa.util;

public class Project_flow_CucumberPOMSeriesTestNgExcelRead {
	
	
//	Save the excel sheet having data with automation.xlsx
	
//	Copy apache poi dependency in pom.xml.
	
	<dependency>
	<groupId>org.apache.poi</groupId>
	<artifactId>poi</artifactId>
	<version>4.1.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
<dependency>
	<groupId>org.apache.poi</groupId>
	<artifactId>poi-ooxml</artifactId>
	<version>4.1.2</version>
</dependency>


--------------
//Copy excelreader.java in com.qa.util.

Right click on project, maven, update project

This will build our project and our project will get updated.

After this right click on project, configure, convert to cucumber project.

After converting to cucumber start writing feature.


Contactus.feature
Feature: Contact Us Feature

Scenario Outline: Contact Us scenario with different set of data
Given user navigates to contact us page
When user fills the form from given sheetname "<SheetName>" and rownumber <RowNumber>
And user clicks on send button
Then it shows a successful message "Your message has been successfully sent to our team."

Examples:
|SheetName|RowNumber|
|contactus|0|
|contactus|1|

This will pick the data from contactus sheet with rows 0 and 1.

Save and then run the feature by doing right click,
Run as cucumber feature
to generate missing step definitions.

Select contact us feature which will show missing steps for contact feature.
Run


---------------

This webmaster in UI is coming from excel file then it is been read by feature
And then goes to page class method, fillContactUsForm under heading.

So then use select.byvisibletext(by heading- webmaster).
Then enter email Id and other details. Then do click on send.
((select.selectByVisibleText(heading);
  driver.findElement(email).sendKeys(emailId);
))
Then checking the text of successmeasage field.((getSuccessMessg()))


ContactUsPage.java

package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ContactUsPage {
	
	private WebDriver driver;

	private By subjectHeading = By.id("id_contact");
	private By email = By.id("email");
	private By orderRef = By.id("id_order");
	private By messageText = By.id("message");
	private By sendButton = By.id("submitMessage");
	private By successMessg = By.cssSelector("div#center_column p");

	public ContactUsPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getContactUsPageTitle() {
		return driver.getTitle();
	}
	
	public void fillContactUsForm(String heading, String emailId, String orderReference, String message) {
		Select select = new Select(driver.findElement(subjectHeading));
		select.selectByVisibleText(heading);
		driver.findElement(email).sendKeys(emailId);
		driver.findElement(orderRef).sendKeys(orderReference);
		driver.findElement(messageText).sendKeys(message);
	}

	public void clickSend() {
		driver.findElement(sendButton).click();
	}
	
	public String getSuccessMessg() {
		return driver.findElement(successMessg).getText();
	}
	
-----------------
So creating contactus page class object in step def.

((private ContactUsPage contactUsPage = new ContactUsPage(DriverFactory.getDriver());))

Copy url of contact page.
((DriverFactory.getDriver().get("http://automationpractice.com/index.php?controller=contact");))

Create excelreader object.
((ExcelReader reader = new ExcelReader();))

Reader.getadata(,) This will return the list of map.
		
((List<Map<String,String>> testData = 
				reader.getData("/Users/harshchoubey/Downloads/automation.xlsx", sheetName);))

So testdata stores the full excel data of sheet contactus .
From then we extracted the data rowwise

((String heading = testData.get(rowNumber).get("subjectheading");))


So heading variable gets value subject heading.

After capturing data using heading, email, message
Call the page method fillcontactus.

((contactUsPage.fillContactUsForm(heading, email, orderRef, message);))


below is the expected message from feature.
((Then it shows a successful message "Your message has been successfully sent to our team."))

This will give the actual message.
((	String actualSuccMessg = contactUsPage.getSuccessMessg();))

Sheetname coming from below.
((Examples:
|SheetName|RowNumber|
|contactus|0|
|contactus|1|

))

This scenario will execute 2 times for the data corresponding to 0th and 1st row.

((
		Feature: Contact Us Feature

		Scenario Outline: Contact Us scenario with different set of data
		Given user navigates to contact us page
		When user fills the form from given sheetname "<SheetName>" and rownumber <RowNumber>
		And user clicks on send button
		Then it shows a successful message "Your message has been successfully sent to our team."

		Examples:
		|SheetName|RowNumber|
		|contactus|0|
		|contactus|1|		
))

Run the parallelrun runner as testng.

Both webmaster and customer service 2 rows heading excel data starts running 
in parallel.

In the extent report we can see passed or failed but not the actual
Excel data.

Try avoiding using excel as full data can be directly used in feature file
And can be seen by business

Excel run extent Report says data from rownumber 1, 2,3 and so on.

We are not getting actual data from excel.


do git push origin master.




--------------------------

package Project_flow.Project_flow;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

import com.pages.ContactUsPage;
import com.qa.factory.DriverFactory;
import com.qa.util.ExcelReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactUsSteps {
	
	private ContactUsPage contactUsPage = new ContactUsPage(DriverFactory.getDriver());

	@Given("user navigates to contact us page")
	public void user_navigates_to_contact_us_page() {
		DriverFactory.getDriver().get("http://automationpractice.com/index.php?controller=contact");
	}

	@When("user fills the form from given sheetname {string} and rownumber {int}")
	public void user_fills_the_form_from_given_sheetname_and_rownumber(String sheetName, Integer rowNumber) throws InvalidFormatException, IOException {
		
		ExcelReader reader = new ExcelReader();
		List<Map<String,String>> testData = 
				reader.getData("/Users/harshchoubey/Downloads/automation.xlsx", sheetName);
		
		
		String heading = testData.get(rowNumber).get("subjectheading");
		String email = testData.get(rowNumber).get("email");
		String orderRef = testData.get(rowNumber).get("orderref");
		String message = testData.get(rowNumber).get("message");
		
		contactUsPage.fillContactUsForm(heading, email, orderRef, message);

	}

	@When("user clicks on send button")
	public void user_clicks_on_send_button() {
		contactUsPage.clickSend();
	}

	@Then("it shows a successful message {string}")
	public void it_shows_a_successful_message(String expSuccessMessage) {
		String actualSuccMessg = contactUsPage.getSuccessMessg();
		Assert.assertEquals(actualSuccMessg, expSuccessMessage);
	}

}

	
	

}

}

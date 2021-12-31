package Project_flow.Project_flow;

public class Project_flow_serenity {

	
	
	
	Pom.xml
	
	Add below dependencies in pom.xml.
	((  
	
	<!-- https://mvnrepository.com/artifact/net.serenity-bdd/serenity-core -->
		<dependency>
		    <groupId>net.serenity-bdd</groupId>
		    <artifactId>serenity-core</artifactId>
		    <version>3.1.15</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/net.serenity-bdd/serenity-junit -->
		<dependency>
		    <groupId>net.serenity-bdd</groupId>
		    <artifactId>serenity-junit</artifactId>
		    <version>3.1.15</version>
		</dependency>


		<!-- https://mvnrepository.com/artifact/net.serenity-bdd/serenity-ensure -->
		<dependency>
		    <groupId>net.serenity-bdd</groupId>
		    <artifactId>serenity-ensure</artifactId>
		    <version>3.1.15</version>
		</dependency>


		<!-- https://mvnrepository.com/artifact/net.serenity-bdd/serenity-cucumber6 -->
		<dependency>
		    <groupId>net.serenity-bdd</groupId>
		    <artifactId>serenity-cucumber6</artifactId>
		    <version>2.6.0</version>
		</dependency>

		
		))
	
        
		add below changes under properties.
		((
		   <properties>	
				
				<serenity.version>2.3.12</serenity.version>
			    <serenity.maven.version>2.3.12</serenity.maven.version>
		        <serenity.cucumber.version>2.3.12</serenity.cucumber.version>
		        
		   </properties>		
				
				
				
		))
		
				
		Add plugins below: Add serenityrunner.java under surefire plugin.

		This will be executed using maven .
		
	((
			
			<plugins>


			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>${maven.compiler.version}</version>
				<configuration>
					<encoding>UTF-8</encoding>
					<source>${java.version}</source>
					<target>${java.version}</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>${maven.surefire.version}</version>
				
	  			<configuration>
					<includes>
						<include>**/MySerenityTestRunner.java</include>
					</includes>
					<parallel>methods</parallel>
					<threadCount>4</threadCount>
					<useUnlimitedThreads>false</useUnlimitedThreads>
				</configuration>
				
				
				
     
			</plugin>
			
			
			
	))
	
	
	This below serenity-reports plugin will take serenity core and generate reports .
	
	((			
			
			
			<plugin>
    <groupId>net.serenity-bdd.maven.plugins</groupId>
    <artifactId>serenity-maven-plugin</artifactId>
    <version>${serenity.maven.version}</version>
    
    <configuration>
        <tags>${tags}</tags>
    </configuration>
    
    <!-- https://mvnrepository.com/artifact/net.serenity-bdd/serenity-core -->
 <dependencies>

 <dependency>
  <groupId>net.serenity-bdd</groupId>
  <artifactId>serenity-core</artifactId>
  <version>3.1.15</version>
 </dependency>
     </dependencies>  
    
    <executions>
        <execution>
            <id>serenity-reports</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>aggregate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
			
))
	
Add serenityrunner.

We need to use cucumberwithserenity.class.	


glue=Parallel is our step def folder.

public class MySerenityTestRunner  is the classname which has to be used in pom.xml surefire plugin.

go to maven cd project path and do mvn clean verify.

this will generate serenity reports url.

Also serenity folder gets generated in eclipse.
Here we can see complete report.

Under target,serenity, site all files could be seen .
They could be combined into 1 if we opened via browser.

We can download csv file and share.

Above is serenity bdd framework.

Serenity supports only junit, not testng.




((
		package testrunners;



		import org.junit.runner.RunWith;

		import io.cucumber.junit.CucumberOptions;
		import net.serenitybdd.cucumber.CucumberWithSerenity;



		@RunWith(CucumberWithSerenity.class)
		@CucumberOptions(
				
			
				plugin = {"pretty"},
				glue = {"parallel"},
				features= {"src/test/resources/parallel/Loginpage.feature"}
									
				         
				
				)

		public class MySerenityTestRunner {

		}
	
		
		
		))

		
}



package TestScripts;

		import org.openqa.selenium.By;


		import org.openqa.selenium.WebDriver;
		import org.openqa.selenium.chrome.ChromeDriver;
		import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import org.openqa.selenium.WebElement;

		import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

		import org.openqa.selenium.*;
		import org.openqa.selenium.support.ui.Select;

		import java.io.File;
		import java.io.IOException;
		import java.sql.Driver;
		import java.util.ArrayList;
		import java.util.Arrays;
		import java.util.Iterator;
		import org.openqa.selenium.chrome.ChromeDriverService;
		import org.openqa.selenium.chrome.ChromeOptions;
		import org.openqa.selenium.remote.DesiredCapabilities;
		import org.openqa.selenium.remote.RemoteWebDriver; 



		public class allegro {

			@SuppressWarnings("deprecation")
			public static void main(String[] args) throws InterruptedException, IOException {
				// TODO Auto-generated method stub
				
				
		        System.setProperty("webdriver.chrome.driver","C:\\Users\\karolina.sobczak\\Downloads\\drivers\\chromedriverhehe2.exe");

		        ChromeOptions options = new ChromeOptions(); 
		        options.addArguments("start-maximized"); 

		        ChromeDriver driver = new ChromeDriver(options);
		        
				//open the website
				
				driver.get("https://allegro.pl/login/form?authorization_uri=https:%2F%2Fallegro.pl%2Fauth%2Foauth%2Fauthorize%3Fclient_id%3Dtb5SFf3cRxEyspDN%26redirect_uri%3Dhttps:%2F%2Fallegro.pl%2Flogin%2Fauth%26response_type%3Dcode%26state%3Dz318pK&oauth=true");
				driver.manage().window().maximize();
				Thread.sleep(2000);
				System.out.println("website is open");
				
				//if statement
				
				
				      
				        WebElement e = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div[2]/button[2]")); 
				        if (e != null) {
				            e.click();
				            
				            System.out.println("clicked on agreement");
				            
				        } if (e != null) { 
				          
				        	  System.out.println("no agreement");{
				            
				          }
				        
				        	  Thread.sleep(2000);
				  
			//login
				
				driver.findElement(By.id("username")).sendKeys("karolinasobczak1988@gmail.com");
				driver.findElement(By.id("password")).sendKeys("hpssKK4Mro");
				
				WebElement login = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
				login.click();
				 System.out.println("user is logged in");
		
				 //searching for a product
				

				  WebDriverWait wait = new WebDriverWait(driver, 15);

				  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/header/div[1]/div/div[1]/div/form/input")));
				 
				  driver.findElement(By.xpath("/html/body/div[2]/div[1]/header/div[1]/div/div[1]/div/form/input")).sendKeys("kubek");
				  System.out.println("list of the products is displayed");
				  
				 //select from dropdown
				
				  WebDriverWait wait2 = new WebDriverWait(driver, 15);

				  wait2.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("kubek termiczny")));
				  WebElement kubek = driver.findElement(By.linkText("kubek termiczny"));
				  kubek.click();
				  //driver.findElement(By.linkText("kubek termiczny"));
				  System.out.println("kubek termiczny is selected");
				  
				  //sort the prices
				  WebElement dropDown2 = driver.findElement(By.id("allegro.listing.sort"));
				  dropDown2.click();
				  
				// select the first operator using "select by value"
	              Select selectByValue = new Select(driver.findElement(By.id("allegro.listing.sort")));
	              selectByValue.selectByValue("p");
	              Thread.sleep(5000);
	              System.out.println("sort by the lowest price is selected");
		
		
		
		
		

	}

}

			
			}
		

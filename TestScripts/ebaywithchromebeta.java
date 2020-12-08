package TestScripts;


import org.openqa.selenium.By;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

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



public class ebaywithchromebeta {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
        System.setProperty("webdriver.chrome.driver","C:\\Users\\karolina.sobczak\\Downloads\\drivers\\chromedriverhehe2.exe");
		
		//WebDriver driver = new ChromeDriver();
		
		
		//on
		//System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\karolina.sobczak\\\\Downloads\\\\drivers\\\\chromedriverhehe2.exe");
	    //ChromeOptions options = new ChromeOptions();
	   // options.setExperimentalOption("debuggerAddress", "127.0.0.1:1559");
	    //WebDriver browser=new ChromeDriver(options);	
		//off
        Proxy proxy = new Proxy(); 
        proxy.setHttpProxy("https://signin.ebay.co.uk/ws/eBayISAPI.dll?SignIn&ru=:1559"); 
        proxy.setSslProxy("https://signin.ebay.co.uk/ws/eBayISAPI.dll?SignIn&ru=:1559"); 

        DesiredCapabilities capabilities = DesiredCapabilities.chrome(); 
        capabilities.setCapability("proxy", proxy); 

        ChromeOptions options = new ChromeOptions(); 
        options.addArguments("start-maximized"); 

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        System.out.println("Set proxy");

        ChromeDriver driver = new ChromeDriver(options);
        
		//open the website
		
		driver.get("https://signin.ebay.co.uk/ws/eBayISAPI.dll?SignIn&ru=");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		System.out.println("Ebay website is open");
		
		driver.findElement(By.xpath("//*[@id=\"userid\"]")).sendKeys("karolinasobczak1988@gmail.com");
		driver.findElement(By.id("signin-continue-btn")).click();
		 System.out.println("Accounts name is present");
		
		//checking if accounts name is visible
		
		 Thread.sleep(2000);
		
		 WebElement linkText = driver.findElement(By.linkText("karolinasobczak1988@gmail.com"));
		 
		 if(linkText.isDisplayed())
		 {
		 System.out.println("Element using link text is found");
		 }
		 else
			 System.out.println("Element using text is NOT found");
	      
	   }
	}
			
		
		//password
		
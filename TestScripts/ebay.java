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
import org.openqa.selenium.remote.RemoteWebDriver; 



public class ebay {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
        System.setProperty("webdriver.chrome.driver","C:\\Users\\karolina.sobczak\\Downloads\\drivers\\chromedriverhehe2.exe");
		
		//WebDriver driver = new ChromeDriver();
		
		
		//on
		//System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\karolina.sobczak\\\\Downloads\\\\drivers\\\\chromedriverhehe2.exe");
	    ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("debuggerAddress", "127.0.0.1:1559");
	    WebDriver browser=new ChromeDriver(options);	
		//off
		
		//open the website
		
		browser.get("https://signin.ebay.co.uk/ws/eBayISAPI.dll?SignIn&ru=");
		browser.manage().window().maximize();
		Thread.sleep(2000);
		System.out.println("Ebay website is open");
		
		 browser.findElement(By.xpath("//*[@id=\"userid\"]")).sendKeys("karolinasobczak1988@gmail.com");
		 browser.findElement(By.id("signin-continue-btn")).click();
		 System.out.println("Accounts name is present");
		
		//checking if accounts name is visible
		
		
		
		String t = "karolinasobczak1988@gmail.com";
	      // identify elements with text()
	      List<WebElement> l= browser.findElements(By.xpath("//*[contains(text(),'karolinasobczak1988@gmail.com')]"));
	      // verify list size
	      if ( l.size() > 0){
	         System.out.println("Text: " + t + " is present. ");
	      } else {
	         System.out.println("Text: " + t + " is not present. ");
	      }
	      
	      
	   }
	}
			
		
		//password
		
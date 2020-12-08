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

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class NavigateToGoogle {
	
	
	

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\karolina.sobczak\\Downloads\\drivers\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		
		driver.get("https://www.netflix.com/ie/login");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		System.out.println("Netflix website is open");
		
		//log in
		
		 driver.findElement(By.xpath("//*[@id=\"id_userLoginId\"]")).sendKeys("netflix-rm@gndean.com");
		 driver.findElement(By.xpath("//*[@id=\"id_password\"]")).sendKeys("DentistTimeIs230");
		 
		 driver.findElement(By.xpath("//*[@id=\"appMountPoint\"]/div/div[3]/div/div/div[1]/form/button")).click();
		 Thread.sleep(5000);
		 driver.findElement(By.className("avatar-wrapper")).click();
		 System.out.println("Login Done with Submit");
		 
		 //checking if logged in
		 
		 String Url = driver.getCurrentUrl();

		    if (Url.equals("https://www.netflix.com/browse")) {
		        System.out.println("Login successful");
		    } else {
		        System.out.println("Login Failed");
		    }
		 
		 //searching the serie 
		 
		 WebElement searchBox = driver.findElement(By.className("icon-search"));
		 searchBox.click();
		 Thread.sleep(5000);
		 WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"appMountPoint\"]/div/div/div[1]/div[1]/div[1]/div/div/div/div[1]/div/div/input"));
		 
		 searchInput.sendKeys("After Life");
		 
		 System.out.println("Searching the serie");
		 
		 //playing the episode
		 
		 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 WebElement jpgClick = driver.findElement(By.xpath("//*[@id=\"title-card-0-0\"]/div[1]/a/div[1]/img"));
		 jpgClick.click();
		 
		 WebElement episode1 = driver.findElement(By.tagName("cantankerous").className("titleCard-playSVG"));
		 episode1.click();
		 Thread.sleep(10000);
		 
		 //back from episode
		 
		 WebElement backtobrowse = driver.findElement(By.xpath("//*[@id=\"appMountPoint\"]/div/div/div[1]/div/div/div[2]/div/div[3]/div/div[5]/div[1]/button"));
		 backtobrowse.click();
		 
		 WebElement exit = driver.findElement(By.className("previewModal-close"));
		 exit.click(); }
		 
		 
		 }
					
				
		 
	
	
	
		
	
	


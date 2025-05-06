package com.BaseClass;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class baseClass {
	
	 protected WebDriver driver;
	    protected WebDriverWait wait;

	    @BeforeClass
	    public void setup() {
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--blink-settings=imagesEnabled=false"); // Disable images
	        options.addArguments("--disable-popup-blocking");
	        options.addArguments("--disable-notifications");
	        options.addArguments("--disable-extensions");
	        options.addArguments("--disable-dev-shm-usage");
	        options.addArguments("--disable-software-rasterizer");
	        options.addArguments("--disable-gpu");
	        options.addArguments("--no-sandbox");
	       // options.addArguments("--headless"); // Optional: for headless execution
	        options.addArguments("--remote-allow-origins=*");
	        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

	        driver = new ChromeDriver(options); //  Create driver with options
	        driver.manage().window().maximize();
	        driver.get("https://www.tarladalal.com/");
	        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    }

	    @AfterClass
	    public void tearDown() {
	       // if (driver != null) {
	           // driver.quit();
	        }
	    }



package com.BaseClass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import com.Utilities.LoggerReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import com.Utilities.configReader;

public class baseClass {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@BeforeClass
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		// Disable images
		// Disable images using preferences
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.managed_default_content_settings.images", 2); // 2 = block
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-software-rasterizer");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		// options.addArguments("--headless"); // Optional: for headless execution
		options.addArguments("--remote-allow-origins=*");
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		driver = new ChromeDriver(options); // Create driver with options
		driver.manage().window().maximize();
		LoggerReader.info("browser opened");
		driver.get(configReader.getKeyValues("URL"));
		LoggerReader.info("Url is opened");
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			LoggerReader.info("Closing browser after all tests");
			driver.quit();
		}

	}

}
package com.pageObject;

import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class pageObjectclass {
	   WebDriver driver;
	    WebDriverWait wait;
	    private JavascriptExecutor js;

	    public pageObjectclass(WebDriver driver,WebDriverWait wait) {
	        this.driver = driver;
	        this.wait = wait;
	        
	        PageFactory.initElements(driver, this);
	    }

	    @FindBy(xpath = "//a[text()='Recipes List']")
	    public WebElement recipes_list;

	    @FindBy(xpath = "//span[text()='Close']")
	    public WebElement dismiss_btn;

	    @FindBy(className = "two-line-text")
	    public List<WebElement> recipes;

	    public void clickRecipeList() {
	        try {
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipes_list);
	           // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.elementToBeClickable(recipes_list));
	            recipes_list.click();
	        } catch (ElementClickInterceptedException e) {
	           // dismiss(); 
	            clickRecipeList(); 
	        }
	    }
	    
	    public void removeAds() {
	        try {
	            js = (JavascriptExecutor) driver;
	            js.executeScript(
	                "const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate');" +
	                "while (elements.length > 0) elements[0].remove();"
	            );
	        } catch (Exception e) {
	            System.out.println("Ad removal failed: " + e.getMessage());
	        }
	    }

	    public void dismiss() {
	    	
	            dismiss_btn.click();
	        }
	    

	    public void click_on_recipes() {
	    	js.executeScript("window.scrollTo(0, -900);");
	    	driver.get("https://www.tarladalal.com/recipes/");
	    	/*for (WebElement element : recipes) {
	    	    js.executeScript("arguments[0].scrollIntoView(true);", element);
	    	}
	           for (int i = 0; i < 24; i++) {
	            try {
	                recipes.get(i).click();
	                 
	            } catch (Exception e) {
	                System.out.println("Skipping index " + i + ": " + e.getMessage());
	            }*/
	        //}
	    }
}

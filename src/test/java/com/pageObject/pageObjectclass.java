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

	   // @FindBy(xpath = "//span[text()='Close']")
	   // public WebElement dismiss_btn;

	    @FindBy(xpath = "//h5//a")
	    public List<WebElement> recipes;
	    
	    @FindBy(xpath="//h4[@class='rec-heading']")
	    WebElement recipeTitleElement;

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
	            System.out.println("Ads removed" + e.getMessage());
	        }
	    }

	  
	    

	    public void click_on_recipes() {    	
	    	driver.get("https://www.tarladalal.com/recipes/");    	
	    	 js.executeScript("window.scrollBy(0, 300);");
	    	  	 for (int i = 0; i < 24; i++) {
	            try {
	            	recipes.get(i).click();
	            	String recipeUrl = driver.getCurrentUrl();
	        		//Split the URL by hyphen and 'r' to get the parts
	        		String[] parts = recipeUrl.split("-");
	        		// The recipe ID is the last part before 'r'
	        		//RecipeID
	        		String recipeId = parts[parts.length - 1].replace("r", "");
	        		System.out.println(recipeId);
	        		
	        		//Recipe Name
	        		String recipetitle = recipeTitleElement.getText();
	        		System.out.println(recipetitle);
	            		
	                driver.navigate().back();
	                 
	            } catch (Exception e) {
	                System.out.println(e.getMessage());
	            }
	        //}
	    }}


}

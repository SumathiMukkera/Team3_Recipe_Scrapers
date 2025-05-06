package com.pageObject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
	    
	    @FindBy(xpath = "//p[text()='You are here: ']//span[3]")
	    public WebElement cusine_category;
	    
	   
	    public void clickRecipeList() {
	        try {
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipes_list);
	           // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.elementToBeClickable(recipes_list));
	            recipes_list.click();
	        } catch (ElementClickInterceptedException e) {
	            dismiss(); 
	            clickRecipeList(); 
	        }
	    }

	    public void dismiss() {
	    	try {
	            dismiss_btn.click();
	    	}
	    	catch(Exception e) {
	    		
	    		e.printStackTrace();
	    		
	    	}
	        }
	    

	    public void click_on_recipes() {
	           for (int i = 0; i < 24; i++) {
	            try {
	                recipes.get(i).click();
	                driver.navigate().back(); 
	               // PageFactory.initElements(driver, this); 
	            } catch (Exception e) {
	                System.out.println("Skipping index " + i + ": " + e.getMessage());
	            }
	        }
	           
	        	   
	           }
	    

     public String getCusineCategory() {
    	 
    	 String cuisineTitle =  cusine_category.getText().trim();
			
			int index = cuisineTitle.indexOf(">");
		    if (index != -1) {
		        cuisineTitle = cuisineTitle.substring(0, index).trim();
		    }

		return cuisineTitle;
	
      }
     
     public List<String> getIngredients() {
		  
		  List<WebElement> ingredients = driver.findElements(By.xpath("//div[@id=\"ingredients\"]/ul"));
		  
		  System.out.println(ingredients);
		  
		  List<String> Ingre = new ArrayList<>();
		  
		  for(WebElement ingredient : ingredients) {
			  
			   Ingre.add(ingredient.getText());
			  
		  }
		  
		  System.out.println(Ingre);
		return Ingre;
	  }
     
}

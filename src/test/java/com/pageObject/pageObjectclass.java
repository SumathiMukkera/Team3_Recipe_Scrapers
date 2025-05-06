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
	
	 @FindBy(xpath = "//a[text()='Recipes List']")
	    public WebElement recipes_list;

	    @FindBy(xpath = "//h5//a")
	    public List<WebElement> recipes;
	    
	    @FindBy(xpath="//h4[@class='rec-heading']")
	    WebElement recipeTitleElement;
	    
	    @FindBy(xpath ="(//p[@class='mb-0 font-size-13'])[1]")
	    WebElement preparation_time;
	    @FindBy(xpath ="(//p[@class='mb-0 font-size-13'])[2]")
	    WebElement cooking_time;
	    @FindBy(xpath ="(//p[@class='mb-0 font-size-13'])[3]")
	    WebElement total_time;
	    @FindBy(xpath ="(//div[@class='content']//p)[3]")
	    WebElement noOfServings;
	    @FindBy(xpath="//ul[@class='tags-list']//li")
	   List<WebElement> tags;
	    @FindBy(xpath ="//*[@id=\"aboutrecipe\"]/p[1]")
	    WebElement aboutrecipe;

	public pageObjectclass(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

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
			js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate');"
					+ "while (elements.length > 0) elements[0].remove();");
		} catch (Exception e) {
			System.out.println("Ads removed" + e.getMessage());
		}
	}

	public void click_on_recipes() {
		driver.get("https://www.tarladalal.com/recipes/");
		js.executeScript("window.scrollBy(0, 600);");
		 for (int i = 0; i < 24; i++) {
			    try {
			       	recipes.get(i).click();

			        String recipeUrl = driver.getCurrentUrl();
			        String[] parts = recipeUrl.split("-");
			        String recipeId = parts[parts.length - 1].replace("r", "");
			        System.out.println("Recipe ID: " + recipeId);

			        String recipetitle = recipeTitleElement.getText();
			        System.out.println("Recipe Title: " + recipetitle);

			        

			        System.out.println(preparation_time.getText());
			        System.out.println(cooking_time.getText());
			        System.out.println(noOfServings.getText());
			        
			        //Recipe Description from webpage.
			        String recipeDescription = aboutrecipe.getText();
			        System.out.println("Recipe Description: "  +recipeDescription);
			        
			        for(WebElement tag : tags) {
			        String tagNames =	tag.getText();
			        System.out.println(tagNames);
			        	
			        	
			        }

			        driver.navigate().back();
			       // wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".rcc_recipename > a")));
			    } catch (Exception e) {
			        System.out.println(e.getMessage());
			        driver.navigate().back();
			    }
			}

	}

}

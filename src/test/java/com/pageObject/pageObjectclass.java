package com.pageObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.TestNGtests.DatabaseClass;


public class pageObjectclass {

	WebDriver driver;
	WebDriverWait wait;
	private JavascriptExecutor js;
	DatabaseClass db;
	List<Recipe> allRecipesList = new ArrayList<Recipe>();
	String tableName = "recipes";
	
	 @FindBy(xpath = "//a[text()='Recipes List']")    public WebElement recipes_list;
	 @FindBy(xpath = "//h5//a")	    public List<WebElement> recipes;
	 @FindBy(xpath="//h4[@class='rec-heading']")   public WebElement recipeTitleElement;	    
	 @FindBy(xpath ="(//p[@class='mb-0 font-size-13'])[1]")  public  WebElement preparation_time;
	 @FindBy(xpath ="(//p[@class='mb-0 font-size-13'])[2]")  public  WebElement cooking_time;
	 @FindBy(xpath ="(//p[@class='mb-0 font-size-13'])[3]")  public  WebElement total_time;
	 @FindBy(xpath ="(//div[@class='content']//p)[3]")  public  WebElement noOfServings;
	 @FindBy(xpath="//ul[@class='tags-list']//li")   public  List<WebElement> tags;
	 @FindBy(xpath = "//a[starts-with(@href, '/recipes-for-') and not(contains(@href, 'cuisine')) and not(contains(@href, 'course')) and not(contains(@href, 'occasion'))]")
	 public WebElement foodCategory;
	 @FindBy(xpath = "//div[@id=\"ingredients\"]/ul")	public List<WebElement> ingredientsList;
	 @FindBy(xpath = "//a[contains(text(), 'Next')]")   public WebElement nextPageButton;
	 @FindBy(xpath = "//a[@class='page-link' and text()='Next']")  public WebElement pageNextButton;
	 
	 
	  public pageObjectclass(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	  // Method to click on recipe list
		public void clickRecipeList() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipes_list);
			wait.until(ExpectedConditions.elementToBeClickable(recipes_list));
			recipes_list.click();
		} catch (ElementClickInterceptedException e) {
				clickRecipeList();
		}
	}

	// Method to remove ads	
	public void removeAds() {
		try {
			js = (JavascriptExecutor) driver;
			js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate');"
					+ "while (elements.length > 0) elements[0].remove();");
		} catch (Exception e) {
			System.out.println("Ads removed" + e.getMessage());
		}
	}
	
	// Method for navigation
	public void click_on_recipes_with_pagination() throws SQLException, TimeoutException {
		db = new DatabaseClass();
		db.createDatabase();
 		db.connect();
 		db.createTable(tableName);
		driver.get("https://www.tarladalal.com/recipes/");
	    int totalPages = 3; // change based on site total pages
	    for (int page = 1; page <= totalPages; page++) {
	        System.out.println(" Scraping Page: " + page);
           
	        // Scrape current page recipes
	        click_on_recipes(); // Method to get all required categories
	        insertRecipesIntoTable("recipes", allRecipesList);
	        // Click next page (except after last page)
	        if (page != totalPages) {
	            try {
	                //nextPageButton.click();
	            	String currentUrl = driver.getCurrentUrl();	
	            	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextPageButton);
	            	Thread.sleep(500); // wait for scroll
	            	((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextPageButton);
	            	wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
	            } catch (Exception e) {
	                System.out.println("Failed to go next page: " + e.getMessage());
	                break;
	            }
	        }
	    }
	    insertRecipesIntoTable("recipes", allRecipesList); // insert method to add values to the table
	}
	

	// Method to get all categories
	public void click_on_recipes() {
		js.executeScript("window.scrollBy(0, 300);");
		 for (int i = 0; i < 24; i++) {
			    try {
			    	String mainWindow = driver.getWindowHandle();  // save main window

		            // Open recipe link in new tab using JS
		            js.executeScript("window.open(arguments[0]);", recipes.get(i).getAttribute("href"));

		            // Switch to new tab
		            for (String windowHandle : driver.getWindowHandles()) {
		                if (!windowHandle.equals(mainWindow)) {
		                    driver.switchTo().window(windowHandle);
		                    break;
		                }
		            }

					wait.until(ExpectedConditions.visibilityOf(recipeTitleElement));

			        String recipeUrl = driver.getCurrentUrl();
			        String[] parts = recipeUrl.split("-");
			        String recipeId = parts[parts.length - 1].replace("r", "");
			        System.out.println("Recipe ID: " + recipeId);

			        String recipetitle = recipeTitleElement.getText();
			        System.out.println("Recipe_Name: " + recipetitle);

			        String recipeCategory = "Recipe_category";
					System.out.println("Recipe_category");
					
					String AllFoodCategory = foodCategory.getText();
					System.out.println("Food category "+AllFoodCategory);
					
					String ingredients = "Ingredients";
		             System.out.println("Ingredients");
					
		             String preparationTime = preparation_time.getText();
					System.out.println(preparationTime );
					

					String cookingTime =cooking_time.getText();
					List<String> tagTexts = new ArrayList<>();
					for (WebElement tag : tags) {
						 tagTexts.add(tag.getText());
					}	
					
					List<String> currentIngredients = new ArrayList<>();
		            for (WebElement ingredient : ingredientsList) {
		                String ingText = ingredient.getText().toLowerCase().trim();
		                currentIngredients.add(ingText);
		            }
		            
		           			        
			        for(WebElement tag : tags) {
			        String tagNames =	tag.getText();
			        System.out.println(tagNames);       	
			        	
			        }
			        String noOfServing = noOfServings.getText();				
					
					Recipe recipe = new Recipe();
					recipe.setRecipeID(recipeId);
					recipe.setRecipeName(recipetitle);
					recipe.setRecipeCategory(recipeCategory);
					recipe.setFoodCategory(AllFoodCategory);
					recipe.setIngredients(ingredients);
					recipe.setPreperationTime(preparationTime);
					recipe.setCookingTime(cookingTime);
					recipe.setTags("tags");
					recipe.setNumOfServings(noOfServing);
					recipe.setCuisineCategory("Cuisine_category");
					recipe.setRecipeDescription("Recipe_Description");
					recipe.setPreparationMethod("Preparation_method");
					recipe.setNutritionValues("Nutrient_Values");
					recipe.setRecipeUrl("Recipe_URL");
					
					// Add to list
					allRecipesList.add(recipe);
    

					driver.close();
		            driver.switchTo().window(mainWindow);
			    } catch (Exception e) {
			        System.out.println(e.getMessage());
			        			    }
			}

	}
	
	public void insertRecipesIntoTable(String tableName, List<Recipe> recipes) throws SQLException {
		for (Recipe recipe : recipes) {
			db.insertData(tableName, recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeDescription(),
					recipe.getIngredients(), recipe.getPreperationTime(), recipe.getCookingTime(),
					recipe.getPreparationMethod(), recipe.getNumOfServings(), recipe.getCuisineCategory(),
					recipe.getFoodCategory(), recipe.getRecipeCategory() ,recipe.getTags(), recipe.getNutritionValues(), recipe.getRecipeUrl());
		}
	}

}

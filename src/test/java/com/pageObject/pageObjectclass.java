package com.pageObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.TestNGtests.dataBaseClass;
import com.Utilities.ExcelDataReader;
import com.Utilities.configReader;

public class pageObjectclass {

	WebDriver driver;
	WebDriverWait wait;
	private JavascriptExecutor js;
	dataBaseClass db;
	configReader cofgreader;
	public static String[] EGGETARION_ELEMINATE_OPTIONS = new String[] { "veggie", "eggplant", "without egg",
			"eggless" };
	public static String[] VEGAN_ELEMINATE_OPTIONS = new String[] { "egg", "milk", "honey", "butter", "cheese", "ghee",
			"gelatin", "mayonnaise", "cream", "whey", "casein", "paneer" };
	public static final String[] RECIPE_CATEGORY_OPTIONS = { "breakfast", "lunch", "snack", "dinner" };

	List<Recipe> lfvEliminationRecipes = new ArrayList<Recipe>();
	List<Recipe> allRecipesList = new ArrayList<Recipe>();
	String[] tableNames = { "recipes", "LFVEliminatedRecipe" };
	@FindBy(xpath = "//a[text()='Recipes List']")
	public WebElement recipes_list;
	@FindBy(xpath = "//h5//a")
	public List<WebElement> recipes;
	@FindBy(xpath = "//h4[@class='rec-heading']")
	public WebElement recipeTitleElement;
	@FindBy(xpath = "(//p[@class='mb-0 font-size-13'])[1]")
	public WebElement preparation_time;
	@FindBy(xpath = "(//p[@class='mb-0 font-size-13'])[2]")
	public WebElement cooking_time;
	@FindBy(xpath = "(//p[@class='mb-0 font-size-13'])[3]")
	public WebElement total_time;
	@FindBy(xpath = "(//div[@class='content']//p)[3]")
	public WebElement noOfServings;
	@FindBy(xpath = "//ul[@class='tags-list']//li")
	public List<WebElement> tags;
	@FindBy(xpath = "//a[starts-with(@href, '/recipes-for-') and not(contains(@href, 'cuisine')) and not(contains(@href, 'course')) and not(contains(@href, 'occasion'))]")
	public WebElement foodCategory;
	@FindBy(xpath = "//div[@id=\"ingredients\"]/ul")
	public List<WebElement> ingredientsList;
	@FindBy(xpath = "//a[contains(text(), 'Next')]")
	public WebElement nextPageButton;
	@FindBy(xpath = "//a[@class='page-link' and text()='Next']")
	public WebElement pageNextButton;
	@FindBy(xpath = "//*[contains(text(), 'Breakfast')] | //*[contains(text(), 'Snacks')] | //*[contains(text(), 'Dinner')] | //*[contains(text(), 'Lunch')]")
	WebElement recipeCategory;

	@FindBy(xpath = "//p[text()='You are here: ']//span[3]")
	public WebElement cusine_category;
	@FindBy(xpath = "//*[@id=\"aboutrecipe\"]/p[1]")
	public WebElement aboutrecipe;

	@FindBy(xpath = "//a[@class='scroll-link' and @href='#nutrients']")
	WebElement nutrientValue;

	@FindBy(xpath = "//figure/table")
	WebElement nutrientTable;

	@FindBy(xpath = "//*[@id='methods']")
	WebElement prepMethod;

	public pageObjectclass(WebDriver driver, WebDriverWait wait) {

		this.driver = driver;
		this.wait = wait;
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	/*
	 * private boolean isRecipeValid(List<String> ingredients) throws
	 * InvalidFormatException, IOException {
	 * 
	 * List<String> eliminateList=
	 * getListFromExcel("Eliminate",cofgreader.getSheetName()); List<String> addList
	 * = getListFromExcel("Add",cofgreader.getSheetName() );
	 * 
	 * // Check for eliminate for (String ing : ingredients) { for (String bad :
	 * eliminateList) { if (ing.contains(bad)) { return false; //Recipe invalid due
	 * to bad ingredient } } }
	 * 
	 * // Check for add (must contain at least 1 from add list) boolean hasAdd =
	 * false; for (String ing : ingredients) { for (String good : addList) { if
	 * (ing.contains(good)) { hasAdd = true; break; } } if (hasAdd) break; }
	 * 
	 * return hasAdd; // valid only if at least one from add list }
	 */

	public List<Recipe> filterRecipesByList(List<Recipe> recipeList, String listName, String sheetName,
			boolean toBeNotIncluded) throws InvalidFormatException, IOException {
		List<Recipe> filteredRecipes = new ArrayList<>();

		List<String> filterItems = getListFromExcel(listName, sheetName);

		for (Recipe recipe : recipeList) {
			/*
			 * String ingredients = recipe.getIngredients().toLowerCase(); boolean
			 * matchFound = false;
			 * 
			 * for (String filter : filterItems) { if
			 * (ingredients.contains(filter.toLowerCase())) { matchFound = true; break; } }
			 * 
			 * if (toBeNotIncluded) { // Exclude recipes that contain any of the filter
			 * items if (!matchFound) { filteredRecipes.add(recipe); } } else { // Include
			 * recipes that contain at least one of the filter items if (matchFound) {
			 * filteredRecipes.add(recipe); } } }
			 */
			String ingredients = recipe.getIngredients().toLowerCase();
			boolean matchFound = filterItems.stream().anyMatch(filter -> ingredients.contains(filter.toLowerCase()));
			if (toBeNotIncluded ? !matchFound : matchFound) {
				filteredRecipes.add(recipe);
			}
		}

		return filteredRecipes;
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

	public void removeAds() {
		try {
			js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate');"
					+ "while (elements.length > 0) elements[0].remove();");
		} catch (Exception e) {
			System.out.println("Ads removed" + e.getMessage());
		}
	}

	public boolean navigateToNextPage() {
		try {
			if (pageNextButton.isDisplayed() && pageNextButton.isEnabled()) {
				clickUsingJavascriptExecutor(pageNextButton);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Next page not available or failed to navigate: " + e.getMessage());
		}
		return false;
	}

	// Method for navigation
	public void click_on_recipes_with_pagination()
			throws SQLException, TimeoutException, InvalidFormatException, IOException {
		db = new dataBaseClass();
		db.createDatabase();
		db.connect();
		for (String tableName : tableNames) {
			db.createTable(tableName);
		}
		driver.get("https://www.tarladalal.com/recipes/");
		/*
		 * int totalPages = 5; // change based on site total pages for (int page = 1;
		 * page <= totalPages; page++) { System.out.println(" Scraping Page: " + page);
		 * 
		 * // Scrape current page recipes click_on_recipes(); // Method to get all
		 * required categories // insertRecipesIntoTable("recipes", allRecipesList); //
		 * Click next page (except after last page) if (page != totalPages) { try {
		 * //nextPageButton.click(); String currentUrl = driver.getCurrentUrl();
		 * ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].scrollIntoView(true);", nextPageButton);
		 * Thread.sleep(500); // wait for scroll ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].click();", nextPageButton);
		 * wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl))); }
		 * catch (Exception e) { System.out.println("Failed to go next page: " +
		 * e.getMessage()); break; } } }
		 */
		boolean hasNextPage;
		int page = 1;

		do {
			System.out.println("Scraping Page: " + page);

			click_on_recipes(); // Scrape current page recipes

			hasNextPage = navigateToNextPage(); // Try moving to next page
			page++;

		} while (hasNextPage);
		insertRecipesIntoTable("recipes", allRecipesList);
		insertRecipesIntoTable("LFVEliminatedRecipe", lfvEliminationRecipes);// insert method to add values to the table
	}

	// Method to get all categories
	public void click_on_recipes() throws InvalidFormatException, IOException {

		// driver.get("https://www.tarladalal.com/recipes/");
		js.executeScript("window.scrollBy(0, 100);");
		for (int i = 0; i < 24; i++) {
			try {
				String mainWindow = driver.getWindowHandle(); // save main window

				// Open recipe link in new tab using JS
				js.executeScript("window.open(arguments[0]);", recipes.get(i).getAttribute("href"));

				// Switch to new tab
				for (String windowHandle : driver.getWindowHandles()) {
					if (!windowHandle.equals(mainWindow)) {
						driver.switchTo().window(windowHandle);
						break;
					}
				}

				// Wait for page to load
				wait.until(ExpectedConditions.visibilityOf(recipeTitleElement));
				String recipeUrl = driver.getCurrentUrl();
				String[] parts = recipeUrl.split("-");
				String recipeId = parts[parts.length - 1].replace("r", "");
				System.out.println("Recipe ID: " + recipeId);

				//String recipetitle = recipeTitleElement.getText();
				//System.out.println("Recipe_Name: " + recipetitle);
				String recipetitle = "";
				try {
					if (recipeTitleElement.isDisplayed()) {
						recipetitle = recipeTitleElement.getText();
					}
				} catch (NoSuchElementException ex) {
					recipetitle = "recipetitle are not listed";
				}
				System.out.println("recipetitle: " + recipetitle);
				
				
				String preparationTime = preparation_time.getText();
				System.out.println(preparationTime);
				
				/*String preparationTime = "";
				try {
					if (preparation_time.isDisplayed()) {
						preparationTime = preparation_time.getText();
					}
				} catch (NoSuchElementException ex) {
					preparationTime = "preparationTime are not listed";
				}
				System.out.println("preparationTime: " + preparationTime);*/

				String cookingTime = cooking_time.getText();
				/*String cookingTime = "";
				try {
					if (cooking_time.isDisplayed()) {
						cookingTime = cooking_time.getText();
					}
				} catch (NoSuchElementException ex) {
					preparationTime = "cookingTime are not listed";
				}
				System.out.println("cookingTime: " + cookingTime);*/
				
				
				// String recipeDescription = aboutrecipe.getText().replace("\n", "");

				String recipeDescription = "";
				try {
					if (nutrientTable.isDisplayed()) {
						recipeDescription = aboutrecipe.getText().replace("\n", "");
					}
				} catch (NoSuchElementException ex) {
					recipeDescription = "Recipe Descriptionare not listed";
				}
				System.out.println("RecipeDescription: " + recipeDescription);
				
				List<String> currentIngredients = new ArrayList<>();
				for (WebElement ingredient : ingredientsList) {
					String ingText = ingredient.getText().toLowerCase().trim();
					currentIngredients.add(ingText);
				}

				String noOfServing = noOfServings.getText();

				String tagloca = "";
				for (WebElement tag : tags) {
					// tagTexts.add(tag.getText());
					tagloca = tagloca + " " + tag.getText();
				}
				System.out.println("Recipe Tag:" + tagloca);

				String recipeCategory = "";
				for (String recipeCategoryOption : RECIPE_CATEGORY_OPTIONS) {
					if (tagloca.toLowerCase().contains(recipeCategoryOption.toLowerCase())) {
						recipeCategory = recipeCategoryOption;
						break;
					}
				}

				System.out.println("Recipe Category:" + recipeCategory);

				String ingredientsName = String.join(" ", currentIngredients);
				System.out.println("Ingredients Name : " + ingredientsName);
				// Prep_method
				removeAds();
				String prepMethodTxt = prepMethod.getText().replace("\n", "");
				System.out.println("Preparation Method : " + prepMethodTxt);

				// Nutrient Values
				removeAds();
				clickUsingJavascriptExecutor(nutrientValue);

				String nutValues = "";
				try {
					if (nutrientTable.isDisplayed()) {
						nutValues = nutrientTable.getText();
					}
				} catch (NoSuchElementException ex) {
					nutValues = "Nutrient values are not listed";
				}
				System.out.println("Nutrient Values: " + nutValues);

				// Recipe_URL
				removeAds();
				String recipeURL = driver.getCurrentUrl();
				System.out.println("Recipe URL  :" + recipeURL);

				String foodCategory = "Vegetarian";// by default food category is vegetarian
				String combinedText = (tags + ingredientsName).toLowerCase();// combining tags and ingredientname for
																				// filtering
				// using streams to check if there is any match with the ingredients in
				// arraylist and the string
				boolean isEggetarian = !Arrays.stream(EGGETARION_ELEMINATE_OPTIONS).anyMatch(combinedText::contains);
				boolean isVegan = !Arrays.stream(VEGAN_ELEMINATE_OPTIONS).anyMatch(combinedText::contains);
				if (combinedText.contains("egg") && isEggetarian) {
					foodCategory = "Eggetarian";
				} else if (combinedText.contains("jain")) {
					foodCategory = "Jain";
				} else if (isVegan || combinedText.contains("vegan") || recipeUrl.contains("vegan")) {
					foodCategory = "Vegan";
				} else if (combinedText.contains("non-veg")) {
					foodCategory = "Non-Veg";
				}
				// logger.info("Food Category : " + foodCategory );
				System.out.println("Food Category : " + foodCategory);
				System.out.println("Recipe Description: " + recipeDescription);
				// String cusineCategory = cusine_category.getText();

				String cusineCategory = "";
				try {
					if (cusine_category.isDisplayed()) {
						cusineCategory = cusine_category.getText();
					}
				} catch (NoSuchElementException ex) {
					cusineCategory = "cusinecategory are not listed";
				}
				System.out.println("Cusine Category: " + cusineCategory);
				// Apply your rule here
				/*
				 * if (isRecipeValid(currentIngredients)) {
				 * 
				 * System.out.println("========= Recipe accepted: " + recipetitle +
				 * "========="); System.out.println("ID: " + recipeId);
				 * System.out.println("FoodCategory: " + foodCategory);
				 * System.out.println("Recipe Category:" + recipeCategory);
				 * 
				 * System.out.println("Ingredients:"); for (String ing : currentIngredients) {
				 * System.out.println("- " + ing);
				 * 
				 * } System.out.println("==========================================="); //Here
				 * you can save recipe to Excel/DB/file etc. } else {
				 * System.out.println("=====Recipe rejected: " + recipetitle + "========="); }
				 */
				Recipe recipe = new Recipe();
				recipe.setRecipeID(recipeId);
				recipe.setRecipeName(recipetitle);
				recipe.setRecipeCategory(recipeCategory);
				recipe.setFoodCategory(foodCategory);
				recipe.setIngredients(ingredientsName);
				recipe.setPreperationTime(preparationTime);
				recipe.setCookingTime(cookingTime);
				recipe.setTags(tagloca);
				recipe.setNumOfServings(noOfServing);
				recipe.setCuisineCategory(cusineCategory);
				recipe.setRecipeDescription(recipeDescription);
				recipe.setPreparationMethod(prepMethodTxt);
				recipe.setNutritionValues(nutValues);
				recipe.setRecipeUrl(recipeURL);
				// Add to list
				allRecipesList.add(recipe);

				// Close recipe tab and switch back
				driver.close();
				driver.switchTo().window(mainWindow);

			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
			} finally {
				lfvEliminationRecipes = filterRecipesByList(allRecipesList, "Eliminate", cofgreader.getSheetName(),
						true);
			}
		}

	}

	public void insertRecipesIntoTable(String tableName, List<Recipe> recipes) throws SQLException {
		for (Recipe recipe : recipes) {
			db.insertData(tableName, recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeDescription(),
					recipe.getIngredients(), recipe.getPreperationTime(), recipe.getCookingTime(),
					recipe.getPreparationMethod(), recipe.getNumOfServings(), recipe.getCuisineCategory(),
					recipe.getFoodCategory(), recipe.getRecipeCategory(), recipe.getTags(), recipe.getNutritionValues(),
					recipe.getRecipeUrl());
		}
	}

	public void clickUsingJavascriptExecutor(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public List<String> getListFromExcel(String listName, String sheetname)
			throws org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException {
		ExcelDataReader reader = new ExcelDataReader();
		cofgreader = new configReader();
		String filepath = configReader.getexcelfilepath();
		sheetname = cofgreader.getSheetName();

		List<Map<String, String>> list = reader.getData(filepath, sheetname);

		List<String> listWithValues = new ArrayList<>();

		for (Map<String, String> row : list) {
			String expectedResult = row.get(listName);
			if (expectedResult != null) { // Avoid null values
				listWithValues.add(expectedResult.trim());
			}
		}
		return listWithValues;

	}

}

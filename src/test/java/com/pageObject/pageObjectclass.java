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
import org.openqa.selenium.NoSuchElementException;

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
	static configReader cofgreader;

	public static String[] EGGETARION_ELEMINATE_OPTIONS = new String[] { "veggie", "eggplant", "without egg",
			"eggless" };
	public static String[] VEGAN_ELEMINATE_OPTIONS = new String[] { "egg", "milk", "honey", "butter", "cheese", "ghee",
			"gelatin", "mayonnaise", "cream", "whey", "casein", "paneer" };
	public static final String[] RECIPE_CATEGORY_OPTIONS = { "breakfast", "lunch", "snack", "dinner" };


	public static final String LFV_TO_ELIMINATE = "pork, meat, poultry, fish, sausage, ham, salami, bacon, milk, cheese, yogurt, butter, ice cream, egg, prawn, oil, olive oil, coconut oil, soybean oil, corn oil, safflower oil, sunflower oil, rapeseed oil, peanut oil, cottonseed oil, canola oil, mustard oil, cereals, bread, maida, atta, sooji, poha, cornflake, cornflour, pasta, white rice, pastry, cakes, biscuit, soy, soy milk, white miso paste, soy sauce, soy curls, edamame, soy yogurt, soy nut, tofu, pies, chip, cracker, potatoe, sugar, jaggery, glucose, fructose, corn syrup, cane sugar, aspartame, cane solid, maltose, dextrose, sorbitol, mannitol, xylitol, maltodextrin, molasses, brown rice syrup, splenda, nutra sweet, stevia, barley malt";
	  public static final String LCHF_TO_ELIMINATE = "Ham,sausage,tinned fish,tuna,sardines,yams,beets,parsnip,turnip,"
	  		+ "rutabagas,carrot,yuca,kohlrabi,celery root,horseradish,daikon,jicama,radish,pumpkin,squash,Whole fat milk,"
	  		+ "low fat milk,fat free milk,Evaporated milk,condensed milk,curd,buttermilk,ice cream,flavored milk,"
	  		+ "sweetened yogurt,soft cheese,grain,Wheat,oat,barely,rice,millet,jowar,bajra,corn,dal,lentil,banana,mango,papaya,plantain,"
	  		+ "apple,orange,pineapple,pear,tangerine,all melon varieties,peach,plum,nectarine,Avocado,olive oil,coconut oil,soybean oil,"
	  		+ "corn oil,safflower oil,sunflower oil,rapeseed oil,peanut oil,cottonseed oil,canola oil,mustard oil,sugar,jaggery,glucose,"
	  		+ "fructose,corn syrup,cane sugar,aspartame,cane solids,maltose,dextrose,"
	  		+ "sorbitol,mannitol,xylitol,maltodextrin,molasses,brown rice syrup,splenda,nutra sweet,stevia,barley malt,potato,corn,pea";
	  public static final String LCHF_ADD = "Fish,prawn,poultry,egg,Onion,Garlic,turmeric,Ginger,Butter,ghee,hard cheese,paneer,cottage cheese,sour cream,greek yogurt,hung curd,almond,pistachio,brazil nut,walnut,pine nut,hazelnut,macadamia nut,pecan,hemp seed,sunflower seed,sesame seed,chia seed,flax seed,Blueberry,blackberry,strawberry";
	    public static final String LFV_ADD = "Lettuce,kale,chard,arugula,spinach,cabbage,pumpkin,sweet potatoes,purple potatoes,yams,turnip,parsnip,karela,bittergourd,beet,carrot,cucumber,red onion,white onion,broccoli,cauliflower,carrot,celery,artichoke,bell pepper,mushroom,tomato,sweet and hot pepper,banana,mango,papaya,plantain,apple,orange,pineapple,pear,tangerine,all berry varieties,all melon varieties,peach,plum,nectarine,Avocado,Amaranth,Rajgira,Ramdana Barnyard,Sanwa,Samvat ke chawal buckwheat,kuttu finger millet,Ragi,Nachni foxtail millet,kangni,kakum kodu,kodon,little millet,moraiyo,kutki,shavan,sama pearl millet,bajra,broom corn millet,chena sorghum,jowar,Lentil,Pulse,Moong dhal,masoor dhal,toor dhal,urd dhal,lobia,rajma,matar,all forms of chana,almond,cashew,pistachio,brazil nut,walnut,pine nut,hazelnut,macadamia nut,pecan,peanut,hemp seed,sun flower seed,sesame seed,chia seed,flax seed";
	    public static final String LFV_TO_ADD = "Butter,Ghee,salmon,mackerel,sardines";
	    List<Recipe> allRecipesList = new ArrayList<Recipe>();
	List<Recipe> lfvEliminationRecipes = new ArrayList<Recipe>();
	List<Recipe> lchfAddRecipes = new ArrayList<Recipe>();
	List<Recipe> lchfEliminationRecipes = new ArrayList<Recipe>();
	List<Recipe> lfvAddRecipes = new ArrayList<Recipe>();
	List<Recipe> lfvToAddRecipes = new ArrayList<Recipe>();
	//List<Recipe> lfvToAvoidRecipes  = new ArrayList<Recipe>();
	
	
	String[] tableNames = { "recipes", "LCHFEliminatedRecipe","lchfAddRecipes","lfvEliminationRecipes","lfvAddRecipes","lfvToAddRecipes"}; 

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
	//@FindBy(xpath = "//a[@class='page-link' and text()='Next']")
	//public WebElement pageNextButton;
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
			if ( nextPageButton.isDisplayed() &&  nextPageButton.isEnabled()) {
				clickUsingJavascriptExecutor( nextPageButton);
				return true;
			}
		} catch (Exception e) {
			System.out.println("Next page not available or failed to navigate: " + e.getMessage());
		}
		return false;
	}


	// Method for navigation
	public void click_on_recipes_with_pagination()
			throws SQLException, TimeoutException, IOException {
		db = new dataBaseClass();
		db.createDatabase();
		db.connect();
		for (String tableName : tableNames) {
			db.createTable(tableName);
		}
  driver.get("https://www.tarladalal.com/recipes/");
	boolean hasNextPage;
		int page = 1;

		do {
			System.out.println("Scraping Page: " + page);

			click_on_recipes(); // Scrape current page recipes

			hasNextPage = navigateToNextPage(); // Try moving to next page
			page++;

		} while (hasNextPage);
		
		lchfEliminationRecipes = filterRecipes(allRecipesList,LCHF_TO_ELIMINATE, true);					
		lchfAddRecipes = filterRecipes(lchfEliminationRecipes,LCHF_ADD, false);
		//lchfToAvoid = filterRecipes(,,false);
		lfvEliminationRecipes = filterRecipes(allRecipesList, LFV_TO_ELIMINATE, true);
		lfvAddRecipes = filterRecipes(lfvEliminationRecipes, LFV_ADD, false);
		lfvToAddRecipes = filterRecipes(lfvEliminationRecipes,LFV_TO_ADD, false);
				
		System.out.println("******************************************************************");
		System.out.println(lfvEliminationRecipes);
		System.out.println("******************************************************************");
		System.out.println(lchfEliminationRecipes);
		System.out.println("******************************************************************");
		System.out.println(lchfAddRecipes);
		System.out.println("******************************************************************");
		System.out.println(lfvAddRecipes);
		System.out.println("******************************************************************");
		System.out.println(lfvToAddRecipes);
		System.out.println("******************************************************************");
		insertRecipesIntoTable("recipes", allRecipesList);
		insertRecipesIntoTable("lfvEliminationRecipes", lfvEliminationRecipes);// insert method to add values to the table
		insertRecipesIntoTable("LCHFEliminatedRecipe", lchfEliminationRecipes);
		insertRecipesIntoTable("lchfAddRecipes", lchfAddRecipes);
		insertRecipesIntoTable("lfvAddRecipes", lfvAddRecipes);
		insertRecipesIntoTable("lfvToAddRecipes", lfvToAddRecipes);
	}


	// Method to get all categories
	public void click_on_recipes() throws  IOException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 100);");
		int i = 0;
		int recipeCount = recipes.size();
		System.out.println("Found recipes count: " + recipeCount);
		do {
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

				String recipetitle;
				try {
				    recipetitle = (recipeTitleElement != null) ? recipeTitleElement.getText().trim() : "Recipe title element is null";
				    if (recipetitle.isEmpty()) {
				        recipetitle = "Recipe title is empty";
				    }
				} catch (Exception ex) {
				    recipetitle = "Recipe title fetch failed: " + ex.getClass().getSimpleName();
				}
				System.out.println("recipetitle: " + recipetitle);
				
				
				String preparationTime = preparation_time.getText();
				System.out.println(preparationTime);
				
				String cookingTime = cooking_time.getText();

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
				recipe.setNutritionValues("nutValues");
				recipe.setRecipeUrl(recipeURL);
				// Add to list
				allRecipesList.add(recipe);

				// Close recipe tab and switch back
				driver.close();
		        // Switch back to main window
		        driver.switchTo().window(mainWindow);
		    } catch (Exception ex) {
		        System.out.println("Error while processing recipe " + (i + 1) + ": " + ex.getMessage());
		    }
		    i++;
		} while (i < recipeCount);

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
	
	public List<Recipe> filterRecipes(List<Recipe> recipeList, String filterString, boolean toBeNotIncluded) {
	    List<Recipe> filteredRecipes = new ArrayList<>();
	    
	    // Split the filterString into individual terms, trim and lowercase them
	    String[] filters = filterString.toLowerCase().split(",");
	    
	    for (Recipe recipe : recipeList) {
	        String ingredients = recipe.getIngredients().toLowerCase();
	        boolean matchFound = false;

	        for (String filter : filters) {
	            filter = filter.trim(); // remove whitespace
	            if (ingredients.contains(filter)) {
	                matchFound = true;
	                break;
	            }
	        }

	        if (toBeNotIncluded) {
	            if (!matchFound) {
	                filteredRecipes.add(recipe);
	            }
	        } else {
	            if (matchFound) {
	                filteredRecipes.add(recipe);
	            }
	        }
	    }

	    return filteredRecipes;

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
		
}}

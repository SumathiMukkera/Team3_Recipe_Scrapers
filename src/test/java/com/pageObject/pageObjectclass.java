package com.pageObject;

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

public class pageObjectclass {

	WebDriver driver;
	WebDriverWait wait;
	private JavascriptExecutor js;
	public static String[] EGGETARION_ELEMINATE_OPTIONS = new String[] { "veggie", "eggplant", "without egg",
			"eggless" };
	public static String[] VEGAN_ELEMINATE_OPTIONS = new String[] { "egg", "milk", "honey", "butter", "cheese", "ghee",
			"gelatin", "mayonnaise", "cream", "whey", "casein", "paneer" };
	public static final String[] RECIPE_CATEGORY_OPTIONS = { "breakfast", "lunch", "snack", "dinner" };

	public pageObjectclass(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[text()='Recipes List']")
	public WebElement recipes_list;

	@FindBy(xpath = "//h5//a")
	public List<WebElement> recipes;

	@FindBy(xpath = "//h4[@class='rec-heading']")
	WebElement recipeTitleElement;
	@FindBy(xpath = "//a[starts-with(@href, '/recipes-for-') and not(contains(@href, 'cuisine')) and not(contains(@href, 'course')) and not(contains(@href, 'occasion'))]")
	WebElement foodCategory;
	@FindBy(xpath = "//*[contains(text(), 'Breakfast')] | //*[contains(text(), 'Snacks')] | //*[contains(text(), 'Dinner')] | //*[contains(text(), 'Lunch')]")
	WebElement recipeCategory;

	@FindBy(xpath = "//div[@id=\"ingredients\"]/ul")
	public List<WebElement> ingredientsList;
	@FindBy(xpath = "//a[contains(text(), 'Next')]")
	public WebElement nextPageButton;
	@FindBy(xpath = "//ul[@class='tags-list']//li")
	public List<WebElement> tags;

	public void click_on_recipes_with_pagination() {
		driver.get("https://www.tarladalal.com/recipes/");
		int totalPages = 5; // change based on site total pages
		for (int page = 1; page <= totalPages; page++) {
			System.out.println(" Scraping Page: " + page);

			// Scrape current page recipes
			click_on_recipes(); // your existing method

			// Click next page (except after last page)
			if (page != totalPages) {
				try {
					// nextPageButton.click();
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
	}

	private boolean isRecipeValid(List<String> ingredients) {
		List<String> eliminateList = Arrays.asList("pork", "meat", "poultry", "fish", "sausage", "ham", "salami",
				"bacon", "milk", "cheese", "yogurt", "butter", "ice cream", "egg", "prawn", "oil", "olive oil",
				"coconut oil", "soybean oil", "corn oil", "safflower oil", "sunflower oil", "rapeseed oil",
				"peanut oil", "cottonseed oil", "canola oil", "mustard oil", "cereals", "bread", "maida", "atta",
				"sooji", "poha", "cornflake", "cornflour", "pasta", "white rice", "pastry", "cakes", "biscuit", "soy",
				"soy milk", "white miso paste", "soy sauce", "soy curls", "edamame", "soy yogurt", "soy nut", "tofu",
				"pies", "chip", "cracker", "potatoe", "sugar", "jaggery", "glucose", "fructose", "corn syrup",
				"cane sugar", "aspartame", "cane solid", "maltose", "dextrose", "sorbitol", "mannitol", "xylitol",
				"maltodextrin", "molasses", "brown rice syrup", "splenda", "nutra sweet", "stevia", "barley malt"

		);

		List<String> addList = Arrays.asList("lettuce", "kale", "chard", "arugula", "spinach", "cabbage", "pumpkin",
				"sweet potatoes", "purple potatoes", "yams", "turnip", "karela", "bittergourd", "beet", "carrot",
				"cucumber", "red onion", "white onion", "broccoli", "cauliflower", "celery", "artichoke", "bell pepper",
				"mushroom", "tomato", "sweet and hot pepper", "banana", "mango", "papaya", "plantain", "apple",
				"orange", "pineapple", "pear", "tangerine", "berry", "melon", "peach", "plum", "nectarine", "avocado",
				"amaranth", "rajgira", "ramdana", "sanwa", "samvat", "buckwheat", "kuttu", "ragi", "nachni",
				"foxtail millet", "kangni", "kakum", "kodu", "kodon", "little millet", "moraiyo", "kutki", "shavan",
				"sama", "pearl millet", "bajra", "broom corn millet", "chena", "sorghum", "jowar", "lentil", "pulse",
				"moong dhal", "masoor dhal", "toor dhal", "urd dhal", "lobia", "rajma", "matar", "chana", "almond",
				"cashew", "pistachio", "brazil nut", "walnut", "pine nut", "hazelnut", "macadamia nut", "pecan",
				"peanut", "hemp seed", "sun flower seed", "sesame seed", "chia seed", "flax seed");

		// Check for eliminate ❌
		for (String ing : ingredients) {
			for (String bad : eliminateList) {
				if (ing.contains(bad)) {
					return false; // ❌ Recipe invalid due to bad ingredient
				}
			}
		}

		// Check for add ✅ (must contain at least 1 from add list)
		boolean hasAdd = false;
		for (String ing : ingredients) {
			for (String good : addList) {
				if (ing.contains(good)) {
					hasAdd = true;
					break;
				}
			}
			if (hasAdd)
				break;
		}

		return hasAdd; // valid only if at least one from add list
	}

	public void clickRecipeList() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipes_list);
			// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

	public void click_on_recipes() {
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
				// recipes.get(i).click();
				String recipeUrl = driver.getCurrentUrl();
				// Split the URL by hyphen and 'r' to get the parts
				String[] parts = recipeUrl.split("-");
				// The recipe ID is the last part before 'r'
				// RecipeID
				String recipeId = parts[parts.length - 1].replace("r", "");
				System.out.println(recipeId);

				// Recipe Name
				String recipetitle = recipeTitleElement.getText();
				System.out.println(recipetitle);

				// List<String> tagTexts = new ArrayList<>();
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

				// Collect ingredients
				List<String> currentIngredients = new ArrayList<>();

				for (WebElement ingredient : ingredientsList) {
					String ingText = ingredient.getText().toLowerCase().trim();
					currentIngredients.add(ingText);
				}
				String ingredientsName = String.join(" ", currentIngredients);
				System.out.println("Ingredients Name : " + ingredientsName);

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

				// ✅ Apply your rule here
				if (isRecipeValid(currentIngredients)) {

					System.out.println("========= Recipe accepted: " + recipetitle + "=========");
					System.out.println("ID: " + recipeId);
					System.out.println("FoodCategory: " + foodCategory);
					System.out.println("Recipe Category:" + recipeCategory);

					System.out.println("Ingredients:");
					for (String ing : currentIngredients) {
						System.out.println("- " + ing);

					}
					System.out.println("===========================================");
					// 🔥 Here you can save recipe to Excel/DB/file etc.
				} else {
					System.out.println("=====Recipe rejected: " + recipetitle + "=========");
				}
				// driver.navigate().back();

				// Close recipe tab and switch back
				driver.close();
				driver.switchTo().window(mainWindow);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// }
		}
	}

}

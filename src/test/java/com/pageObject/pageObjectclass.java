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

	@FindBy(xpath = "//a[contains(text(),'Vegetable Manchurian, Veg Manchurian with gravy Re')]")
	public WebElement VegManchurian;

	public pageObjectclass(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[text()='Recipes List']")
	public WebElement recipes_list;

	/*
	 * @FindBy(xpath = "//span[text()='Close']") public WebElement dismiss_btn;
	 */
	@FindBy(xpath = "//span[text()='Close']")
	public List<WebElement> dismiss_btns;

	@FindBy(className = "two-line-text")
	public List<WebElement> recipes;

	public void clickRecipeList() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", recipes_list);
			// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(recipes_list));
			recipes_list.click();
		} catch (ElementClickInterceptedException e) {
			System.out.println("Click intercepted, trying to dismiss popup...");
			dismiss();
			clickRecipeList();
			// dismiss();

		}
	}

	/*
	 * public void dismiss() {
	 * 
	 * dismiss_btn.click(); }
	 */
	public void dismiss() {
		try {
			if (!dismiss_btns.isEmpty()) {
				WebElement btn = dismiss_btns.get(0);
				wait.until(ExpectedConditions.elementToBeClickable(btn));
				btn.click();
				System.out.println("Popup dismissed.");
			} else {
				System.out.println("No popup to dismiss.");
			}
		} catch (Exception e) {
			System.out.println("Dismiss failed or popup not present: " + e.getMessage());
		}
	}

	/*
	 * public void click_on_recipes() { for (int i = 1; i < 24; i++) { try {
	 * recipes.get(i).click(); driver.navigate().back(); //
	 * PageFactory.initElements(driver, this); } catch (Exception e) {
	 * System.out.println("Skipping index " + i + ": " + e.getMessage()); } }
	 */

	public void click_on_recipes() {
		VegManchurian.click();
        String url=driver.getCurrentUrl();
		//String url = "https://www.tarladalal.com/vegetable-manchurian--easy-chinese-recipe--4137r";
		String recipeId = url.substring(url.lastIndexOf('-') + 1);
		System.out.println(recipeId);

	}

}

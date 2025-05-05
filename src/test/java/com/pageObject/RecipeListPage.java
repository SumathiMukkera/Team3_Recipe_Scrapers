package com.pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class RecipeListPage {
    WebDriver  driver;

    @FindBy(xpath = "//a[@class='page-link' and text()='Next']")
    WebElement pageNextButton;  //Next button

    public RecipeListPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    RecipePage recipePageObject;

    public void parseAllRecipesOnAllPages() {

        boolean hasNextPage;
        driver.get("https://www.tarladalal.com/recipes/?page=310");
        do {

            parseAllRecipes();

            hasNextPage = navigateToNextPage();

        }while (hasNextPage);

    }

    public void clickUsingJavascriptExecutor(WebElement element) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        ex.executeScript("arguments[0].click();", element);
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

    public void  parseAllRecipes() {



    }


    private List<WebElement> getAllRecipes() {
        // Using the XPath locate all recipe links //section//div[contains(@class, 'recipe-block')]/div/a
        return null;
    }
}

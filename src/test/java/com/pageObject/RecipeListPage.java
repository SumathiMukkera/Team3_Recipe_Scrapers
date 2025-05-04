package com.pageObject;

import org.openqa.selenium.WebElement;

import java.util.List;

public class RecipeListPage {
    private RecipePage recipePageObject;

    public void parseAllRecipesOnAllPages() {
        boolean hasNextPage;

        do {

            parseAllRecipes();
            hasNextPage = navigateToNextPage();

        }while (hasNextPage);

    }

    private boolean navigateToNextPage() {
        return true;
    }

    private void parseAllRecipes() {

         List<WebElement> recipeLinks = getAllRecipes();
         for(WebElement recipeLink : recipeLinks) {
             recipeLink.click();
             recipePageObject.parseRecipeDetails();
         }
    }

    private List<WebElement> getAllRecipes() {
        // Using the XPath locate all recipe links //section//div[contains(@class, 'recipe-block')]/div/a
        return null;
    }
}

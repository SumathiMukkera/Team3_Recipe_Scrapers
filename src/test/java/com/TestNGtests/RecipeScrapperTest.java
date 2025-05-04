package com.TestNGtests;

import com.pageObject.HomePage;
import com.pageObject.RecipeListPage;
import org.testng.annotations.Test;

public class RecipeScrapperTest {

    private HomePage homePage;
    private RecipeListPage recipeListPage;
    private RecipeDatabase database;

    @Test
    void scrapeRecipe() {
        homePage.launch();
        homePage.navigateToRecipeList();

        recipeListPage.parseAllRecipesOnAllPages();

        database.persistAllRecipes();
    }

}

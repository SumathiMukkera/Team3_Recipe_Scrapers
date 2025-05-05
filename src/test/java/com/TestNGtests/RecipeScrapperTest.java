package com.TestNGtests;

import com.BaseClass.baseClass;
import com.pageObject.HomePage;
import com.pageObject.RecipeListPage;
import org.testng.annotations.Test;

public class RecipeScrapperTest  extends baseClass {

    private HomePage homePage = new HomePage();
    private RecipeListPage recipeListPage;
    private RecipeDatabase database = new RecipeDatabase();

    @Test
    void scrapeRecipe() {
        homePage.launch();
        homePage.navigateToRecipeList();

        recipeListPage = new RecipeListPage(driver);
        recipeListPage.parseAllRecipesOnAllPages();

        database.persistAllRecipes();
    }

}

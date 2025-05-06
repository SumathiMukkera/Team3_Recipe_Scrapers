package com.TestNGtests;

import com.BaseClass.baseClass;
import com.pageObject.HomePage;
import com.pageObject.RecipeListPage;
import com.pageObject.pageObjectclass;

import org.testng.annotations.Test;

public class RecipeScrapperTest extends baseClass {

   /* private HomePage homePage=new HomePage() ;
    

    
    private RecipeListPage recipeListPage;
    private RecipeDatabase database;

  
    @Test
    void scrapeRecipe() {
       // homePage.launch();
       // homePage.navigateToRecipeList();

    
       // recipeListPage.parseAllRecipesOnAllPages();

        //database.persistAllRecipes();
    }*/
	 @Test
	    public void verifyRecipeListClick() throws InterruptedException {
	        pageObjectclass page = new pageObjectclass(driver,wait);
	        
	        page.clickRecipeList();
	       
	        driver.navigate().refresh();
	        Thread.sleep(1000);
	        //page.click_on_recipes();
	        
	        page.click_on_recipes_with_pagination();
	        

}
}
package com.TestNGtests;


import org.testng.annotations.Test;

import com.BaseClass.baseClass;
import com.pageObject.pageObjectclass;

public class RecipeScrapperTest extends baseClass {

    
   
    private DatabaseClass database;

    @Test
    void scrapeRecipe() {
       

    	pageObjectclass page = new pageObjectclass(driver,wait);
	        
	        page.clickRecipeList();
	        page.removeAds();
	        page.click_on_recipes();

       
    }

}

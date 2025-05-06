package com.TestNGtests;

import com.BaseClass.baseClass;
import com.Utilities.DataProviderClass;
import com.pageObject.RecipeListPage;
import com.pageObject.pageObjectclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class RecipeScrapperTest  extends baseClass {

    
    private RecipeListPage recipeListPage;
    private pageObjectclass pageobject;
   

    @Test
    void scrapeRecipe() {
        
        recipeListPage = new RecipeListPage(driver);
        recipeListPage.parseAllRecipesOnAllPages();

        //database.persistAllRecipes();
    }
    
    @Test(dataProvider = "excelData" , dataProviderClass = DataProviderClass.class)
   public void VerifyIngredients(Map<String , String> Exceldata) {
    	
    	pageobject = new pageObjectclass(driver,wait);
    	
       pageobject.clickRecipeList();
    	
    	pageobject.click_on_recipes();
    	
    	// Ingredients list
      List<String> ingredients_W = pageobject.getIngredients(); 
      
      System.out.println(ingredients_W);
      
      //Excel data from excel sheet
      String ingredients_E = Exceldata.get("Eliminate");
      System.out.println(ingredients_E);
      
      String addData = Exceldata.get("Add");
      System.out.println(addData);

//      List<String> eliminateList = new ArrayList<>(Arrays.asList(ingredients_E.split("\\s*,\\s*")));
//      for(String ingre : ingredients_W) {
//    	  
//    	  eliminateList.add(ingre.toLowerCase());
//      }
//    	
//      List<String> addList = new ArrayList<>(Arrays.asList(addData.split("\\s*,\\s*")));
//
//   // Step 4: Remove (case-insensitive)
//   Iterator<String> iterator = ingredients_W.iterator();
//   while (iterator.hasNext()) {
//       String item = iterator.next();
//       if (eliminateList.contains(item.toLowerCase())) {
//           iterator.remove(); // safely remove while iterating
//       }
//   }
//   
//   for (String addItem : addList) 
//   {
//	    boolean alreadyPresent = false;
//	    for (String existingItem : ingredients_W) 
//	    {
//	        if (existingItem.equalsIgnoreCase(addItem)) {
//	            alreadyPresent = true;
//	            break;
//	        }
//	    }
//	    if (!alreadyPresent) {
//	        ingredients_W.add(addItem);
//	    }
//	}
//
//	//  the final list
//	System.out.println("Final ingredients list: " + ingredients_W);
    }
    
    
 
}

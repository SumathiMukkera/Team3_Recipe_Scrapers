package com.TestNGtests;

import org.testng.annotations.Test;

import com.BaseClass.baseClass;
import com.pageObject.pageObjectclass;



public class testsClass extends baseClass{
	

	 @Test
	    public void verifyRecipeListClick() throws InterruptedException {
	        pageObjectclass page = new pageObjectclass(driver,wait);
	        
	        page.clickRecipeList();
	       
	        driver.navigate().refresh();
	        Thread.sleep(1000);
	        page.click_on_recipes();
	        
	        
	        
	        
	    }

}

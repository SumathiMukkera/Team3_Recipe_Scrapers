package com.TestNGtests;

import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import com.BaseClass.baseClass;
import com.pageObject.pageObjectclass;

public class RecipeScrapperTest extends baseClass {

	pageObjectclass page;

	@Test
	void scrapeRecipe() throws SQLException, TimeoutException, Exception, IOException {

		page = new pageObjectclass(driver, wait);
		page.clickRecipeList();
		driver.navigate().refresh();
		page.removeAds();
		page.click_on_recipes_with_pagination();

	}
}
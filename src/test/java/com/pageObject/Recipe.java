package com.pageObject;

public class Recipe {

	private String recipeID;
	public String recipeName;
	public String recipeCategory;
	public String foodCategory;
	public String ingredients;
	public String preperationTime;
	public String cookingTime;
	public String tags;
	public String numOfServings;
	public String cuisineCategory;
	public String recipeDescription;
	public String preparationMethod;
	public String nutritionValues;
	public String recipeUrl;

	// constructor
	 public Recipe() {}
	 
	public Recipe(String recipeId, String recipeTitle, String recipeCategory, String foodCategory, String ingredients,
			String preperationTime, String cookingTime, String tags, String numOfServings, String cuisineCategory,
			String recipeDescription, String preparationMethod, String nutritionValues, String recipeUrl) {
		this.recipeID = recipeId;
		this.recipeName = recipeTitle;
		this.recipeDescription = recipeDescription;
		this.ingredients = ingredients;
		this.preperationTime = preperationTime;
		this.cookingTime = cookingTime;
		this.preparationMethod = preparationMethod;
		this.numOfServings = numOfServings;
		this.cuisineCategory = cuisineCategory;
		this.foodCategory = foodCategory;
		this.recipeCategory = recipeCategory;
		this.tags = tags;
		this.nutritionValues = nutritionValues;
		this.recipeUrl = recipeUrl;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getRecipeDescription() {
		return recipeDescription;
	}

	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}

	public String getPreparationMethod() {
		return preparationMethod;
	}

	public void setPreparationMethod(String preparationMethod) {
		this.preparationMethod = preparationMethod;
	}

	public String getNutritionValues() {
		return nutritionValues;
	}

	public void setNutritionValues(String nutritionValues) {
		this.nutritionValues = nutritionValues;
	}

	public String getRecipeUrl() {
		return recipeUrl;
	}

	public void setRecipeUrl(String recipeUrl) {
		this.recipeUrl = recipeUrl;
	}

	public String getPreperationTime() {
		return preperationTime;
	}

	public void setPreperationTime(String preperationTime) {
		this.preperationTime = preperationTime;
	}

	public String getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getNumOfServings() {
		return numOfServings;
	}

	public void setNumOfServings(String numOfServings) {
		this.numOfServings = numOfServings;
	}

	public String getCuisineCategory() {
		return cuisineCategory;
	}

	public void setCuisineCategory(String cuisineCategory) {
		this.cuisineCategory = cuisineCategory;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public String getRecipeCategory() {
		return recipeCategory;
	}

	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(String recipeID) {
		this.recipeID = recipeID;
	}

	



    }
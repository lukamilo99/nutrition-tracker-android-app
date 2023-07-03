package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.presentation.view.states.CategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.StringState
import java.util.Date

interface MainContract {

    interface SharedViewModel {

        val categories: LiveData<CategoryState>
        val meals: LiveData<MealState>
        val mealDetails: LiveData<MealDetailsState>
        val categoryNames: LiveData<StringState>
        val regionNames: LiveData<StringState>
        val selectedSearchCriteria: LiveData<String>
        val mealsStats: LiveData<MealDetailsState>
        val allMeals: LiveData<MealDetailsState>
        val mealsPlan: LiveData<List<MealDetailsView>>
        fun fetchCategories()
        fun fetchMealsByName(name: String)
        fun fetchMealsByCategory(category: String)
        fun fetchMealsByRegion(region: String)
        fun fetchMealsByMainIngredient(ingredient: String)
        fun fetchMealDetailsById(mealId: String)
        fun insertMeal(meal: MealDetailsView)
        fun fetchCategoryNames()
        fun fetchRegionNames()
        fun getMealsByCategory(category: String)
        fun getMealsByRegion(region: String)
        fun getMealsByMainIngredient(ingredient: String)
        fun getMealDetailsById(mealId: Int)
        fun getMealsFromLastWeek()
        fun getAllMeals()
        fun sortMealsByNameAscending()
        fun sortMealsByNameDescending()
        fun updateMealPreparationDate(date: Date)
        fun updateMealMultiField(meal: MealDetailsView)
        fun updateMealType(type: String)
        fun updateMeal(meal: MealDetailsView)
        fun deleteMeal(meal: MealDetailsView)
        fun setSelectedSearchCriteria(criteria: String)
        fun insertMealIntoPlan(meal: MealDetailsView, index: Int)
    }
}
package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.presentation.view.states.CategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.StringState

interface MainContract {

    interface SharedViewModel {

        val categories: LiveData<CategoryState>
        val meals: LiveData<MealState>
        val mealDetails: LiveData<MealDetailsState>
        val categoryNames: LiveData<StringState>
        val regionNames: LiveData<StringState>
        fun getCategories()
        fun getMealsByName(name: String)
        fun getMealsByCategory(category: String)
        fun getMealsByRegion(region: String)
        fun getMealsByMainIngredient(ingredient: String)
        fun getMealDetailsById(mealId: String)
        fun getCategoryNames()
        fun getRegionNames()
        fun sortMealsByNameAscending()
        fun sortMealsByNameDescending()
    }
}
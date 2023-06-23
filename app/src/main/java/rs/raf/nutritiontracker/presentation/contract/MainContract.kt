package rs.raf.nutritiontracker.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.nutritiontracker.presentation.view.states.CategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealState

interface MainContract {

    interface SharedViewModel {

        val categories: LiveData<CategoryState>
        val meals: LiveData<MealState>
        fun getCategories()
        fun getMealByName(name: String)
        fun getMealByMainIngredient(ingredient: String)
    }
}
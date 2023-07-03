package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.model.view.MealView

sealed class MealState {
    object Loading: MealState()
    data class Success(val meals: List<MealView>): MealState()
    data class Error(val message: String): MealState()
}

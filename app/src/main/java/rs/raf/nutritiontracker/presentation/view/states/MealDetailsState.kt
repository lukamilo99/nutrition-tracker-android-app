package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.model.view.MealDetailsView

sealed class MealDetailsState {
    object Loading: MealDetailsState()
    object DataFetched: MealDetailsState()
    data class Success(val meal: MealDetailsView): MealDetailsState()
    data class Error(val message: String): MealDetailsState()
}

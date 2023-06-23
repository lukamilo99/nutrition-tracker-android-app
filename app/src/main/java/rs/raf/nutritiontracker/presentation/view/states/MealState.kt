package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.model.api.MealApiResponse

sealed class MealState {
    object Loading: MealState()
    object DataFetched: MealState()
    data class Success(val meals: List<MealApiResponse>): MealState()
    data class Error(val message: String): MealState()
}

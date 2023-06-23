package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse

sealed class CategoryState {
    object Loading: CategoryState()
    object DataFetched: CategoryState()
    data class Success(val categories: List<CategoryApiResponse>): CategoryState()
    data class Error(val message: String): CategoryState()
}

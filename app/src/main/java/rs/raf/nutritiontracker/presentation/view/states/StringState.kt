package rs.raf.nutritiontracker.presentation.view.states

sealed class StringState {
    object Loading: StringState()
    data class Success(val names: List<String>): StringState()
    data class Error(val message: String): StringState()
}

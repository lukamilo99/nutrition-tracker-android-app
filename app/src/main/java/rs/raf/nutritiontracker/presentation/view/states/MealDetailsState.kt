package rs.raf.nutritiontracker.presentation.view.states

import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import java.util.Date

sealed class MealDetailsState {
    object Loading: MealDetailsState()
    data class Success(var meal: MealDetailsView): MealDetailsState() {
        fun copy(
            preparationDate: Date? = null,
            type: String? = null,
            name: String? = null,
            category: String? = null,
            area: String? = null,
            instructions: String? = null,
            youtubeUrl: String? = null
        ): Success {
            if (preparationDate != null) this.meal.preparationDate = preparationDate
            if (type != null) this.meal.type = type
            if (name != null) this.meal.name = name
            if (category != null) this.meal.category = category
            if (area != null) this.meal.area = area
            if (instructions != null) this.meal.instructions = instructions
            if (youtubeUrl != null) this.meal.youtubeUrl = youtubeUrl
            return this
        }

    }
    data class SuccessList(var meals: List<MealDetailsView>): MealDetailsState()
    data class Error(val message: String): MealDetailsState()
}

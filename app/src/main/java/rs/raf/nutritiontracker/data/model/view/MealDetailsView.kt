package rs.raf.nutritiontracker.data.model.view

import java.util.Date

data class MealDetailsView(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val mealThumb: String,
    val preparationDate: Date,
    val type: String,
    val youtubeUrl: String?,
    val ingredientsWithMeasurements: List<IngredientWithMeasurementView>
)

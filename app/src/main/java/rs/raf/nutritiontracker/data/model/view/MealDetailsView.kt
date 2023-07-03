package rs.raf.nutritiontracker.data.model.view

import java.util.Date

data class MealDetailsView(
    val id: String,
    var name: String,
    var category: String,
    var area: String,
    var instructions: String,
    val mealThumb: String,
    var preparationDate: Date,
    var creationDate: Date,
    var type: String,
    var youtubeUrl: String?,
    val isSaved: Boolean,
    val ingredientsWithMeasurements: List<IngredientWithMeasurementView>
) {
    constructor(
        name: String,
        category: String,
        area: String,
        instructions: String,
        type: String,
        youtubeUrl: String
    ) : this(
        id = "",
        name = name,
        category = category,
        area = area,
        instructions = instructions,
        mealThumb = "",
        preparationDate = Date(),
        creationDate = Date(),
        type = type,
        youtubeUrl = youtubeUrl,
        isSaved = false,
        ingredientsWithMeasurements = emptyList()
    )
}
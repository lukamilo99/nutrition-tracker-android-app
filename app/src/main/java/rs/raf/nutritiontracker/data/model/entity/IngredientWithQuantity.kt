package rs.raf.nutritiontracker.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class IngredientWithQuantity(
    @Embedded val ingredient: IngredientEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "ingredientId",
        entity = MealIngredientCrossRef::class
    )
    val measurement: MealIngredientCrossRef?
)

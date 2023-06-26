package rs.raf.nutritiontracker.data.model.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MealWithIngredients(
    @Embedded val meal: MealEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(MealIngredientCrossRef::class)
    )
    val ingredients: List<IngredientWithQuantity>
)

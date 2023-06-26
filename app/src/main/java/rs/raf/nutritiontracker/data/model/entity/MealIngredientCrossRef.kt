package rs.raf.nutritiontracker.data.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "meal_ingredient",
    primaryKeys = ["mealId", "ingredientId"],
    foreignKeys = [
        ForeignKey(entity = MealEntity::class, parentColumns = ["id"], childColumns = ["mealId"]),
        ForeignKey(entity = IngredientEntity::class, parentColumns = ["id"], childColumns = ["ingredientId"])
    ]
)
data class MealIngredientCrossRef(
    val mealId: Int,
    val ingredientId: Int,
    val quantity: String,
)

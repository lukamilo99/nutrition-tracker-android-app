package rs.raf.nutritiontracker.data.model.entity

import androidx.room.Entity

@Entity(primaryKeys = ["mealId", "ingredientId"], tableName = "meal_ingredient")
data class MealIngredient(
    val mealId: Long,
    val ingredientId: Long,
)

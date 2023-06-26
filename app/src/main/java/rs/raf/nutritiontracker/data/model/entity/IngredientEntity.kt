package rs.raf.nutritiontracker.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

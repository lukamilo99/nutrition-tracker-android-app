package rs.raf.nutritiontracker.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val mealId: Long = 0,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val mealThumb: String,
    val preparationDate: Date,
    val creationDate: Date,
    val type: String,
    val youtubeUrl: String?
)


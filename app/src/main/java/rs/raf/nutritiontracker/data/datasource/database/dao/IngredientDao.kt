package rs.raf.nutritiontracker.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIngredient(ingredient: IngredientEntity): Long

    @Update
    fun updateIngredient(ingredient: IngredientEntity)

    @Delete
    fun deleteIngredient(ingredient: IngredientEntity)
}
package rs.raf.nutritiontracker.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredient WHERE name = :ingredientName")
    fun getIngredientByName(ingredientName: String): IngredientEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(ingredient: IngredientEntity): Int
}
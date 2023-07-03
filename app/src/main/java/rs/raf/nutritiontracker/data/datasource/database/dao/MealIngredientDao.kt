package rs.raf.nutritiontracker.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import rs.raf.nutritiontracker.data.model.entity.MealIngredient

@Dao
interface MealIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMealIngredientCrossRef(crossRef: MealIngredient)

    @Update
    fun updateMealIngredientCrossRef(crossRef: MealIngredient)

    @Delete
    fun deleteMealIngredientCrossRef(crossRef: MealIngredient)
}
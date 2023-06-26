package rs.raf.nutritiontracker.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import rs.raf.nutritiontracker.data.model.entity.MealIngredientCrossRef

@Dao
interface MealIngredientCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMealIngredientCrossRef(crossRef: MealIngredientCrossRef)
}
package rs.raf.nutritiontracker.data.datasource.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity
import rs.raf.nutritiontracker.data.model.entity.MealEntity
import rs.raf.nutritiontracker.data.model.entity.MealIngredientCrossRef
import rs.raf.nutritiontracker.data.model.entity.MealWithIngredients

@Dao
interface MealDao {

    @Transaction
    @Query("SELECT * FROM meal WHERE id = :mealId")
    fun getMealWithIngredients(mealId: String): LiveData<MealWithIngredients>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: MealEntity): Int

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<IngredientEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMealIngredientsJoin(mealIngredientJoin: MealIngredientCrossRef)

    @Transaction
    @Query("DELETE FROM meal WHERE id = :mealId")
    fun deleteMeal(mealId: String)
}
package rs.raf.nutritiontracker.data.datasource.database.dao

import io.reactivex.Observable
import androidx.room.*
import rs.raf.nutritiontracker.data.model.entity.MealEntity
import rs.raf.nutritiontracker.data.model.entity.MealWithIngredients

@Dao
interface MealDao {

    @Transaction
    @Query("SELECT * FROM meal WHERE mealId = :mealId")
    fun getMealDetailsById(mealId: Int): Observable<MealWithIngredients>

    @Transaction
    @Query("SELECT * FROM meal")
    fun getAllMeals(): Observable<List<MealWithIngredients>>

    @Transaction
    @Query("SELECT * FROM meal WHERE CAST((creationDate / 1000) AS INTEGER) BETWEEN strftime('%s','now','-7 days') AND strftime('%s','now')  ORDER BY mealId DESC;")
    fun getMealsFromLastWeek(): Observable<List<MealWithIngredients>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: MealEntity): Long

    @Transaction
    @Query("SELECT * FROM meal " +
            "INNER JOIN meal_ingredient " +
            "ON meal.mealId = meal_ingredient.mealId " +
            "INNER JOIN ingredient " +
            "ON meal_ingredient.ingredientId = ingredient.ingredientId " +
            "WHERE ingredient.name = :ingredient")
    fun getMealsByIngredient(ingredient: String): Observable<List<MealEntity>>
    @Transaction
    @Query("SELECT * FROM meal WHERE category = :category")
    fun getMealsByCategory(category: String): Observable<List<MealEntity>>
    @Transaction
    @Query("SELECT * FROM meal WHERE area = :region")
    fun getMealsByRegion(region: String): Observable<List<MealEntity>>

    @Update
    fun updateMeal(meal: MealEntity)

    @Delete
    fun deleteMeal(meal: MealEntity)
}

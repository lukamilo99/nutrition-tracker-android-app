package rs.raf.nutritiontracker.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.nutritiontracker.data.datasource.database.dao.IngredientDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealIngredientCrossRefDao
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity
import rs.raf.nutritiontracker.data.model.entity.MealEntity
import rs.raf.nutritiontracker.data.model.entity.MealIngredientCrossRef

@Database(
    entities = [MealEntity::class, IngredientEntity::class, MealIngredientCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun mealIngredientCrossRefDao(): MealIngredientCrossRefDao
}
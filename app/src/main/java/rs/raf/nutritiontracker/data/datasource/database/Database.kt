package rs.raf.nutritiontracker.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.nutritiontracker.data.datasource.database.converter.DateConverter
import rs.raf.nutritiontracker.data.datasource.database.dao.IngredientDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealIngredientDao
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity
import rs.raf.nutritiontracker.data.model.entity.MealEntity
import rs.raf.nutritiontracker.data.model.entity.MealIngredient

@Database(
    entities = [MealEntity::class, IngredientEntity::class, MealIngredient::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun mealIngredientCrossRefDao(): MealIngredientDao
}
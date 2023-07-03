package rs.raf.nutritiontracker.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse
import rs.raf.nutritiontracker.data.model.api.CategoryNameApiResponse
import rs.raf.nutritiontracker.data.model.api.RegionNameApiResponse
import rs.raf.nutritiontracker.data.model.entity.MealEntity
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.data.model.view.MealView
import rs.raf.nutritiontracker.data.model.view.Resource

interface MealRepository {

    fun fetchCategories(): Observable<Resource<List<CategoryApiResponse>>>
    fun fetchMealsByName(name: String): Observable<Resource<List<MealView>>>
    fun fetchMealsByMainIngredient(ingredient: String): Observable<Resource<List<MealView>>>
    fun fetchMealDetailsById(mealId: String): Observable<Resource<MealDetailsView>>
    fun fetchMealsByCategory(category: String): Observable<Resource<List<MealView>>>
    fun fetchMealsByRegion(region: String): Observable<Resource<List<MealView>>>
    fun fetchRegionNames(): Observable<Resource<List<RegionNameApiResponse>>>
    fun fetchCategoryNames(): Observable<Resource<List<CategoryNameApiResponse>>>
    fun getMealsByMainIngredient(ingredient: String): Observable<List<MealView>>
    fun getMealsByCategory(category: String): Observable<List<MealView>>
    fun getMealsByRegion(region: String): Observable<List<MealView>>
    fun getMealDetailsById(mealId: Int): Observable<MealDetailsView>
    fun getMealsFromLastWeek(): Observable<List<MealDetailsView>>
    fun getAllMeals(): Observable<List<MealDetailsView>>
    fun insertMeal(mealView: MealDetailsView): Completable
    fun updateMeal(mealView: MealDetailsView): Completable
    fun deleteMeal(mealView: MealDetailsView): Completable

}
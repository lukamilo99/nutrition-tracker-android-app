package rs.raf.nutritiontracker.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse
import rs.raf.nutritiontracker.data.model.api.RegionNameApiResponse
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.data.model.view.MealView
import rs.raf.nutritiontracker.data.model.view.Resource

interface MealRepository {

    fun getCategories(): Observable<Resource<List<CategoryApiResponse>>>
    fun getMealsByName(name: String): Observable<Resource<List<MealView>>>
    fun getMealsByMainIngredient(ingredient: String): Observable<Resource<List<MealView>>>
    fun getMealDetailsById(mealId: String): Observable<Resource<MealDetailsView>>
    fun getMealsByCategory(category: String): Observable<Resource<List<MealView>>>
    fun getMealsByRegion(region: String): Observable<Resource<List<MealView>>>
    fun getRegionNames(): Observable<Resource<List<RegionNameApiResponse>>>
    fun insertMeal(mealView: MealDetailsView): Completable

}
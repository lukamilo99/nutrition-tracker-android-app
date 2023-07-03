package rs.raf.nutritiontracker.data.datasource.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.model.api.CategoryListApiResponse
import rs.raf.nutritiontracker.data.model.api.CategoryNameApiResponse
import rs.raf.nutritiontracker.data.model.api.CategoryNameListApiResponse
import rs.raf.nutritiontracker.data.model.api.MealDetailsListApiResponse
import rs.raf.nutritiontracker.data.model.api.MealListApiResponse
import rs.raf.nutritiontracker.data.model.api.RegionNameListApiResponse

interface ApiMealService {

    @GET("categories.php")
    fun getCategories(): Observable<CategoryListApiResponse>
    @GET("search.php")
    fun getMealsByName(@Query("s") name: String): Observable<MealListApiResponse>
    @GET("filter.php")
    fun getMealsByMainIngredient(@Query("i") ingredient: String): Observable<MealListApiResponse>
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Observable<MealListApiResponse>
    @GET("lookup.php")
    fun getMealDetailsById(@Query("i") mealId: String): Observable<MealDetailsListApiResponse>
    @GET("filter.php")
    fun getMealsByRegion(@Query("a") region: String): Observable<MealListApiResponse>
    @GET("list.php")
    fun getRegionNames(@Query("a") list: String): Observable<RegionNameListApiResponse>
    @GET("list.php")
    fun getCategoryNames(@Query("c") list: String): Observable<CategoryNameListApiResponse>
}
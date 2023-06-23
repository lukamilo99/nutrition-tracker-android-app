package rs.raf.nutritiontracker.data.datasource.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.nutritiontracker.data.model.api.CategoryListApiResponse
import rs.raf.nutritiontracker.data.model.api.MealListApiResponse

interface ApiMealService {

    @GET("categories.php")
    fun getCategories(): Observable<CategoryListApiResponse>
    @GET("search.php")
    fun getMealByName(@Query("s") name: String): Observable<MealListApiResponse>
    @GET("filter.php")
    fun getMealByMainIngredient(@Query("i") ingredient: String): Observable<MealListApiResponse>
}
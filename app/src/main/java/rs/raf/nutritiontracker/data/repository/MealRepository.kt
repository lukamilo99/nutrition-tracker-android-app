package rs.raf.nutritiontracker.data.repository

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse
import rs.raf.nutritiontracker.data.model.api.MealApiResponse
import rs.raf.nutritiontracker.data.model.view.Resource

interface MealRepository {

    fun getCategories(): Observable<Resource<List<CategoryApiResponse>>>
    fun getMealByName(name: String): Observable<Resource<List<MealApiResponse>>>
    fun getMealByMainIngredient(ingredient: String): Observable<Resource<List<MealApiResponse>>>
}
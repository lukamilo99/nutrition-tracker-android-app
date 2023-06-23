package rs.raf.nutritiontracker.data.repository.impl

import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasource.remote.ApiMealService
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse
import rs.raf.nutritiontracker.data.model.api.MealApiResponse
import rs.raf.nutritiontracker.data.model.view.Resource
import rs.raf.nutritiontracker.data.repository.MealRepository

class MealRepositoryImpl(
    private val apiMealService: ApiMealService
) : MealRepository {

    override fun getCategories(): Observable<Resource<List<CategoryApiResponse>>> {
        return apiMealService
            .getCategories()
            .map { response ->
                Resource.Success(response.categories)
            }
    }

    override fun getMealByName(name: String): Observable<Resource<List<MealApiResponse>>> {
        return apiMealService
            .getMealByName(name)
            .map { response ->
                Resource.Success(response.meals)
            }
    }

    override fun getMealByMainIngredient(ingredient: String): Observable<Resource<List<MealApiResponse>>> {
        return apiMealService
            .getMealByMainIngredient(ingredient)
            .map { response ->
                Resource.Success(response.meals)
            }
    }
}

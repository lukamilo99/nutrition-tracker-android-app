package rs.raf.nutritiontracker.data.repository.impl

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasource.database.dao.IngredientDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealIngredientCrossRefDao
import rs.raf.nutritiontracker.data.datasource.remote.ApiMealService
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse

import rs.raf.nutritiontracker.data.model.api.RegionNameApiResponse
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity
import rs.raf.nutritiontracker.data.model.entity.MealIngredientCrossRef
import rs.raf.nutritiontracker.data.model.mapper.CustomMapper
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.data.model.view.MealView
import rs.raf.nutritiontracker.data.model.view.Resource
import rs.raf.nutritiontracker.data.repository.MealRepository

class MealRepositoryImpl(
    private val apiMealService: ApiMealService,
    private val mealDao: MealDao,
    private val ingredientDao: IngredientDao,
    private val mealIngredientCrossRefDao: MealIngredientCrossRefDao,
    private val customMapper: CustomMapper
) : MealRepository {

    override fun getCategories(): Observable<Resource<List<CategoryApiResponse>>> {
        return apiMealService
            .getCategories()
            .map { response ->
                Resource.Success(response.categories)
            }
    }

    override fun getMealsByName(name: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByName(name)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun getMealsByMainIngredient(ingredient: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByMainIngredient(ingredient)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun getMealDetailsById(mealId: String): Observable<Resource<MealDetailsView>> {
        return apiMealService
            .getMealDetailsById(mealId)
            .map { response ->
                val mealDetails = response.meals.firstOrNull()?.let { mealDetailsApiResponse ->
                    customMapper.apiToViewMealDetails(mealDetailsApiResponse)
                }
                mealDetails?.let { Resource.Success(it) }
            }
    }

    override fun getMealsByCategory(category: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByCategory(category)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun getMealsByRegion(region: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByRegion(region)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun insertMeal(mealView: MealDetailsView): Completable {
        return Completable.fromCallable {
            val mealEntity = customMapper.viewToEntityMealDetails(mealView)
            val mealId = mealDao.insertMeal(mealEntity)

            for (ingredientWithMeasurement in mealView.ingredientsWithMeasurements) {
                val existingIngredientEntity = ingredientDao.getIngredientByName(ingredientWithMeasurement.name)
                val ingredientId = if (existingIngredientEntity != null) {
                    existingIngredientEntity.id
                } else {
                    val newIngredientEntity = IngredientEntity(
                        name = ingredientWithMeasurement.name
                    )
                    ingredientDao.insertIngredient(newIngredientEntity)
                }

                val crossRef = MealIngredientCrossRef(
                    mealId = mealId,
                    ingredientId = ingredientId,
                    quantity = ingredientWithMeasurement.quantity
                )
                mealIngredientCrossRefDao.insertMealIngredientCrossRef(crossRef)
            }
        }
    }

    override fun getRegionNames(): Observable<Resource<List<RegionNameApiResponse>>> {
        return apiMealService
            .getRegionNames("list")
            .map { response ->
                Resource.Success(response.meals)
            }
    }
}

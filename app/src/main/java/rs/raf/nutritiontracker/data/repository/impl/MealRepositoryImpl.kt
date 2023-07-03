package rs.raf.nutritiontracker.data.repository.impl

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.nutritiontracker.data.datasource.database.dao.IngredientDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealDao
import rs.raf.nutritiontracker.data.datasource.database.dao.MealIngredientDao
import rs.raf.nutritiontracker.data.datasource.remote.ApiMealService
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse
import rs.raf.nutritiontracker.data.model.api.CategoryNameApiResponse
import rs.raf.nutritiontracker.data.model.api.RegionNameApiResponse
import rs.raf.nutritiontracker.data.model.entity.IngredientEntity
import rs.raf.nutritiontracker.data.model.entity.MealIngredient
import rs.raf.nutritiontracker.data.model.mapper.CustomMapper
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.data.model.view.MealView
import rs.raf.nutritiontracker.data.model.view.Resource
import rs.raf.nutritiontracker.data.repository.MealRepository

class MealRepositoryImpl(
    private val apiMealService: ApiMealService,
    private val mealDao: MealDao,
    private val ingredientDao: IngredientDao,
    private val mealIngredientDao: MealIngredientDao,
    private val customMapper: CustomMapper
) : MealRepository {

    override fun fetchCategories(): Observable<Resource<List<CategoryApiResponse>>> {
        return apiMealService
            .getCategories()
            .map { response ->
                Resource.Success(response.categories)
            }
    }

    override fun fetchMealsByName(name: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByName(name)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun getMealsFromLastWeek(): Observable<List<MealDetailsView>> {
        return mealDao.getMealsFromLastWeek().map { list ->
            list.map { customMapper.entityToViewMealDetails(it) }
        }
    }

    override fun getAllMeals(): Observable<List<MealDetailsView>> {
        return mealDao.getAllMeals().map { list ->
            list.map { customMapper.entityToViewMealDetails(it) }
        }
    }

    override fun fetchMealsByMainIngredient(ingredient: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByMainIngredient(ingredient)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun fetchMealDetailsById(mealId: String): Observable<Resource<MealDetailsView>> {
        return apiMealService
            .getMealDetailsById(mealId)
            .map { response ->
                val mealDetails = response.meals.firstOrNull()?.let { mealDetailsApiResponse ->
                    customMapper.apiToViewMealDetails(mealDetailsApiResponse)
                }
                mealDetails?.let { Resource.Success(it) } ?: throw NoSuchElementException("No meal details found for the given id")
            }
    }

    override fun fetchMealsByCategory(category: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByCategory(category)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun fetchMealsByRegion(region: String): Observable<Resource<List<MealView>>> {
        return apiMealService
            .getMealsByRegion(region)
            .map { response ->
                val meals = response.meals.map { mealApiResponse ->
                    customMapper.apiToViewMeal(mealApiResponse)
                }
                Resource.Success(meals)
            }
    }

    override fun fetchRegionNames(): Observable<Resource<List<RegionNameApiResponse>>> {
        return apiMealService
            .getRegionNames("list")
            .map { response ->
                Resource.Success(response.meals)
            }
    }

    override fun fetchCategoryNames(): Observable<Resource<List<CategoryNameApiResponse>>> {
        return apiMealService
            .getCategoryNames("list")
            .map { response ->
                Resource.Success(response.meals)
            }
    }

    override fun getMealsByMainIngredient(ingredient: String): Observable<List<MealView>> {
        return mealDao.getMealsByIngredient(ingredient).map { list ->
            list.map { customMapper.entityToViewMeal(it) }
        }
    }

    override fun getMealsByCategory(category: String): Observable<List<MealView>> {
        return mealDao.getMealsByCategory(category).map { list ->
            list.map { customMapper.entityToViewMeal(it) }
        }
    }

    override fun getMealsByRegion(region: String): Observable<List<MealView>> {
        return mealDao.getMealsByRegion(region).map { list ->
            list.map { customMapper.entityToViewMeal(it) }
        }
    }


    override fun insertMeal(mealView: MealDetailsView): Completable {
        return Completable.fromCallable {
            val mealEntity = customMapper.viewToEntityMealDetails(mealView)
            val mealId = mealDao.insertMeal(mealEntity)

            for (ingredientWithMeasurement in mealView.ingredientsWithMeasurements) {
                val newIngredientEntity = IngredientEntity(
                    name = ingredientWithMeasurement.ingredientName,
                    quantity = ingredientWithMeasurement.quantity
                )
                val ingredientId = ingredientDao.insertIngredient(newIngredientEntity)
                val crossRef = MealIngredient(
                    mealId = mealId,
                    ingredientId = ingredientId
                )
                mealIngredientDao.insertMealIngredientCrossRef(crossRef)
            }
        }
    }

    override fun updateMeal(mealView: MealDetailsView): Completable {
        return Completable.fromCallable {
            val mealEntity = customMapper.viewToEntityMealDetails(mealView)
            mealDao.updateMeal(mealEntity)

            for (ingredientWithMeasurement in mealView.ingredientsWithMeasurements) {
                val newIngredientEntity = IngredientEntity(
                    name = ingredientWithMeasurement.ingredientName,
                    quantity = ingredientWithMeasurement.quantity,
                    ingredientId = ingredientWithMeasurement.id
                )
                ingredientDao.updateIngredient(newIngredientEntity)
                val crossRef = MealIngredient(
                    mealId = mealEntity.mealId,
                    ingredientId = newIngredientEntity.ingredientId
                )
                mealIngredientDao.updateMealIngredientCrossRef(crossRef)
            }
        }
    }

    override fun deleteMeal(mealView: MealDetailsView): Completable {
        return Completable.fromCallable {
            val mealEntity = customMapper.viewToEntityMealDetails(mealView)
            mealDao.deleteMeal(mealEntity)

            for (ingredientWithMeasurement in mealView.ingredientsWithMeasurements) {
                val newIngredientEntity = IngredientEntity(
                    name = ingredientWithMeasurement.ingredientName,
                    quantity = ingredientWithMeasurement.quantity,
                    ingredientId = ingredientWithMeasurement.id
                )
                ingredientDao.deleteIngredient(newIngredientEntity)
                val crossRef = MealIngredient(
                    mealId = mealEntity.mealId,
                    ingredientId = newIngredientEntity.ingredientId
                )
                mealIngredientDao.deleteMealIngredientCrossRef(crossRef)
            }
        }
    }

    override fun getMealDetailsById(mealId: Int): Observable<MealDetailsView> {
        return mealDao.getMealDetailsById(mealId)
            .map { mealWithIngredients ->
                customMapper.entityToViewMealDetails(mealWithIngredients)
            }
    }
}


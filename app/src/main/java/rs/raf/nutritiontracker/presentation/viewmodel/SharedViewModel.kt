package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.data.model.view.Resource
import rs.raf.nutritiontracker.data.repository.MealRepository
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.states.CategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.StringState
import timber.log.Timber
import java.util.Date

class SharedViewModel(
    private val mealRepository : MealRepository
) : ViewModel(), MainContract.SharedViewModel {

    private val subscriptions = CompositeDisposable()
    override val categories: MutableLiveData<CategoryState> = MutableLiveData()
    override val meals: MutableLiveData<MealState> = MutableLiveData()
    override val selectedSearchCriteria: MutableLiveData<String> = MutableLiveData()
    override val mealsStats: MutableLiveData<MealDetailsState> = MutableLiveData()
    override val mealDetails: MutableLiveData<MealDetailsState> = MutableLiveData()
    override val categoryNames: MutableLiveData<StringState> = MutableLiveData()
    override val regionNames: MutableLiveData<StringState> = MutableLiveData()
    override val allMeals: MutableLiveData<MealDetailsState> = MutableLiveData()
    override val mealsPlan: MutableLiveData<List<MealDetailsView>> =  MutableLiveData()

    override fun getAllMeals() {
        val subscription = mealRepository
            .getAllMeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    allMeals.value = MealDetailsState.SuccessList(it)
                },
                {
                    allMeals.value = MealDetailsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByCategory(category: String) {
        val subscription = mealRepository
            .getMealsByCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meals.value = MealState.Success(it)
                },
                {
                    meals.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByRegion(region: String) {
        val subscription = mealRepository
            .getMealsByRegion(region)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meals.value = MealState.Success(it)
                },
                {
                    meals.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByMainIngredient(ingredient: String) {
        val subscription = mealRepository
            .getMealsByMainIngredient(ingredient)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meals.value = MealState.Success(it)
                },
                {
                    meals.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealDetailsById(mealId: Int) {
        val subscription = mealRepository
            .getMealDetailsById(mealId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealDetails.value = MealDetailsState.Success(it)
                },
                {
                    mealDetails.value = MealDetailsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsFromLastWeek() {
        val subscription = mealRepository
            .getMealsFromLastWeek()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsStats.value = MealDetailsState.SuccessList(it)
                },
                {
                    mealsStats.value = MealDetailsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchCategories() {
        val subscription = mealRepository
            .fetchCategories()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> categories.value = CategoryState.Loading
                    is Resource.Success -> {
                        categories.value = CategoryState.Success(resource.data)
                    }
                    is Resource.Error -> categories.value =
                        CategoryState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchMealsByName(name: String) {
        val subscription = mealRepository
            .fetchMealsByName(name)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> meals.value = MealState.Loading
                    is Resource.Success -> {
                        meals.value = MealState.Success(resource.data)
                    }
                    is Resource.Error -> meals.value =
                        MealState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchMealsByCategory(category: String) {
        val subscription = mealRepository
            .fetchMealsByCategory(category)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> meals.value = MealState.Loading
                    is Resource.Success -> {
                        meals.value = MealState.Success(resource.data)
                    }
                    is Resource.Error -> meals.value =
                        MealState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun insertMeal(meal: MealDetailsView) {
        val subscription = mealRepository
            .insertMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Meal inserted successfully")
            }, { error ->
                Timber.e(error, "Error occurred while inserting meal")
            })
        subscriptions.add(subscription)
    }

    override fun updateMeal(meal: MealDetailsView) {
        val subscription = mealRepository
            .updateMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Meal updated successfully")
            }, { error ->
                Timber.e(error, "Error occurred while updating meal")
            })
        subscriptions.add(subscription)
    }

    override fun deleteMeal(meal: MealDetailsView) {
        val subscription = mealRepository
            .deleteMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Meal deleted successfully")
            }, { error ->
                Timber.e(error, "Error occurred while deleting meal")
            })
        subscriptions.add(subscription)
    }

    override fun fetchMealsByRegion(region: String) {
        val subscription = mealRepository
            .fetchMealsByRegion(region)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> meals.value = MealState.Loading
                    is Resource.Success -> {
                        meals.value = MealState.Success(resource.data)
                    }
                    is Resource.Error -> meals.value =
                        MealState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchMealsByMainIngredient(ingredient: String) {
        val subscription = mealRepository
            .fetchMealsByMainIngredient(ingredient)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> meals.value = MealState.Loading
                    is Resource.Success -> {
                        meals.value = MealState.Success(resource.data)
                    }
                    is Resource.Error -> meals.value =
                        MealState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchMealDetailsById(mealId: String) {
        val subscription = mealRepository
            .fetchMealDetailsById(mealId)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> mealDetails.value = MealDetailsState.Loading
                    is Resource.Success -> {
                        mealDetails.value = MealDetailsState.Success(resource.data)
                    }

                    is Resource.Error -> mealDetails.value =
                        MealDetailsState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchCategoryNames() {
        val subscription = mealRepository
            .fetchCategoryNames()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> categoryNames.value = StringState.Loading
                    is Resource.Success -> {
                        val catNames = resource.data.map { it.strCategory }
                        categoryNames.value = StringState.Success(catNames)
                    }
                    is Resource.Error -> categoryNames.value =
                        StringState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchRegionNames() {
        val subscription = mealRepository
            .fetchRegionNames()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> regionNames.value = StringState.Loading
                    is Resource.Success -> {
                        val regNames = resource.data.map { it.strArea }
                        regionNames.value = StringState.Success(regNames)
                    }
                    is Resource.Error -> regionNames.value =
                        StringState.Error("Error happened while fetching data from the server")
                }
            }
        subscriptions.add(subscription)
    }

    override fun sortMealsByNameAscending() {
        val currentState = meals.value
        if (currentState is MealState.Success) {
            val sortedMeals = currentState.meals.sortedBy { it.name }
            meals.value = MealState.Success(sortedMeals)
        }
    }

    override fun sortMealsByNameDescending() {
        val currentState = meals.value
        if (currentState is MealState.Success) {
            val sortedMeals = currentState.meals.sortedByDescending { it.name }
            meals.value = MealState.Success(sortedMeals)
        }
    }

    override fun updateMealPreparationDate(date: Date) {
        val updatedMealDetails = (mealDetails.value as? MealDetailsState.Success)!!.copy(preparationDate = date)
        mealDetails.value = updatedMealDetails
    }

    override fun updateMealType(type: String) {
        val updatedMealDetails = (mealDetails.value as? MealDetailsState.Success)!!.copy(type = type)
        mealDetails.value = updatedMealDetails
    }

    override fun updateMealMultiField(meal: MealDetailsView) {
        val updatedMealDetails = (mealDetails.value as? MealDetailsState.Success)!!.copy(name = meal.name,
            category = meal.category,
            area = meal.area,
            instructions = meal.instructions,
            type = meal.type,
            youtubeUrl = meal.youtubeUrl)
        mealDetails.value = updatedMealDetails
    }

    override fun setSelectedSearchCriteria(criteria: String) {
        selectedSearchCriteria.value = criteria
    }

    override fun insertMealIntoPlan(meal: MealDetailsView, index: Int) {
        val currentPlan: MutableList<MealDetailsView> = mealsPlan.value?.toMutableList() ?: mutableListOf()
        if (index < currentPlan.size) {
            currentPlan[index] = meal
        } else {
            currentPlan.add(meal)
        }
        mealsPlan.value = currentPlan
    }



    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}
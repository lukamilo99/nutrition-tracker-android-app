package rs.raf.nutritiontracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.nutritiontracker.data.model.view.Resource
import rs.raf.nutritiontracker.data.repository.MealRepository
import rs.raf.nutritiontracker.presentation.contract.MainContract
import rs.raf.nutritiontracker.presentation.view.states.CategoryState
import rs.raf.nutritiontracker.presentation.view.states.MealDetailsState
import rs.raf.nutritiontracker.presentation.view.states.MealState
import rs.raf.nutritiontracker.presentation.view.states.StringState
import timber.log.Timber

class SharedViewModel(
    private val mealRepository : MealRepository
) : ViewModel(), MainContract.SharedViewModel {

    private val subscriptions = CompositeDisposable()
    override val categories: MutableLiveData<CategoryState> = MutableLiveData()
    override val meals: MutableLiveData<MealState> = MutableLiveData()
    override val mealDetails: MutableLiveData<MealDetailsState> = MutableLiveData()
    override val categoryNames: MutableLiveData<StringState> = MutableLiveData()
    override val regionNames: MutableLiveData<StringState> = MutableLiveData()

    override fun getCategories() {
        val subscription = mealRepository
            .getCategories()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> categories.value = CategoryState.Loading
                        is Resource.Success -> {
                            categories.value = CategoryState.Success(resource.data)
                            val catNames = resource.data.map { it.strCategory }
                            categoryNames.value = StringState.Success(catNames)
                        }
                        is Resource.Error -> categories.value = CategoryState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    categories.value = CategoryState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByName(name: String) {
        val subscription = mealRepository
            .getMealsByName(name)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> meals.value = MealState.Loading
                        is Resource.Success -> {
                            meals.value = MealState.Success(resource.data)
                        }
                        is Resource.Error -> meals.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    meals.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByCategory(category: String) {
        val subscription = mealRepository
            .getMealsByCategory(category)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> meals.value = MealState.Loading
                        is Resource.Success -> {
                            meals.value = MealState.Success(resource.data)
                        }
                        is Resource.Error -> meals.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    meals.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByRegion(region: String) {
        val subscription = mealRepository
            .getMealsByRegion(region)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> meals.value = MealState.Loading
                        is Resource.Success -> {
                            meals.value = MealState.Success(resource.data)
                        }
                        is Resource.Error -> meals.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    meals.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsByMainIngredient(ingredient: String) {
        val subscription = mealRepository
            .getMealsByMainIngredient(ingredient)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> meals.value = MealState.Loading
                        is Resource.Success -> {
                            meals.value = MealState.Success(resource.data)
                        }
                        is Resource.Error -> meals.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    meals.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealDetailsById(mealId: String) {
        val subscription = mealRepository
            .getMealDetailsById(mealId)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> mealDetails.value = MealDetailsState.Loading
                        is Resource.Success -> {
                            mealDetails.value = MealDetailsState.Success(resource.data)
                        }
                        is Resource.Error -> mealDetails.value = MealDetailsState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    mealDetails.value = MealDetailsState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getCategoryNames() {

    }

    override fun getRegionNames() {
        val subscription = mealRepository
            .getRegionNames()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    when(resource) {
                        is Resource.Loading -> regionNames.value = StringState.Loading
                        is Resource.Success -> {
                            val regNames = resource.data.map { it.strArea }
                            regionNames.value = StringState.Success(regNames)
                        }
                        is Resource.Error -> regionNames.value = StringState.Error("Error happened while fetching data from the server")
                    }
                },
                { error ->
                    regionNames.value = StringState.Error("Error happened while fetching data from the server")
                    Timber.e(error)
                }
            )
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

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}
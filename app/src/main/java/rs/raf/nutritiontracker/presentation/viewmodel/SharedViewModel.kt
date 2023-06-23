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
import rs.raf.nutritiontracker.presentation.view.states.MealState
import timber.log.Timber

class SharedViewModel(
    private val mealRepository : MealRepository
) : ViewModel(), MainContract.SharedViewModel {

    private val subscriptions = CompositeDisposable()
    override val categories: MutableLiveData<CategoryState> = MutableLiveData()
    override val meals: MutableLiveData<MealState> = MutableLiveData()

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

    override fun getMealByName(name: String) {
        val subscription = mealRepository
            .getMealByName(name)
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

    override fun getMealByMainIngredient(ingredient: String) {
        val subscription = mealRepository
            .getMealByMainIngredient(ingredient)
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

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}
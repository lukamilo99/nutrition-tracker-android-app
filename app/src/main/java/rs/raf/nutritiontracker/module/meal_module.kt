package rs.raf.nutritiontracker.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasource.remote.ApiMealService
import rs.raf.nutritiontracker.data.repository.MealRepository
import rs.raf.nutritiontracker.data.repository.impl.MealRepositoryImpl
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

val mealModule = module {

    viewModel { SharedViewModel(mealRepository = get()) }
    single<MealRepository> { MealRepositoryImpl(apiMealService = get()) }
    single<ApiMealService> { create(retrofit = get())}
}
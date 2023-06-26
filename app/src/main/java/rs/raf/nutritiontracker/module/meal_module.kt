package rs.raf.nutritiontracker.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.nutritiontracker.data.datasource.database.Database
import rs.raf.nutritiontracker.data.datasource.remote.ApiMealService
import rs.raf.nutritiontracker.data.model.mapper.CustomMapper
import rs.raf.nutritiontracker.data.repository.MealRepository
import rs.raf.nutritiontracker.data.repository.impl.MealRepositoryImpl
import rs.raf.nutritiontracker.presentation.viewmodel.SharedViewModel

val mealModule = module {

    viewModel { SharedViewModel(mealRepository = get()) }
    single { get<Database>().mealDao()}
    single { get<Database>().ingredientDao()}
    single { get<Database>().mealIngredientCrossRefDao()}
    single<MealRepository> { MealRepositoryImpl(apiMealService = get()
        , customMapper = get()
        , mealDao = get()
        , ingredientDao = get()
        , mealIngredientCrossRefDao = get())}
    single<ApiMealService> { create(retrofit = get())}
    single{CustomMapper()}
}
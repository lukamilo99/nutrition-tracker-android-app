package rs.raf.nutritiontracker.data.model.mapper

import rs.raf.nutritiontracker.data.model.api.MealApiResponse
import rs.raf.nutritiontracker.data.model.api.MealDetailsApiResponse
import rs.raf.nutritiontracker.data.model.entity.MealEntity
import rs.raf.nutritiontracker.data.model.entity.MealWithIngredients
import rs.raf.nutritiontracker.data.model.view.IngredientWithMeasurementView
import rs.raf.nutritiontracker.data.model.view.MealDetailsView
import rs.raf.nutritiontracker.data.model.view.MealView
import java.util.Date

class CustomMapper {

    fun apiToViewMealDetails(response: MealDetailsApiResponse): MealDetailsView {
        val ingredientsWithMeasurements = mutableListOf<IngredientWithMeasurementView>()
        for (i in 1..20) {
            val ingredient = getIngredient(response, i)
            val measure = getMeasure(response, i)

            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                ingredientsWithMeasurements.add(IngredientWithMeasurementView(ingredient, measure))
            }
        }

        return MealDetailsView(
            id = response.idMeal ?: "",
            name = response.strMeal ?: "",
            category = response.strCategory ?: "",
            area = response.strArea ?: "",
            instructions = response.strInstructions ?: "",
            mealThumb = response.strMealThumb ?: "",
            preparationDate = Date(),
            type = "",
            youtubeUrl = response.strYoutube,
            ingredientsWithMeasurements = ingredientsWithMeasurements
        )
    }

    fun entityToViewMealDetails(mealWithIngredients: MealWithIngredients): MealDetailsView {
        val ingredientsWithMeasurements = mealWithIngredients.ingredients.map {
            IngredientWithMeasurementView(it.ingredient.name, it.measurement?.quantity ?: "")
        }

        return MealDetailsView(
            id = mealWithIngredients.meal.id.toString(),
            name = mealWithIngredients.meal.name,
            category = mealWithIngredients.meal.category,
            area = mealWithIngredients.meal.area,
            instructions = mealWithIngredients.meal.instructions,
            mealThumb = mealWithIngredients.meal.mealThumb,
            preparationDate = mealWithIngredients.meal.preparationDate,
            type = mealWithIngredients.meal.type,
            youtubeUrl = mealWithIngredients.meal.youtubeUrl,
            ingredientsWithMeasurements = ingredientsWithMeasurements
        )
    }

    fun viewToEntityMealDetails(mealDetailsView: MealDetailsView): MealEntity {
        return MealEntity(
            id = mealDetailsView.id.toInt(),
            name = mealDetailsView.name,
            category = mealDetailsView.category,
            area = mealDetailsView.area,
            instructions = mealDetailsView.instructions,
            mealThumb = mealDetailsView.mealThumb,
            preparationDate = mealDetailsView.preparationDate,
            type = mealDetailsView.type,
            youtubeUrl = mealDetailsView.youtubeUrl
        )
    }

    fun apiToViewMeal(apiResponse: MealApiResponse): MealView {
        return MealView(
            id = apiResponse.idMeal,
            name = apiResponse.strMeal,
            mealThumb = apiResponse.strMealThumb
        )
    }

    fun entityToViewMeal(entity: MealEntity): MealView {
        return MealView(
            id = entity.id.toString(),
            name = entity.name,
            mealThumb = entity.mealThumb
        )
    }

    private fun getIngredient(meal: MealDetailsApiResponse, index: Int): String? {
        return when(index) {
            1 -> meal.strIngredient1
            2 -> meal.strIngredient2
            3 -> meal.strIngredient3
            4 -> meal.strIngredient4
            5 -> meal.strIngredient5
            6 -> meal.strIngredient6
            7 -> meal.strIngredient7
            8 -> meal.strIngredient8
            9 -> meal.strIngredient9
            10 -> meal.strIngredient10
            11 -> meal.strIngredient11
            12 -> meal.strIngredient12
            13 -> meal.strIngredient13
            14 -> meal.strIngredient14
            15 -> meal.strIngredient15
            16 -> meal.strIngredient16
            17 -> meal.strIngredient17
            18 -> meal.strIngredient18
            19 -> meal.strIngredient19
            20 -> meal.strIngredient20
            else -> null
        }
    }

    private fun getMeasure(meal: MealDetailsApiResponse, index: Int): String? {
        return when(index) {
            1 -> meal.strMeasure1
            2 -> meal.strMeasure2
            3 -> meal.strMeasure3
            4 -> meal.strMeasure4
            5 -> meal.strMeasure5
            6 -> meal.strMeasure6
            7 -> meal.strMeasure7
            8 -> meal.strMeasure8
            9 -> meal.strMeasure9
            10 -> meal.strMeasure10
            11 -> meal.strMeasure11
            12 -> meal.strMeasure12
            13 -> meal.strMeasure13
            14 -> meal.strMeasure14
            15 -> meal.strMeasure15
            16 -> meal.strMeasure16
            17 -> meal.strMeasure17
            18 -> meal.strMeasure18
            19 -> meal.strMeasure19
            20 -> meal.strMeasure20
            else -> null
        }
    }
}
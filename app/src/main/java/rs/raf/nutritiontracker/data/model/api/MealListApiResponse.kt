package rs.raf.nutritiontracker.data.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealListApiResponse(
    val meals: List<MealApiResponse>
)

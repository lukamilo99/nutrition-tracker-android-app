package rs.raf.nutritiontracker.data.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryApiResponse(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

package rs.raf.nutritiontracker.data.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegionNameListApiResponse(
    val meals: List<RegionNameApiResponse>
)

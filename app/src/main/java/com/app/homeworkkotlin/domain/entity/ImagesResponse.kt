package com.app.homeworkkotlin.domain.entity

import com.app.homeworkkotlin.domain.entity.Image
import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("total_results") var resultCount: Int,
    @SerializedName("photos") val images: List<Image>
)
package com.aostrovskiy.hotels.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hotel(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("name") var name: String? = null,
        @SerializedName("address") var address: String? = null,
        @SerializedName("stars") var stars: Int = 0,
        @SerializedName("distance") var distance: Double = 0.toDouble(),
        @SerializedName("image") var image: String? = null,
        var availability: Array<String> = arrayOf()
) : Serializable

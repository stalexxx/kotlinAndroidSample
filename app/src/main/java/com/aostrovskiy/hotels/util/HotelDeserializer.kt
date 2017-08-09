package com.aostrovskiy.hotels.util

import android.annotation.SuppressLint
import com.aostrovskiy.hotels.model.Hotel
import com.google.gson.*
import java.lang.reflect.Type

class HotelDeserializer : JsonDeserializer<Hotel> {
    @SuppressLint("NewApi")
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext): Hotel {
        val hotel = Gson().fromJson(json, Hotel::class.java)

        val jsonObject = json.asJsonObject

        if (jsonObject.has(Constant.HOTEL_FIELD_SUITES_AV)) {
            val elem = jsonObject.get(Constant.HOTEL_FIELD_SUITES_AV)
            if (elem != null && !elem.isJsonNull) {
                if (elem.isJsonPrimitive) {
                    hotel.availability = elem.asString.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
            }
        }
        return hotel
    }
}
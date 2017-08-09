package com.aostrovskiy.hotels.api

import com.aostrovskiy.hotels.model.Hotel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelAPI {

    @GET("hotels")
    fun getHotels(): Observable<List<Hotel>>

    @GET("hotel/{hid}")
    fun getHotel(@Path("hid") id: String): Observable<Hotel>
}

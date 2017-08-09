package com.aostrovskiy.hotels.view.card

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.aostrovskiy.hotels.R
import com.aostrovskiy.hotels.model.Hotel

class CardFillRoutine(val context: Context) {
    fun fill(view: HotelCardView, hotel: Hotel) {
        view.name.text = hotel.name
        view.address.text = hotel.address
        view.distance.text = context.getString(R.string.distance_from_center, hotel.distance)
        view.freeSuites.text = context.getString(R.string.default_card_free_suites, hotel.availability.size)

        view.stars.apply {
            removeAllViews()
            (0..hotel.stars - 1).map {
                ImageView(context).apply {
                    setImageResource(R.drawable.ic_star_black_24dp)
                }
            }.forEach {
                iv ->
                addView(iv, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            }
        }

    }
}
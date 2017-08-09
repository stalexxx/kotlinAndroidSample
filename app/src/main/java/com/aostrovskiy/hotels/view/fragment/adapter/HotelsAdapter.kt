package com.aostrovskiy.hotels.view.fragment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aostrovskiy.hotels.R
import com.aostrovskiy.hotels.model.Hotel
import com.aostrovskiy.hotels.view.card.CardFillRoutine

class HotelsAdapter(var cardFillRoutine: CardFillRoutine) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var comparator: Comparator<Hotel>? = null
    private var hotels: List<Hotel>? = null
    private lateinit var clickHotel: OnClickHotel

    fun setAdapter(hotels: List<Hotel>, clickHotel: OnClickHotel) {
        this.hotels = if (comparator == null) hotels else {
            hotels.sortedWith(comparator!!)
        }

        this.clickHotel = clickHotel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotel_item_row, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hotel = hotels!![position]
        val viewHolder = holder as HotelViewHolder

        cardFillRoutine.fill(holder, hotel)

        viewHolder.cardView.setOnClickListener { clickHotel.onClickHotelItem(hotel) }
    }

    override fun getItemCount(): Int = hotels?.size ?: 0

    fun doSorting(c: Comparator<Hotel>) {
        comparator = c
        hotels = hotels?.sortedWith(c)
    }

    interface OnClickHotel {
        fun onClickHotelItem(hotel: Hotel)
    }

}

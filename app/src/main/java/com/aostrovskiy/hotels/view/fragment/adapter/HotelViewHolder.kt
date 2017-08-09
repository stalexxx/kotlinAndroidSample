package com.aostrovskiy.hotels.view.fragment.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.aostrovskiy.hotels.R
import com.aostrovskiy.hotels.view.card.HotelCardView

class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view), HotelCardView {

    @BindView(R.id.item_hotel_name) override lateinit var name: TextView

    @BindView(R.id.item_hotel_address) override lateinit var address: TextView

    @BindView(R.id.item_hotel_distance) override lateinit var distance: TextView

    @BindView(R.id.item_hotel_free_suites) override lateinit var freeSuites: TextView

    @BindView(R.id.item_hotel_stars) override lateinit var stars: LinearLayout

    @BindView(R.id.cardContainer) lateinit var cardView: CardView

    init {
        ButterKnife.bind(this, view)
    }

}

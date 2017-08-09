package com.aostrovskiy.hotels.view.card

import android.widget.LinearLayout
import android.widget.TextView

interface HotelCardView {
    var name: TextView
        get

    var address: TextView
        get

    var distance: TextView
        get

    var freeSuites: TextView
        get

    var stars: LinearLayout
        get

}
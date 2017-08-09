package com.aostrovskiy.hotels.presenter

import com.aostrovskiy.hotels.api.HotelAPI
import com.aostrovskiy.hotels.model.Field
import com.aostrovskiy.hotels.model.Hotel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


open class ListPresenter @Inject
constructor(private val api: HotelAPI) {

    private lateinit var view: View

    private val subscription = CompositeDisposable()

    interface View {
        fun loading()

        fun onHotelsLoaded(hotels: List<Hotel>)

        fun loadComplete()

        fun sort(comparator: Comparator<Hotel>)
    }

    fun injectView(view: View) {
        this.view = view
    }

    fun getHotels() {
        view.loading()
        subscription.add(api.getHotels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.loadComplete() }
                .onErrorReturnItem(ArrayList<Hotel>())
                .subscribe({ hotel -> view.onHotelsLoaded(hotel) }))
    }

    fun onStop() {
        subscription.clear()
    }

    fun sort(field: Field, asc: Boolean) {
        view.sort(kotlin.Comparator { h1, h2 ->
            when (field) {
                Field.DISTANCE -> java.lang.Double.compare(h1.distance,
                        h2.distance) * if (asc) 1 else -1
                Field.FREE_SUITES -> Integer.compare(h1.availability.size,
                        h2.availability.size) * if (asc) 1 else -1
            }
        })
    }
}

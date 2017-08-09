package com.aostrovskiy.hotels.presenter

import com.aostrovskiy.hotels.api.HotelAPI
import com.aostrovskiy.hotels.model.Hotel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DetailsPresenter @Inject constructor(private val api: HotelAPI) {

    private lateinit var view: View
    private val subscription = CompositeDisposable()

    interface View {
        fun loading()

        fun onHotelLoadedSuccess(hotel: Hotel)

        fun onHotelLoadError(message: String?)

        fun onHotelLoadSuccess()
    }

    fun injectView(view: View) {
        this.view = view
    }

    fun loadHotelDetails(id: String?) {
        if (id != null) {
            view.loading()
            subscription.add(api.getHotel(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate { view.onHotelLoadSuccess() }
                    .subscribe(
                            { hotel ->
                                view.onHotelLoadedSuccess(hotel)
                            }
                    ) { throwable ->
                        view.onHotelLoadError(throwable.message)
                    }
            )
        }
    }


    fun onStop() {
        subscription.clear()
    }
}

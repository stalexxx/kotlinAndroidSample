package com.aostrovskiy.hotels.view.fragment


import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.aostrovskiy.hotels.MainApplication
import com.aostrovskiy.hotels.R
import com.aostrovskiy.hotels.model.Hotel
import com.aostrovskiy.hotels.presenter.DetailsPresenter
import com.aostrovskiy.hotels.util.Constant
import com.aostrovskiy.hotels.util.Transform
import com.aostrovskiy.hotels.view.card.CardFillRoutine
import com.aostrovskiy.hotels.view.card.HotelCardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import javax.inject.Inject

class DetailsFragment : Fragment(), DetailsPresenter.View, HotelCardView {

    @BindView(R.id.item_hotel_name) override lateinit var name: TextView

    @BindView(R.id.item_hotel_address) override lateinit var address: TextView

    @BindView(R.id.item_hotel_distance) override lateinit var distance: TextView

    @BindView(R.id.item_hotel_free_suites) override lateinit var freeSuites: TextView

    @BindView(R.id.item_hotel_stars) override lateinit var stars: LinearLayout

    @BindView(R.id.item_hotel_top_container) lateinit var cardContainer: LinearLayout

    lateinit var hotelImage: ImageView

    @Inject lateinit var presenter: DetailsPresenter
    @Inject lateinit var transform: Transformation<Bitmap>
    @Inject lateinit var cardFillRoutine: CardFillRoutine

    lateinit var progressDialog: ProgressDialog

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        (activity.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.hotel_item_row, container, false)

        ButterKnife.bind(this, view)

        presenter.injectView(this)
        progressDialog = ProgressDialog(context)
        hotelImage = createHotelImage(context)
        cardContainer.addView(hotelImage, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        presenter.loadHotelDetails(arguments.get(Constant.HOTEL_ID_PARAM) as String?)
        return view
    }

    private fun createHotelImage(context: Context?): ImageView {
        val imageView = ImageView(context)
        return imageView
    }

    override fun loading() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    override fun onHotelLoadedSuccess(hotel: Hotel) {
        cardFillRoutine.fill(this, hotel)
        displayAvatarImage("${hotel.image}")
    }

    private fun displayAvatarImage(avatarUrl: String) {
        Glide.with(this).load(avatarUrl).bitmapTransform(transform).diskCacheStrategy(DiskCacheStrategy.RESULT).into(hotelImage)
    }

    override fun onHotelLoadError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onHotelLoadSuccess() {
        progressDialog.cancel()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    companion object {
        fun newInstance(user: Int): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putString(Constant.HOTEL_ID_PARAM, user.toString())
            fragment.arguments = bundle
            return fragment
        }
    }
}


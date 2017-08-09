package com.aostrovskiy.hotels.view.fragment

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import com.aostrovskiy.hotels.MainApplication
import com.aostrovskiy.hotels.R
import com.aostrovskiy.hotels.model.Field
import com.aostrovskiy.hotels.model.Hotel
import com.aostrovskiy.hotels.presenter.ListPresenter
import com.aostrovskiy.hotels.view.activity.MainActivity
import com.aostrovskiy.hotels.view.card.CardFillRoutine
import com.aostrovskiy.hotels.view.fragment.adapter.HotelsAdapter
import java.util.*
import javax.inject.Inject

class ListFragment : Fragment(), ListPresenter.View, HotelsAdapter.OnClickHotel {

    @BindView(R.id.recycled_view_list) lateinit var recyclerView: RecyclerView

    @Inject lateinit var presenter: ListPresenter
    @Inject lateinit var cardFillRoutine: CardFillRoutine

    lateinit var adapter: HotelsAdapter

    lateinit var progressDialog: ProgressDialog

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        (activity.application as MainApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val view = inflater!!.inflate(R.layout.fragment_list, container, false)
        ButterKnife.bind(this, view)

        presenter.injectView(this)
        progressDialog = ProgressDialog(context)

        presenter.getHotels()

        configurationRecyclerView()
        return view
    }

    private fun configurationRecyclerView() {
        adapter = HotelsAdapter(cardFillRoutine)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    override fun loading() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    override fun loadComplete() {
        progressDialog.cancel()
    }

    override fun onClickHotelItem(hotel: Hotel) {
        (activity as MainActivity).onClickHotel(hotel.id)//todo
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onHotelsLoaded(hotels: List<Hotel>) {
        adapter.setAdapter(hotels, this)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.hotel_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_sort_dist_asc -> presenter.sort(Field.DISTANCE, true)
            R.id.menu_action_sort_dist_desc -> presenter.sort(Field.DISTANCE, false)
            R.id.menu_action_sort_suit_asc -> presenter.sort(Field.FREE_SUITES, true)
            R.id.menu_action_sort_suit_desc -> presenter.sort(Field.FREE_SUITES, false)
            else -> {
            }
        }
        return true
    }

    override fun sort(comparator: Comparator<Hotel>) {
        adapter.doSorting(comparator)
        adapter.notifyDataSetChanged()
    }
}

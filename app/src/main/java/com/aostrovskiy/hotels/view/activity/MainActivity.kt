package com.aostrovskiy.hotels.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.aostrovskiy.hotels.R
import com.aostrovskiy.hotels.view.fragment.DetailsFragment
import com.aostrovskiy.hotels.view.fragment.ListFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, ListFragment())
                .commit()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .addToBackStack(null)
                .commit()
    }

    fun onClickHotel(id: Int) {
        changeFragment(DetailsFragment.newInstance(id))
    }
}

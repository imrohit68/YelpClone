package com.example.yelpclone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val Base_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "WW2FRD0ypFq_VGWCh1KxkSsCGI8Vdik3LEBPkApm-c15wTz1ErAHIvZlj5o2kjCIF7erc9c1ZLsag07SlLPxMsLkWc06rN9S5wGxiwCvRKD2tEH3yM8rxesPilFeY3Yx"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        val foodName = intent.getStringExtra("FoodName")
        val Location = intent.getStringExtra("Location")
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)

        val retrofit =
            Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        if (foodName != null) {
            if (Location != null) {
                yelpService.searchRestaurants("Bearer $API_KEY", foodName, Location).enqueue(object : Callback<YelpSearchResult> {
                    override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                        Log.i(TAG, "onResponse $response")
                        val body = response.body()
                        if (body == null) {
                            Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                            return
                        }
                        restaurants.addAll(body.restaurants)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                        Log.i(TAG, "onFailure $t")
                    }
                })
            }
        }
    }
}
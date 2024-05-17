package com.example.githubuser.ui.gthubuserapi

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback

class ViewModelFollower : ViewModel() {

    private val apiService = ApiConfig.getApiService()

    fun getFollowers(username: String?): LiveData<List<DetailUserResponse>> {
        val liveData = MutableLiveData<List<DetailUserResponse>>()
        val call = apiService.getFollowers(username)
        call.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: retrofit2.Response<List<DetailUserResponse>>
            ) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body())
                } else {

                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {

            }
        })
        return liveData
    }

    fun getFollowing(username: String?): LiveData<List<DetailUserResponse>> {
        val liveData = MutableLiveData<List<DetailUserResponse>>()
        val call = apiService.getFollowing(username)
        call.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: retrofit2.Response<List<DetailUserResponse>>
            ) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body())
                } else {

                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {

                Toast.LENGTH_SHORT.toShort()
            }
        })
        return liveData
    }
}
package com.example.githubuser.ui.gthubuserapi

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.FavoriteUserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = ApiConfig.getApiService()

    private var userDao : FavoriteUserDao?
    private var userDb : FavoriteUserRoomDatabase?

    init{
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteuserDao()
    }

    fun getUserDetail(username: String): LiveData<DetailUserResponse> {
        val liveData = MutableLiveData<DetailUserResponse>()
        val call = apiService.getDetailUser(username)
        call.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: retrofit2.Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body())
                } else {

                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Toast.LENGTH_SHORT.toShort()
            }
        })
        return liveData


    }
    fun insert(username: String,id:Int,avatarurl:String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                id,
                username,
                avatarurl
            )
            userDao?.insert(user)
        }

    }

    suspend fun checkUser(id: Int) = userDao?.checkuser(id)

    fun remove(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.remove(id)
        }
    }

}
package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun getGithub(
        @Query("q") query: String
    ): Call<Response>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String?): Call<List<DetailUserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String?): Call<List<DetailUserResponse>>
}
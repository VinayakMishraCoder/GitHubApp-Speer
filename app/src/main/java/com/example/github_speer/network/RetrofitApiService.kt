package com.example.github_speer.network

import com.example.github_speer.datamodels.UserDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiService {
    @GET("users/{user_name}")
    suspend fun getGithubUserData(
        @Path("user_name") userName: String?
    ): UserDataModel

    @GET("users/{user_name}/followers")
    suspend fun getUserFollowersList(
        @Path("user_name") userName: String?
    ): ArrayList<UserDataModel>

    @GET("users/{user_name}/following")
    suspend fun getUserFollowingList(
        @Path("user_name") userName: String?
    ): ArrayList<UserDataModel>
}
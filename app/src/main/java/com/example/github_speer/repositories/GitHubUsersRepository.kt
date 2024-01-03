package com.example.github_speer.repositories

import com.example.github_speer.datamodels.UserDataModel
import com.example.github_speer.network.RetrofitApiService
import javax.inject.Inject

class GitHubUsersRepository @Inject constructor(var apiService: RetrofitApiService) {

    suspend fun getGithubUserData(userName: String?): UserDataModel {
        return apiService.getGithubUserData(userName)
    }

    suspend fun getUserFollowersList(
       userName: String?
    ): ArrayList<UserDataModel> {
        return apiService.getUserFollowersList(userName)
    }

    suspend fun getUserFollowingList(userName: String?): ArrayList<UserDataModel> {
        return apiService.getUserFollowingList(userName)
    }
}
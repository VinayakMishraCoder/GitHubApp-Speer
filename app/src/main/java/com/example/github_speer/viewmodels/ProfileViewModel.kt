package com.example.github_speer.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_speer.datamodels.ResultWrapper
import com.example.github_speer.datamodels.UserDataModel
import com.example.github_speer.repositories.GitHubUsersRepository
import com.example.github_speer.utils.NetworkConnectionUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    var repository: GitHubUsersRepository,
    var networkUtil: NetworkConnectionUtil
): ViewModel() {

    private val _responseData = MutableLiveData<ResultWrapper<UserDataModel>>()
    val responseData: LiveData<ResultWrapper<UserDataModel>> get() = _responseData

    var loading = MutableLiveData<Boolean>(false)

    var userName: String? = null

    init {
        retrieveData()
    }

    fun retrieveData() {
        if(loading.value == true) {
            /**
             * Page already loading, no need to refresh (if trying to refresh).
             * */
            return
        }

        /**
         * Checking if the device is connected to internet.
         * */
        if(!networkUtil.isConnectedToInternet()) {
            _responseData.postValue(ResultWrapper.Error("Error : No Internet Connection detected."))
            return
        }

        viewModelScope.launch {

            loading.value = true
            _responseData.postValue(ResultWrapper.Loading)

            val result = withContext(Dispatchers.IO) {
                try {

                    val responseData = repository.getGithubUserData(userName)
                    /**
                     * Generally Response has no message, it only does in the case of an error.
                     * Just to double check we check for id being null, which indicates no user exists with username.
                     * */
                    /**
                     * Generally Response has no message, it only does in the case of an error.
                     * Just to double check we check for id being null, which indicates no user exists with username.
                     * */
                    if(responseData.message != null && responseData.id == null) ResultWrapper.Error(responseData.message)
                    else ResultWrapper.Success(responseData)

                } catch (e: Exception) {

                    Log.e("error-msg", "Error : ${e.message}")
                    _responseData.postValue(ResultWrapper.Error("Error : ${e.message}"))
                    ResultWrapper.Error("Error fetching data")

                }
            }

            if(result != null) {
                _responseData.postValue(result)
            }

            loading.value = false
        }
    }
}
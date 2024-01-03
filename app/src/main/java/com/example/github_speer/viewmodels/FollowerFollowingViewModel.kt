package com.example.github_speer.viewmodels

import androidx.lifecycle.ViewModel
import com.example.github_speer.repositories.GitHubUsersRepository
import com.example.github_speer.utils.NetworkConnectionUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(
    var repository: GitHubUsersRepository,
    var util: NetworkConnectionUtil
): ViewModel() {




}
package com.example.github_speer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.github_speer.databinding.FragmentFollowerFragmentsBinding
import com.example.github_speer.viewmodels.FollowerFollowingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragments : Fragment() {
    private var userName: String? = null

    private val viewModel by viewModels<FollowerFollowingViewModel>()

    private lateinit var binding: FragmentFollowerFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(USER_NAME_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerFragmentsBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

        private const val USER_NAME_ARG = "user_name_arg"

        @JvmStatic
        fun newInstance(userName: String) =
            FollowerFragments().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME_ARG, userName)
                }
            }
    }
}
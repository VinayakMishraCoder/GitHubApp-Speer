package com.example.github_speer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.github_speer.databinding.FragmentFollowingBinding
import com.example.github_speer.viewmodels.FollowerFollowingViewModel


class FollowingFragment : Fragment() {
    private var userName: String? = null

    private val viewModel by viewModels<FollowerFollowingViewModel>()

    private lateinit var binding: FragmentFollowingBinding

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
        binding = FragmentFollowingBinding.inflate(inflater, container, false)



        return binding.root
    }

    companion object {
        private const val USER_NAME_ARG = "user_name_arg"

        @JvmStatic
        fun newInstance(param1: String, userName: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME_ARG, userName)
                }
            }
    }
}
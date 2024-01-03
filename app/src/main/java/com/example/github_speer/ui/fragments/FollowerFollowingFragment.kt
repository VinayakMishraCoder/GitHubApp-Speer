package com.example.github_speer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.github_speer.adapters.FollowersTabsPagingAdapter
import com.example.github_speer.databinding.FragmentFollowerFollowingBinding

class FollowerFollowingFragment : Fragment() {

    private var userName: String? = null

    private lateinit var binding: FragmentFollowerFollowingBinding

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
        binding = FragmentFollowerFollowingBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = FollowersTabsPagingAdapter(childFragmentManager)

        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    companion object {
        private const val USER_NAME_ARG = "user_name_arg"

        @JvmStatic
        fun newInstance(userName: String) =
            FollowerFollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME_ARG, userName)
                }
            }
    }
}
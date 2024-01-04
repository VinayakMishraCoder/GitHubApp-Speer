package com.example.github_speer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.github_speer.adapters.FollowersTabsPagingAdapter
import com.example.github_speer.databinding.FragmentFollowerFollowingBinding
import com.example.github_speer.utils.GeneralUtil.uniClick
import dagger.hilt.android.AndroidEntryPoint

/**
 * This fragment contains a tab layout which constists of two fragments i.e., FollowerFragment and FollowingFragment.
 * This fragment doesn't have any API calls.
 * */
@AndroidEntryPoint
class FollowerFollowingFragment : Fragment() {

    /**
     * Username retrieved from bundle.
     * */
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

        binding.viewPager.adapter = FollowersTabsPagingAdapter(childFragmentManager, userName ?: "")

        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.topText.text = "${userName}'s Followers and Following."

        binding.backButton.uniClick(false) {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    companion object {
        private const val USER_NAME_ARG = "user_name_arg"
        /**
         * Way to get instance of this fragment while passing the currUsername being processed to it.
         * */
        @JvmStatic
        fun newInstance(userName: String) =
            FollowerFollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME_ARG, userName)
                }
            }
    }
}
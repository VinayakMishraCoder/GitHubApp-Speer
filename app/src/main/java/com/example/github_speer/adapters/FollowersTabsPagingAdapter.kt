package com.example.github_speer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.github_speer.ui.fragments.FollowerFragments
import com.example.github_speer.ui.fragments.FollowingFragment

/**
 * Adapter for Tab layout in Follower-Following Fragment.
 * */
class FollowersTabsPagingAdapter(fragmentManager: FragmentManager, var userName: String) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFragments.newInstance(userName)
            1 -> FollowingFragment.newInstance(userName)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Followers"
            1 -> "Following"
            else -> null
        }
    }
}
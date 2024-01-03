package com.example.github_speer.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.github_speer.R
import com.example.github_speer.databinding.FragmentUserProfileBinding
import com.example.github_speer.datamodels.ResultWrapper
import com.example.github_speer.ui.activities.UserSearchActivity
import com.example.github_speer.utils.GeneralUtil.uniClick
import com.example.github_speer.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var userName: String? = null

    private val viewModel by viewModels<ProfileViewModel>()

    private lateinit var binding: FragmentUserProfileBinding

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
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        binding.mainDataView.visibility = View.GONE

        binding.backButton.uniClick(false) {
            (requireActivity() as UserSearchActivity).onBackPressed()
        }

        binding.followerButton.uniClick {
            launchFollowerFollowingFragment(userName)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.retrieveData()
        }

        viewModel.userName = userName

        observeResponseData()

        return binding.root
    }

    fun observeResponseData() {
        binding.errorEmptyLayout.root.visibility = View.GONE

        viewModel.responseData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ResultWrapper.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                    binding.mainDataView.visibility = View.GONE
                    binding.errorEmptyLayout.root.visibility = View.GONE
                }
                is ResultWrapper.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.mainDataView.visibility = View.VISIBLE
                    binding.errorEmptyLayout.root.visibility = View.GONE
                    binding.copyUserUrl.uniClick {
                        val clipboard: ClipboardManager =
                            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip: ClipData = ClipData.newPlainText("Github Page", response.data.htmlUrl)
                        clipboard.setPrimaryClip(clip)
                    }
                    binding.user = response.data
                }
                is ResultWrapper.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.mainDataView.visibility = View.GONE
                    binding.errorEmptyLayout.root.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun launchFollowerFollowingFragment(userName: String?) {
        if(userName.isNullOrEmpty()) return
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.fragment_container, FollowerFollowingFragment.newInstance(userName))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        private const val USER_NAME_ARG = "user_name_arg"
        @JvmStatic
        fun newInstance(userName: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME_ARG, userName)
                }
            }
    }
}
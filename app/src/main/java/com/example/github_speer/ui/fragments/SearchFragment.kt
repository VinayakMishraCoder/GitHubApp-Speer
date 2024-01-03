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
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.github_speer.R
import com.example.github_speer.databinding.FragmentSearchBinding
import com.example.github_speer.datamodels.ResultWrapper
import com.example.github_speer.utils.GeneralUtil.uniClick
import com.example.github_speer.viewmodels.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModels<SearchUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        observeResponseData()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.loading = viewModel.loading.value

        binding.searchQuery.doOnTextChanged { text, _, _, _ ->
            viewModel.currUserName = text.toString()
        }

        binding.materialButton.uniClick {
            viewModel.retrieveData(viewModel.currUserName)
        }

        binding.followerButton.uniClick(true) {
            launchFollowerFollowingFragment(viewModel.currUserName)
        }

        binding.userDataView.uniClick {
            launchUserProfileFragment(viewModel.currUserName)
        }

        return binding.root
    }

    fun observeResponseData() {
        binding.errorEmptyLayout.root.visibility = View.GONE
        binding.userDataView.visibility = View.GONE

        viewModel.responseData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ResultWrapper.Loading -> {
                    binding.errorEmptyLayout.root.visibility = View.GONE
                    binding.userDataView.visibility = View.GONE
                }
                is ResultWrapper.Success -> {
                    binding.errorEmptyLayout.root.visibility = View.GONE
                    binding.userDataView.visibility = View.VISIBLE
                    binding.user = response.data
                    binding.copyUserUrl.uniClick {
                        val clipboard: ClipboardManager =
                            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip: ClipData = ClipData.newPlainText("Github Page", response.data.htmlUrl)
                        clipboard.setPrimaryClip(clip)
                    }
                }
                is ResultWrapper.Error -> {
                    binding.errorEmptyLayout.root.visibility = View.VISIBLE
                    binding.userDataView.visibility = View.GONE
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun launchUserProfileFragment(userName: String?) {
        if(userName.isNullOrEmpty()) return
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.fragment_container, UserProfileFragment.newInstance(userName))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun launchFollowerFollowingFragment(userName: String?) {
        if(userName.isNullOrEmpty()) return
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.fragment_container, FollowerFollowingFragment.newInstance(userName))
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
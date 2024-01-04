package com.example.github_speer.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.github_speer.R
import com.example.github_speer.adapters.UsersAdapter
import com.example.github_speer.databinding.FragmentFollowingBinding
import com.example.github_speer.datamodels.ResultWrapper
import com.example.github_speer.viewmodels.FollowerFollowingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This Fragment shows the following list of the user in recyclerview.
 * */
@AndroidEntryPoint
class FollowingFragment : Fragment() {
    /**
     * Username retrieved from bundle.
     * */
    private var userName: String? = null

    private val viewModel by viewModels<FollowerFollowingViewModel>()

    private lateinit var binding: FragmentFollowingBinding

    @Inject
    lateinit var adapter: UsersAdapter

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

        adapter.setOnUserCopyClickListener {
            val clipboard: ClipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("Github Page", it.htmlUrl)
            clipboard.setPrimaryClip(clip)
        }

        adapter.setOnUserProfileClickListener {
            launchUserProfileFragment(it.userName)
        }
        binding.recyclerView.adapter = adapter

        observeResponseData()

        viewModel.userName = userName

        viewModel.retrieveData(false)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.retrieveData(false)
        }

        return binding.root
    }

    /**
     * Observing the data and handling errors related to user-following API call.
     * */
    fun observeResponseData() {
        binding.errorEmptyLayout.root.visibility = View.GONE
        viewModel.responseData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ResultWrapper.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                    binding.errorEmptyLayout.root.visibility = View.GONE
                }
                is ResultWrapper.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.errorEmptyLayout.root.visibility = View.GONE
                    adapter.setDataList(response.data)
                }
                is ResultWrapper.Error -> {
                    binding.errorEmptyLayout.root.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun launchUserProfileFragment(userName: String?) {
        if(userName.isNullOrEmpty()) return
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, UserProfileFragment.newInstance(userName))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        private const val USER_NAME_ARG = "user_name_arg"
        /**
         * Way to get instance of this fragment while passing the currUsername being processed to it.
         * */
        @JvmStatic
        fun newInstance(userName: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME_ARG, userName)
                }
            }
    }
}
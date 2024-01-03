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
import com.example.github_speer.databinding.FragmentFollowerFragmentsBinding
import com.example.github_speer.datamodels.ResultWrapper
import com.example.github_speer.viewmodels.FollowerFollowingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowerFragments : Fragment() {

    private var userName: String? = null

    private val viewModel by viewModels<FollowerFollowingViewModel>()

    private lateinit var binding: FragmentFollowerFragmentsBinding

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
        binding = FragmentFollowerFragmentsBinding.inflate(inflater, container, false)

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

        viewModel.retrieveData(true)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.retrieveData(true)
        }

        return binding.root
    }

    fun observeResponseData() {
        binding.errorEmptyLayout.root.visibility = View.GONE
        viewModel.responseData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ResultWrapper.Loading -> {
                    binding.errorEmptyLayout.root.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is ResultWrapper.Success -> {
                    binding.errorEmptyLayout.root.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
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
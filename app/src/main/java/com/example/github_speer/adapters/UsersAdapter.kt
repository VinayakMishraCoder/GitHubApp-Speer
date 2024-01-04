package com.example.github_speer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_speer.databinding.ItemGithubUserBinding
import com.example.github_speer.datamodels.UserDataModel
import com.example.github_speer.utils.GeneralUtil.uniClick
import javax.inject.Inject

/**
 * Below same adapter can be used to show both the followers and following
 * users in the tab adapter.
 * */
class UsersAdapter @Inject constructor(): RecyclerView.Adapter<UsersAdapter.UsersItemViewHolder>() {

    private var dataList = ArrayList<UserDataModel>()

    private var userCopyClickListener: ((UserDataModel) -> Unit)? = null

    private var userProfileClickListener: ((UserDataModel) -> Unit)? = null

    fun setOnUserCopyClickListener(action: (UserDataModel) -> Unit) {
        this.userCopyClickListener = action
    }

    fun setOnUserProfileClickListener(action: (UserDataModel) -> Unit) {
        this.userProfileClickListener = action
    }

    fun setDataList(listings: ArrayList<UserDataModel>?) {
        if(listings != null) {
            this.dataList = listings
        }
        notifyDataSetChanged()
    }

    inner class UsersItemViewHolder(var binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currUser: UserDataModel) {
            binding.user = currUser
            /**
             * On clicking the Copy button, the URL to github page gets copied into the clipboard.
             * */
            binding.copyUserUrl.uniClick(false) {
                userCopyClickListener?.invoke(currUser)
            }
            /**
             * On clicking the whole view, profile page to the clicked user will open.
             * */
            binding.root.uniClick(true) {
                userProfileClickListener?.invoke(currUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersItemViewHolder {
        return UsersItemViewHolder(ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UsersItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int  = dataList.size
}
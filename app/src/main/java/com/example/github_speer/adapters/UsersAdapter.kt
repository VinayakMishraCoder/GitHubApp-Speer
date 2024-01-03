package com.example.github_speer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_speer.databinding.ItemGithubUserBinding
import com.example.github_speer.datamodels.UserDataModel
import com.example.github_speer.utils.GeneralUtil.uniClick
import javax.inject.Inject

class UsersAdapter @Inject constructor(): RecyclerView.Adapter<UsersAdapter.UsersItemViewHolder>() {
    private var dataList = ArrayList<UserDataModel>()

    private var userFollowingClickListener: ((UserDataModel) -> Unit)? = null

    private var userProfileClickListener: ((UserDataModel) -> Unit)? = null

    fun setOnUserFollowingClickListener(action: (UserDataModel) -> Unit) {
        this.userFollowingClickListener = action
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
            binding.followerButton.uniClick(false) {
                userFollowingClickListener?.invoke(currUser)
            }
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
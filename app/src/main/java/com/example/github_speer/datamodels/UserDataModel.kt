package com.example.github_speer.datamodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataModel(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("login")
    val userName: String? = null,

    @SerializedName("name")
    val fullName: String? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("bio")
    val bio: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("followers")
    val followers: Int? = null,

    @SerializedName("following")
    val following: Int? = null,

    @SerializedName("html_url")
    val htmlUrl: String? = null,

    /**
     * Message Received when error occurs.
     * */
    @SerializedName("message")
    val message: String? = null

): Parcelable
package com.zed.kz.doskaz.entity

import com.google.gson.annotations.SerializedName

data class AuthCredential(

    @SerializedName("idToken")
    val idToken: String? = null

)
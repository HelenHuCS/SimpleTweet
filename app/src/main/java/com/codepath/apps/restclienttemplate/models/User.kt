package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class User(
    val name:String,
    val screenName:String,
    val profileImageUrl:String,
) : Parcelable {


    companion object {
        fun fromJson(jsonObject: JSONObject): User {
            return User(
                jsonObject.getString("name"),
                jsonObject.getString("screen_name"),
                jsonObject.getString("profile_image_url_https"),
            )
        }
    }
}
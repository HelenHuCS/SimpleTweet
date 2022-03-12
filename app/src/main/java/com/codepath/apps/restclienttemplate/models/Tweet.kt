package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "Tweet"

@Parcelize
data class Tweet(
    val id:Long,
    val body:String,
    val createdAt:String,
    val user:User?,
    val medias:List<Media>?
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JSONObject): Tweet {
            return Tweet(
                jsonObject.getLong("id"),
                jsonObject.getString("text"),
                jsonObject.getString("created_at"),
                User.fromJson(jsonObject.getJSONObject("user")),
                getMedias(jsonObject)
            )
        }

        fun fromJsonArray(jsonArray: JSONArray): List<Tweet> {
            val tweets = ArrayList<Tweet>()
            for ( i in 0 until jsonArray.length()) {
                tweets.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return tweets
        }

        private fun getMedias(jsonObject: JSONObject): List<Media>?{
            try {
                val entities = jsonObject.getJSONObject("entities")
                return Media.fromJsonArray(entities.getJSONArray("media"))
            } catch (e: JSONException) {
                Log.e(TAG, "getMedias: ", e)
                return null
            }
        }
    }
}
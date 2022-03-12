package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

@Parcelize
class Media(
    val mediaUrlHttps:String,
    val type:String = "photo"
):Parcelable {


    companion object {
        fun fromJson(jsonObject: JSONObject): Media {
            return Media(
                jsonObject.getString("media_url_https"),
                jsonObject.getString("type")
            )
        }

        fun fromJsonArray(jsonArray: JSONArray): List<Media> {
            val medias = ArrayList<Media>()
            for (i in 0 until jsonArray.length()) {
                medias.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return medias
        }
    }
}
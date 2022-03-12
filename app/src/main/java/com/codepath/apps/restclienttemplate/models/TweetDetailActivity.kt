package com.codepath.apps.restclienttemplate.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.TweetAdapter

class TweetDetailActivity : AppCompatActivity() {

    private lateinit var ivProfile:ImageView
    private lateinit var tvUsername:TextView
    private lateinit var tvScreenName:TextView
    private lateinit var tvTime:TextView
    private lateinit var tvTweetBody:TextView
    private lateinit var llMedia:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet_detail)

        ivProfile = findViewById(R.id.ivProfile)
        tvUsername = findViewById(R.id.tvUsername)
        tvScreenName = findViewById(R.id.tvScreenName)
        tvTime = findViewById(R.id.tvTime)
        tvTweetBody = findViewById(R.id.tvTweetBody)
        llMedia = findViewById(R.id.llMeida)

        val tweet = intent.getParcelableExtra<Tweet>("tweet") as Tweet
        tvUsername.text = tweet.user?.name
        tvScreenName.text = tweet.user?.screenName
        tvTime.text = TweetAdapter.generateTime(tweet.createdAt)
        tvTweetBody.text = tweet.body
        Glide.with(this)
            .load(tweet.user?.profileImageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(ivProfile)

        if (tweet.medias != null) {
            for (media in tweet.medias) {
                if (media.type == "photo") {
                    val iv = ImageView(baseContext)
                    llMedia.addView(iv)
                    Glide.with(this).load(media.mediaUrlHttps).into(iv)
                }
            }
        } else {
            llMedia.visibility = View.GONE
        }
    }
}
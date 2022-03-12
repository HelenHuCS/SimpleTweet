package com.codepath.apps.restclienttemplate

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.apps.restclienttemplate.models.TweetDetailActivity
import java.text.SimpleDateFormat
import java.util.*

class TweetAdapter(val activity:TimelineActivity,val tweets:ArrayList<Tweet>): RecyclerView.Adapter<TweetAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_tweet,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TweetAdapter.ViewHolder, position: Int) {
        val tweet: Tweet = tweets.get(position)
        var hasPhoto = false

        holder.tvUsername.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body
        holder.tvScreenName.text = "@"+tweet.user?.screenName
        holder.tvTime.text = generateTime(tweet.createdAt)
        if (tweet.medias != null) {
            val media = tweet.medias!![0]
            if (media.type == "photo") {
                Glide.with(holder.itemView).load(media.mediaUrlHttps).into(holder.ivMedia)
                hasPhoto = true
            }
        }
        if (!hasPhoto) holder.ivMedia.visibility = View.GONE

        Glide.with(holder.itemView)
            .load(tweet.user?.profileImageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(holder.ivProfile)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val ivProfile = itemView.findViewById<ImageView>(R.id.ivProfile)
        val tvUsername = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvScreenName = itemView.findViewById<TextView>(R.id.tvScreenName)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
        val ivMedia = itemView.findViewById<ImageView>(R.id.ivMedia)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val tweet = tweets[adapterPosition]
            val intent = Intent(activity,TweetDetailActivity::class.java)
            intent.putExtra("tweet",tweet)
            activity.startActivity(intent)
        }
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: List<Tweet>){
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    companion object {
         fun generateTime(tstring: String):String {
            val createTime = Date(tstring)
            val currentTime = Date()
            val diff = currentTime.time - createTime.time
            currentTime.toString()

            val nd = (1000 * 24 * 60 * 60).toLong()
            val nh = (1000 * 60 * 60).toLong()
            val nm = (1000 * 60).toLong()
            val ns = 1000.toLong()

            val day = diff / nd
            val hour = diff % nd / nh
            val min = diff % nd % nh / nm
            val sec = diff % nd % nh % nm / ns

            var ret = ""
            if (day>0) ret+="$day"+"d"
            if (hour>0) ret+="$hour"+"h"
            if (min>0) ret+="$min"+"m"
            return ret
        }
    }

}
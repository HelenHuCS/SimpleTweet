package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.codepath.apps.restclienttemplate.models.SampleModel
import com.codepath.apps.restclienttemplate.models.SampleModelDao
import com.codepath.oauth.OAuthLoginActionBarActivity
private const val TAG = "LoginActivity"
class LoginActivity : OAuthLoginActionBarActivity<TwitterClient>() {

    var sampleModelDao: SampleModelDao? = null
    lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setTitle(getString(R.string.app_name))
        toolbar.setTitleTextColor(resources.getColor(R.color.white))

        val sampleModel = SampleModel()
        sampleModel.name = "CodePath"
        sampleModelDao = (applicationContext as TwitterApplication).myDatabase?.sampleModelDao()
        AsyncTask.execute { sampleModelDao?.insertModel(sampleModel) }
    }


    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login, menu)
        return true
    }

    // OAuth authenticated successfully, launch primary authenticated activity
    // i.e Display application "homepage"
    override fun onLoginSuccess() {
        Log.i(TAG, "onLoginSuccess: successfully")
        val i = Intent(this,TimelineActivity::class.java)
        startActivity(i)
    }

    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    override fun onLoginFailure(e: Exception) {
        e.printStackTrace()
        Log.d(TAG, "onLoginFailure: failed")
    }

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    fun loginToRest(view: View?) {
        client.connect()
    }
}
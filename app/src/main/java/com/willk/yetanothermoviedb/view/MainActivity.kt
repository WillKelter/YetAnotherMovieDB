package com.willk.yetanothermoviedb.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.willk.yetanothermoviedb.BuildConfig
import com.willk.yetanothermoviedb.R
import com.willk.yetanothermoviedb.data.remote.NetworkChangeReceiver

val listFragment = ListFragment()

val networkChangeReceiver : NetworkChangeReceiver =
    NetworkChangeReceiver()


private const val START_STATE_TAG = "START_STATE"

class MainActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        val intentFilter : IntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver,intentFilter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.lo_frame, listFragment, START_STATE_TAG)
            .commit()

        Log.d("dataLog", BuildConfig.SERVER_URL)
        Log.d("dataLog", BuildConfig.API_KEY)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }
}
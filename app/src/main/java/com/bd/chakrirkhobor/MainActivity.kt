package com.bd.chakrirkhobor

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        var FACEBOOK_URL = "https://www.facebook.com/chakrirkhobornets"
        var FACEBOOK_PAGE_ID = "381462145568985"
    }


    private var customTabHelper: CustomTabHelper = CustomTabHelper()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Floting action button
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            if (isOnline(applicationContext)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data =
                    Uri.parse("market://details?id=com.bd.chakrirkhobor")
                startActivity(i)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            } else {
                Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_LONG)
                    .show()
            }
        }

        //Floting action button

    }


    //For internet connection
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
    //End internet connection


    //Open facebook page
    private fun getOpenFacebookIntent(): Intent? {
        return try {
            packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/$FACEBOOK_PAGE_ID"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.FACEBOOK_URL))
        }
    }

    //End opne facebook page

    // For  menu  button
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.socialFb -> {
                if (isOnline(applicationContext)) {
                    startActivity(getOpenFacebookIntent())
                } else {
                    Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG)
                        .show();
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

// For menu button

    // Open url in default web browser
    fun openURL(url: String) {
        val builder = CustomTabsIntent.Builder()

        // modify toolbar color
        builder.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))

        // add share button to overflow menu
        builder.addDefaultShareMenuItem()

        val anotherCustomTab = CustomTabsIntent.Builder().build()

        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.setData(Uri.parse(url))

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // add menu item to oveflow
        builder.addMenuItem("Sample item", pendingIntent)

        // Source url
        // https://blog.mindorks.com/android-browser-lets-launch-chrome-custom-tabs-with-kotlin
        builder.setShowTitle(true)

        // animation for enter and exit of tab
        builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)
        builder.setExitAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out)


        val customTabsIntent = builder.build()

        // check is chrom available
        val packageName = url?.let { customTabHelper.getPackageNameToUse(this, it) }

        if (packageName == null) {
            // if chrome not available open in web view
            val urlOpne = Intent(android.content.Intent.ACTION_VIEW)
            urlOpne.data = Uri.parse(url)
            startActivity(urlOpne)
        } else {
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
    }

    // End function for default web browser


    // Different button action
    @RequiresApi(Build.VERSION_CODES.M)
    fun jobsToday(view: View) {
        if (isOnline(applicationContext)) {
            openURL(getString(R.string.jobsToday_url))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun bankJobs(view: View) {

        if (isOnline(applicationContext)) {
            openURL((getString(R.string.bankJobs_url)))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun govtJobs(view: View) {

        if (isOnline(applicationContext)) {
            openURL(getString(R.string.govtJobs_url))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun ngoJobs(view: View) {

        if (isOnline(applicationContext)) {
            openURL(getString(R.string.ngoJobs_url))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun newsPaper(view: View) {

        if (isOnline(applicationContext)) {
            openURL(getString(R.string.newsPaper_url))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun teletalk(view: View) {
        if (isOnline(applicationContext)) {
            openURL(getString(R.string.teletalk_url))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun result(view: View) {

        if (isOnline(applicationContext)) {
            openURL(getString(R.string.result_url))
        } else {
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

}
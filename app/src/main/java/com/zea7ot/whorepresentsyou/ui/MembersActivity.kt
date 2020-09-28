package com.zea7ot.whorepresentsyou.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.zea7ot.whorepresentsyou.R
import com.zea7ot.whorepresentsyou.api.WhoRepresentsYouService
import com.zea7ot.whorepresentsyou.ui.adapter.AdapterMembers
import com.zea7ot.whorepresentsyou.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val PERMISSION_REQUEST_LOCATION = 0

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private companion object {
        private val TAG = MainActivity::class.simpleName

        private val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private var disposable: Disposable? = null

    private val whoRepresentsYouService by lazy {
        WhoRepresentsYouService.create()
    }

    private fun getMembers(zipcode: String) {
        disposable = whoRepresentsYouService.getAllMembers(zipcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val adapter = AdapterMembers(this, result.members)
                    listView.adapter = adapter
                },
                { error -> Log.e(TAG, "$error") }
            )
    }

    private lateinit var layout: View
    private lateinit var fab: FloatingActionButton
    private lateinit var listView: ListView

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.main_layout)
        listView = findViewById(R.id.lv_main_activity)
        fab = findViewById(R.id.fab_main_activity)

        fab.setOnClickListener {
            // requestLocationUpdate()
            getMembers("98052")
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate() {
        if (checkAllPermissionsGranted(locationPermissions)) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                Log.d(TAG, "$location")
                Snackbar.make(
                    layout,
                    "Latitude: ${location?.latitude}, Longitude: ${location?.longitude}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionsRationaleCompat(locationPermissions)) {
            requestPermissionCompat(locationPermissions, PERMISSION_REQUEST_LOCATION)
        } else {
            requestPermissionCompat(locationPermissions, PERMISSION_REQUEST_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdate()
                } else {
                    Snackbar.make(layout, "Permission Denied", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
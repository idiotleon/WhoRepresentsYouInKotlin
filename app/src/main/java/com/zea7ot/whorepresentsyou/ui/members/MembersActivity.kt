package com.zea7ot.whorepresentsyou.ui.members

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.zea7ot.whorepresentsyou.R
import com.zea7ot.whorepresentsyou.ui.members.adapter.AdapterMembers
import com.zea7ot.whorepresentsyou.util.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

const val PERMISSION_REQUEST_LOCATION = 0

@AndroidEntryPoint
class MembersActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private companion object {
        private val TAG = MembersActivity::class.simpleName

        private val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private val viewModel: MembersViewModel by viewModels()

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
            // viewModel.members
            viewModel.getAllMembers("98052")
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.allMembers.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Timber.d(TAG, "successful, setupObservers()")
                    val adapterMembers = AdapterMembers(this, ArrayList(it.data?.members))
                    listView.adapter = adapterMembers
                }

                Resource.Status.ERROR -> {
                    Timber.e(TAG, "${it.message}")
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    Timber.d(TAG, "being loaded")
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate() {
        if (checkAllPermissionsGranted(locationPermissions)) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                Timber.d(TAG, "$location")
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
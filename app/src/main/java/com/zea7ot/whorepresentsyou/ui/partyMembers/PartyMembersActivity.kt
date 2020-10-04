package com.zea7ot.whorepresentsyou.ui.partyMembers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.zea7ot.whorepresentsyou.R
import com.zea7ot.whorepresentsyou.receiver.ServiceResultReceiver
import com.zea7ot.whorepresentsyou.service.LocationFetchIntentService
import com.zea7ot.whorepresentsyou.ui.partyMembers.adapter.AdapterPartyMembers
import com.zea7ot.whorepresentsyou.util.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

const val PERMISSION_REQUEST_LOCATION = 0

@AndroidEntryPoint
class PartyMembersActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback,
    ServiceResultReceiver.Receiver {
    private companion object {
        private val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private val viewModelPartyMembers: PartyMembersViewModel by viewModels()

    private lateinit var layout: View
    private lateinit var fab: FloatingActionButton
    private lateinit var listView: ListView

    private lateinit var serviceResultReceiver: ServiceResultReceiver

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.main_layout)
        listView = findViewById(R.id.lv_main_activity)
        fab = findViewById(R.id.fab_main_activity)

        fab.setOnClickListener {
            requestLocationUpdate()
        }

        serviceResultReceiver = ServiceResultReceiver(
            this as ServiceResultReceiver.Receiver,
            Looper.myLooper()?.let { Handler(it) })

        setupObservers()
    }

    private fun setupObservers() {
        viewModelPartyMembers.fetchedPartyMember.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Timber.d("successful, setupObservers()")
                    it.data?.members?.let { partyMembers ->
                        listView.adapter = AdapterPartyMembers(this, partyMembers)

                        for (partyMember in partyMembers) {
                            Timber.d("partyMember: ${partyMember.name}")
                            viewModelPartyMembers.insert(partyMember)
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    Timber.e("${it.message}")
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    Timber.d("being loaded")
                }
            }
        })

//        viewModelPartyMembers..observe(this, {
//            Timber.d(TAG, "Items have been inserted")
//        })
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate() {
        if (checkAllPermissionsGranted(locationPermissions)) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                Timber.d("$location")

                val intent = Intent(this, LocationFetchIntentService::class.java).apply {
                    putExtra(LocationFetchIntentService.IDENTITY_LATITUDE, location?.latitude)
                    putExtra(LocationFetchIntentService.IDENTITY_LONGITUDE, location?.longitude)
                }
                LocationFetchIntentService.enqueueWork(this, serviceResultReceiver, intent)
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
                if (grantResults.size != locationPermissions.size) {
                    Snackbar.make(layout, "Permission Denied", Snackbar.LENGTH_SHORT).show()
                } else if (grantResults.size == locationPermissions.size) {
                    for (result in grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Snackbar.make(
                                layout,
                                "One of the Permissions Denied",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            break
                        }
                    }
                } else {
                    requestLocationUpdate()
                }
            }
        }
    }

    // to fetch members based on the zip code acquired
    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        Timber.d("onReceiveResult(,) called")
        when (resultCode) {
            LocationFetchIntentService.RESULT_CODE_ADDRESS -> {
                resultData?.let {
                    // val zipCode = it[LocationFetchIntentService.IDENTITY_ADDRESS] as String
                    // viewModelPartyMembers.getPartyMembersRemotelyAsync(zipCode)
                    viewModelPartyMembers.getPartyMembersRemotelyAsync("98052")
                }
            }
            else -> {

            }
        }
    }
}
package com.zea7ot.whorepresentsyou.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import androidx.annotation.NonNull
import androidx.core.app.JobIntentService
import com.zea7ot.whorepresentsyou.data.repository.GoogleApiRepository
import com.zea7ot.whorepresentsyou.receiver.ServiceResultReceiver
import com.zea7ot.whorepresentsyou.util.DefaultValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LocationFetchIntentService : JobIntentService() {

    @Inject
    lateinit var googleApiRepository: GoogleApiRepository

    companion object {
        private const val ACTION_FETCH_ADDRESS = "action.FETCH_ADDRESS"
        private const val JOB_ID_UPDATE_LOCATION = 1000

        const val IDENTITY_ADDRESS = "identity_address"
        const val IDENTITY_LATITUDE = "identity_latitude"
        const val IDENTITY_LONGITUDE = "identity_longitude"

        const val IDENTITY_RECEIVER = "identity_receiver"
        const val RESULT_CODE_ADDRESS = 1001

        fun enqueueWork(
            context: Context,
            workResultReceiver: ServiceResultReceiver,
            intent: Intent
        ) {
            intent.apply {
                putExtra(IDENTITY_RECEIVER, workResultReceiver)
                action = ACTION_FETCH_ADDRESS
            }

            enqueueWork(
                context,
                LocationFetchIntentService::class.java,
                JOB_ID_UPDATE_LOCATION,
                intent
            )
        }
    }

    override fun onHandleWork(@NonNull intent: Intent) {
        Timber.d("onHandleWork() called")
        intent.action?.let {
            when (it) {
                ACTION_FETCH_ADDRESS -> {
                    intent.getParcelableExtra<ResultReceiver>(IDENTITY_RECEIVER)?.let { receiver ->
                        try {
                            // to fetch the zip code based on the location(latitude and longitude)
                            val latitude = intent.getDoubleExtra(IDENTITY_LATITUDE, 0.toDouble())
                            val longitude = intent.getDoubleExtra(IDENTITY_LONGITUDE, 0.toDouble())
                            CoroutineScope(Dispatchers.Default).launch {
                                val addresses = getAddresses(latitude, longitude)
                                Timber.d("onHandleWork(): ${addresses.toString()}")
                                addresses.data?.getZipCode()?.let { zipCode ->
                                    Timber.d("onHandleWork(), zipCode: $zipCode")
                                    if (zipCode != DefaultValue.Invalid.POSTAL_CODE_STRING) {
                                        // to send the result back
                                        receiver.send(
                                            RESULT_CODE_ADDRESS,
                                            // dummy data for now
                                            Bundle().apply { putString(IDENTITY_ADDRESS, zipCode) })
                                    }
                                }
                            }
                        } catch (ex: InterruptedException) {
                            ex.printStackTrace()
                            Timber.e("onHandleWork(): ${ex.stackTraceToString()}")
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    private suspend fun getAddresses(latitude: Double, longitude: Double) =
        googleApiRepository.getAddresses(latitude, longitude)
}
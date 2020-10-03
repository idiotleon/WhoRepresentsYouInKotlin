package com.zea7ot.whorepresentsyou.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import androidx.annotation.NonNull
import androidx.core.app.JobIntentService
import com.zea7ot.whorepresentsyou.receiver.ServiceResultReceiver
import timber.log.Timber

class LocationFetchIntentService : JobIntentService() {
    companion object {
        private val TAG = LocationFetchIntentService::class.simpleName

        const val ACTION_FETCH_LOCATION = "action.UPDATE_LOCATION"
        private const val JOB_ID_UPDATE_LOCATION = 1000

        const val IDENTITY_ADDRESS = "identity_address"

        // const val IDENTITY_LOCATION = "identity_location"
        const val IDENTITY_RECEIVER = "identity_receiver"
        const val RESULT_CODE_ADDRESS = 1001

        fun enqueueWork(context: Context, workResultReceiver: ServiceResultReceiver) {
            val intent = Intent().apply {
                putExtra(IDENTITY_RECEIVER, workResultReceiver)
                action = ACTION_FETCH_LOCATION
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
        Timber.d(TAG, "onHandleWork() called")
        intent.action?.let {
            when (it) {
                ACTION_FETCH_LOCATION -> {
                    intent.getParcelableExtra<ResultReceiver>(IDENTITY_RECEIVER)?.let { receiver ->
                        try {
                            // to fetch the zip code based on the location(latitude and longitude)

                            // to send the result back
                            receiver.send(
                                RESULT_CODE_ADDRESS,
                                // dummy data for now
                                Bundle().apply { putString(IDENTITY_ADDRESS, "98052") })
                        } catch (ex: InterruptedException) {
                            ex.printStackTrace()
                            Timber.e(TAG, "onHandleWork(): ${ex.stackTraceToString()}")
                        }
                    }
                }
                else -> {

                }
            }
        }
    }
}
package com.zea7ot.whorepresentsyou.receiver

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

class ServiceResultReceiver(
    private val receiver: Receiver?,
    handler: Handler?
) :
    ResultReceiver(handler) {

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        receiver?.onReceiveResult(resultCode, resultData)
    }

    interface Receiver {
        fun onReceiveResult(resultCode: Int, resultData: Bundle?)
    }
}
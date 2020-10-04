package com.zea7ot.whorepresentsyou.ui.paryMemberDetails

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.zea7ot.whorepresentsyou.R
import com.zea7ot.whorepresentsyou.data.entity.PartyMember
import com.zea7ot.whorepresentsyou.ui.partyMembers.PERMISSION_REQUEST_LOCATION
import com.zea7ot.whorepresentsyou.util.checkAllPermissionsGranted
import com.zea7ot.whorepresentsyou.util.requestPermissionCompat
import com.zea7ot.whorepresentsyou.util.shouldShowRequestPermissionsRationaleCompat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MemberDetailsActivity : AppCompatActivity() {
    companion object {
        private val permissionsRequired = arrayOf(Manifest.permission.INTERNET)

        private const val PERMISSION_REQUEST = 0

        private const val EXTRA_NAME = "title"
        private const val EXTRA_LINK = "link"

        fun newIntent(context: Context, member: PartyMember) =
            Intent(context, MemberDetailsActivity::class.java).apply {
                putExtra(EXTRA_NAME, member.name)
                putExtra(EXTRA_LINK, member.link)
            }
    }

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_detail)

        val name = intent.extras?.getString(EXTRA_NAME)
        val link = intent.extras?.getString(EXTRA_LINK)

        title = name

        webView = findViewById(R.id.wv_detail_member)

        if (checkAllPermissionsGranted(permissionsRequired)) {
            link?.let {
                Timber.d("link: $it")
                webView.settings.javaScriptEnabled = false
                // webView.loadUrl("http://www.google.com")
                webView.loadUrl(it)
            }
        } else {
            requestRequiredPermissions()
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
                    requestRequiredPermissions()
                } else {
                    Snackbar.make(webView, "Permission Denied", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestRequiredPermissions() {
        if (shouldShowRequestPermissionsRationaleCompat(permissionsRequired)) {
            requestPermissionCompat(permissionsRequired, PERMISSION_REQUEST)
        } else {
            requestPermissionCompat(permissionsRequired, PERMISSION_REQUEST)
        }
    }
}
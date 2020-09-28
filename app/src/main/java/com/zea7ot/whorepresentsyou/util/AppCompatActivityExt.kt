package com.zea7ot.whorepresentsyou.util

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)

fun AppCompatActivity.checkAllPermissionsGranted(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (ActivityCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        )
            return false
    }

    return true
}

fun AppCompatActivity.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.shouldShowRequestPermissionsRationaleCompat(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        val shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
        if (!shouldShow) return false
    }

    return true
}

fun AppCompatActivity.requestPermissionCompat(permissions: Array<String>, requestCode: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestCode)
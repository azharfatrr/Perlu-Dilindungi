package com.perlu_dilindungi.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener

object LocationHelper {
    fun checkPermissions(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Ask Permission if Denied.
            requestPermissions(context)
            return false
        }
        return true
    }

    private fun requestPermissions(context: Context) {
        // List of permissions needed.
        val permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        ActivityCompat.requestPermissions(
            context as Activity,
            permissions.toTypedArray(),
            42
        )
    }

    @SuppressLint("MissingPermission") // Permission already checked.
            /**
             * Get the last Location of user.
             */
    fun getLastLocation(context: Context, onCompleteListener: OnCompleteListener<Location>) {
        // Check the permission.
        if (!checkPermissions(context)) {
            return
        }

        // Create fusedLocationClient.
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Add onComplete Listener.
        fusedLocationClient.lastLocation.addOnCompleteListener(onCompleteListener)
    }

    @SuppressLint("MissingPermission")
            /**
             * Get the current Location of user.
             */
    fun getCurrentLocation(context: Context, onCompleteListener: OnCompleteListener<Location>) {
        // Check the permission.
        if (!checkPermissions(context)) {
            return
        }

        // Create fusedLocationClient.
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Add cancellation token and onCompleteListener.
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        )
            .addOnCompleteListener(onCompleteListener)
    }
}
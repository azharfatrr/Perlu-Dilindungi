package com.perlu_dilindungi.ui.qrScanner

import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.perlu_dilindungi.R
import com.perlu_dilindungi.helper.LocationHelper
import kotlinx.android.synthetic.main.activity_qr_scanner.*
import kotlin.properties.Delegates


class QRScannerActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var codeScanner: CodeScanner
    private lateinit var sensorManager: SensorManager
    private lateinit var tempSensor: Sensor
    private var isTempAvailable by Delegates.notNull<Boolean>()

    //private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: QRScannerViewModel by viewModels()
    //private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            isTempAvailable = true
        } else {
            tv_temp.text = "N/A"
            isTempAvailable = false
        }
        setupPermissions()
        codeScanner()

        // Hide the action bar.
        supportActionBar?.hide();

        // Set the back button listener.
        back_button.setOnClickListener {
            finish()
        }
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scn)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    viewModel.getQrCode(it.text)
                    updateLocation()
                    viewModel.postQRScanner()
                    tv_text.text = viewModel.reason.value
                    tv_text2.text = viewModel.reasonfalse.value
                    if (viewModel.checkInStatus.value == true) {
                        imageView.setImageResource(R.drawable.qr_benar_foreground)
                    } else {
                        imageView.setImageResource(R.drawable.qr_salah_foreground)
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "codeScanner: ${it.message}")
                }
            }

            scn.setOnClickListener {
                codeScanner.startPreview()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()


        updateLocation()
        if (isTempAvailable) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()

        if (isTempAvailable) {
            sensorManager.unregisterListener(this)
        }

    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQ
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    companion object {
        private const val CAMERA_REQ = 101
    }

    /**
     * Update the location in viewModels.
     */
    private fun updateLocation() {

        LocationHelper.getCurrentLocation(this) {
            // Get the location or return.
            val location = it.result ?: return@getCurrentLocation
            viewModel.updateLocation(location)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            tv_temp.text = p0.values[0].toString() + "ºC"
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}
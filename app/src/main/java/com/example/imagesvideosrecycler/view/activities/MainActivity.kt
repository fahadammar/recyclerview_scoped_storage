package com.example.imagesvideosrecycler.view.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.imagesvideosrecycler.R
import com.example.imagesvideosrecycler.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val TAG = MainActivity::class.java.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // getting the images via the scoped storage and logging them
        viewModel.getImages()

        askPermission()
    }

    private fun askPermission() {
        // Check if the READ_EXTERNAL_STORAGE permission has been granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission has not been granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            Log.d(
                TAG,
                "askPermission: The READ_EXTERNAL_STORAGE permission has already been granted"
            )
        }
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission has been granted
                    // Call the code that requires the permission here
                    askPermission()
                } else {
                    // Permission has been denied
                    // Handle the denial here
                    Toast.makeText(this, "The permission has been denied", Toast.LENGTH_SHORT)
                        .show()
                }
                return
            }
            // Add additional cases for other permissions you may have requested
            else -> {
                askPermission()
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
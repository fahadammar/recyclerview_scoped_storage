package com.example.imagesvideosrecycler.view.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesvideosrecycler.R
import com.example.imagesvideosrecycler.databinding.ActivityMainBinding
import com.example.imagesvideosrecycler.view.adapter.ImageAdapter
import com.example.imagesvideosrecycler.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val TAG = MainActivity::class.java.toString()
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        askPermission()

        setRecyclerView(viewBinding, viewModel)
    }

    private fun setRecyclerView(viewBinding: ActivityMainBinding, viewModel: MainViewModel) {
        Log.d(TAG, "setRecyclerView: Images ArrayList -> ${viewModel.getImages()}")
        viewBinding.imageRecyclerView.layoutManager = GridLayoutManager(this,2)
        val adapter = ImageAdapter(this, viewModel.getImages())
        viewBinding.imageRecyclerView.adapter = adapter
    }

    private fun permissions() : Array<String> {
        val PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }
        return PERMISSIONS
    }
    private fun askPermission() {
        // Check if the READ_EXTERNAL_STORAGE permission has been granted
        // Permission has not been granted, request it
        ActivityCompat.requestPermissions(
            this,
            permissions(),
            PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
        )
    }

    // Handle the result of the permission request
   /* override fun onRequestPermissionsResult(
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
                    askPermission()
                }
                return
            }
            // Add additional cases for other permissions you may have requested
            else -> {
                askPermission()
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/
}
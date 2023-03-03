package com.example.imagesvideosrecycler.view.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
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
import com.example.imagesvideosrecycler.utils.PaginationScrollListener
import com.example.imagesvideosrecycler.view.adapter.ImageAdapter
import com.example.imagesvideosrecycler.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val TAG = MainActivity::class.java.toString()
    private lateinit var viewBinding: ActivityMainBinding

    private var tempArrayList: ArrayList<Uri> = ArrayList()
    var itemsToGet: Int = 10

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
        val layoutManager = GridLayoutManager(this, 2)
        viewBinding.imageRecyclerView.layoutManager = layoutManager
        val adapter = ImageAdapter(this, getFirstItems(viewModel))
        viewBinding.imageRecyclerView.adapter = adapter

        viewBinding.imageRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun onLoadMore(totalItemCount: Int) {
                // checkItemsToGet(viewModel)
                tempArrayList =
                    viewModel.getImages().take(checkItemsToGet(viewModel)) as ArrayList<Uri>
                adapter.images.addAll(tempArrayList)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun getFirstItems(viewModel: MainViewModel): ArrayList<Uri> {
        if (viewModel.getImages().size >= 10) {
            tempArrayList = viewModel.getImages().take(10) as ArrayList<Uri>
            Log.d(TAG, "getFirstItems: Temp Array List --> $tempArrayList")
            return tempArrayList
        } else return viewModel.getImages()
    }

    private fun checkItemsToGet(viewModel: MainViewModel): Int {
        val totalItems = viewModel.getImages().size - 1
        if (itemsToGet < totalItems) {
            itemsToGet += 10
        }
        Log.d(TAG, "checkItemsToGet: Items To Get --> $itemsToGet")
        return itemsToGet
    }

    private fun permissions(): Array<String> {
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
}
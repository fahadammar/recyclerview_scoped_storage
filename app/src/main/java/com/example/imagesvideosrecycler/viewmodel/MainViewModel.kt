package com.example.imagesvideosrecycler.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesvideosrecycler.model.ImagesRepository
import kotlinx.coroutines.*

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    private val TAG = MainViewModel::class.java.toString()
    private val imageRepositoryInstance = ImagesRepository()
    var imagesArrayList = ArrayList<Uri>()

    fun getImages() : ArrayList<Uri> {
        // viewModeScope means less boilerplate code
        getImagesFromRepository()
        Log.d(TAG, "getImages: Images List -> $imagesArrayList")
        return imagesArrayList
    }

    private fun getImagesFromRepository() {
            val imagesList = imageRepositoryInstance.getImages(application)
            if (imagesList != null) {
                for (image in imagesList) {
                    imagesArrayList.add(image)
                }
            }
    }
}
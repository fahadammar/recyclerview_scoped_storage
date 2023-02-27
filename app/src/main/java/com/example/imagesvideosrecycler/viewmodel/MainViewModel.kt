package com.example.imagesvideosrecycler.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesvideosrecycler.model.ImagesRepository
import kotlinx.coroutines.*

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    private val TAG = MainViewModel::class.java.toString()
    private val imageRepositoryInstance = ImagesRepository()
    private var imagesArrayList = ArrayList<String>()

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getImages() {
        // viewModeScope means less boilerplate code
        viewModelScope.launch {
            getImagesFromRepository()
            Log.d(TAG, "getImages: Images List -> $imagesArrayList")
        }
    }

    private suspend fun getImagesFromRepository() = withContext(Dispatchers.IO) {
        val imagesList = imageRepositoryInstance.getImages(application)
        if (imagesList != null) imagesArrayList = imagesList
    }
}
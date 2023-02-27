package com.example.imagesvideosrecycler.model

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore


class ImagesRepository {
    /**
     * MediaStore.Images.Media.DATE_TAKEN is a constant that represents the column name for the date and time when an image was taken or created.
     * This column is available in the MediaStore.Images.Media class and is used to sort and query images based on their capture time.
     * */
    private val imagesDateTime = MediaStore.Images.Media.DATE_TAKEN

    // The projection variable specifies which columns we want to retrieve for each image, in this case, the ID and display name columns.
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME
    )

    val sortOrder = "$imagesDateTime DESC"

    /**
     * MediaStore.Images.Media.DATE_TAKEN is a constant that represents the column name for the date and time when an image was taken or created.
     * This column is available in the MediaStore.Images.Media class and is used to sort and query images based on their capture time.
     *  queryUri variable contains a Uri that points to the internal storage of the device where the images are stored.
     * */
    val queryUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI

    val imagePaths = ArrayList<String>()

    fun getImages(context: Context): ArrayList<String>? {
        // contentResolver.query() is a method that allows you to perform a query on a ContentProvider, such as the MediaStore ContentProvider in this case.
        val cursor = context.contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)

                val contentUri = ContentUris.withAppendedId(queryUri, id)
                val path = contentUri.toString()
                imagePaths.add(path)
            }
        }

        return if (imagePaths.size != 0 || imagePaths != null)
            imagePaths
        else null
    }
}

/**
 * The `contentResolver.query()` method takes five parameters:
 * (1) The Uri that specifies the location of the data we want to query.
 * (2) The projection array that specifies which columns we want to retrieve. Passing null for this parameter retrieves all columns.
 * (3) A selection string, which allows us to filter the results based on certain criteria. Passing null for this parameter retrieves all rows.
 * (4) An array of selection arguments, which provide values for any placeholders in the selection string.
 * (5) Passing null for this parameter indicates that we have no placeholders.
 * (6) The sort order for the results, in this case, sortOrder specifies that we want to sort the results in descending order based on the capture time
 * of the images.
 * */
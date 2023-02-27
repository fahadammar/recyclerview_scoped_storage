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
    private val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME
    )

    private val sortOrder = "$imagesDateTime DESC"

    /**
     * MediaStore.Images.Media.DATE_TAKEN is a constant that represents the column name for the date and time when an image was taken or created.
     * This column is available in the MediaStore.Images.Media class and is used to sort and query images based on their capture time.
     *  queryUri variable contains a Uri that points to the internal storage of the device where the images are stored.
     * */
    private val queryUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI

    private val imagePaths = ArrayList<String>()

    fun getImages(context: Context): ArrayList<String>? {
        /**
         * contentResolver.query() is a method that allows you to perform a query on a ContentProvider, such as the MediaStore ContentProvider in this case.
         * The contentResolver.query() method returns a Cursor object that allows us to iterate over the results of the query.
         * We can then use the Cursor methods such as getColumnIndexOrThrow() and getString() to retrieve the values for each column for each row
         * of the results. In this way, we can get information about each image in the internal storage and use it for further processing.
         *
         * A Cursor is created by calling the query() method on a ContentResolver object, which returns a Cursor object containing the results of the query.
         * The Cursor object is then used to retrieve the data from the query result.
         */
        val cursor = context.contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            sortOrder
        )

        /**
         * In Android, a Cursor object is a data structure that represents the result of a database query.
         * It provides a way to access and iterate over the rows of the query result, as well as to retrieve the values of the columns in each row.
         * */
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

        // close the cursor after use
        cursor?.close()

        return if (imagePaths.size != 0)
            imagePaths
        else null
    }
}

/**
 * @ContentResolver
 * The `contentResolver.query()` method takes five parameters:
 * (1) The Uri that specifies the location of the data we want to query.
 * (2) The projection array that specifies which columns we want to retrieve. Passing null for this parameter retrieves all columns.
 * (3) A selection string, which allows us to filter the results based on certain criteria. Passing null for this parameter retrieves all rows.
 * (4) An array of selection arguments, which provide values for any placeholders in the selection string.
 * (5) Passing null for this parameter indicates that we have no placeholders.
 * (6) The sort order for the results, in this case, sortOrder specifies that we want to sort the results in descending order based on the capture time
 * of the images.
 * */

/**
 * @Cursor
 * The Cursor object has a pointer that points to the current row in the result set, and methods that allow you to move the pointer to the next row,
 * get the values of the columns for the current row, and perform other operations on the data.
 *
 * Here are some of the key methods provided by the Cursor class:

 * @moveToFirst(): Moves the cursor to the first row of the result set.
 * @moveToNext(): Moves the cursor to the next row of the result set.
 * @getColumnIndex(): Returns the index of the named column in the result set.
 * @getString(): Returns the value of the specified column as a string.
 * @getInt(): Returns the value of the specified column as an integer.
 * @getBlob(): Returns the value of the specified column as a byte array.
 *
 * Using a Cursor, you can iterate over the results of a query and retrieve the data that you need to process in your application.
 * Once you are finished with the Cursor, you should call its close() method to release any resources associated with it.
 * */
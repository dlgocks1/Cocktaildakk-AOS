package com.compose.cocktaildakk_compose.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.os.bundleOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.cocktaildakk_compose.data.data_source.CocktailPagingSource
import com.compose.cocktaildakk_compose.data.data_source.GalleryPagingSource
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.domain.repository.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ImageRepository {

    private val uriExternal: Uri by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    }

    private val projection = arrayOf(
        MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
        MediaStore.Images.ImageColumns.SIZE, // 크기
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
        MediaStore.Images.ImageColumns._ID
    )
    private val sortedOrder = MediaStore.Images.ImageColumns.DATE_TAKEN

    private val contentResolver by lazy {
        context.contentResolver
    }

    override fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String?
    ): MutableList<GalleryImage> {
        val galleryImageList = mutableListOf<GalleryImage>()
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        val limit = loadSize
        val offset = (page - 1) * loadSize
        val query = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            Log.i("SDK_VERSION", Build.VERSION.SDK_INT.toString())
            val bundle = bundleOf(
                ContentResolver.QUERY_ARG_OFFSET to offset,
                ContentResolver.QUERY_ARG_LIMIT to limit,
                ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
                ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
                ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to selectionArgs
            )
            contentResolver.query(uriExternal, projection, bundle, null)
        } else {
            Log.i("SDK_VERSION", Build.VERSION.SDK_INT.toString())
            contentResolver.query(
                uriExternal,
                projection,
                selection,
                selectionArgs,
                "$sortedOrder DESC LIMIT $limit OFFSET $offset"
            )
        }
        query?.use { cursor ->
            while (cursor.moveToNext()) {
                val id =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
                val filepath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                val size =
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE))
                val date =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN))
                val contentUri = ContentUris.withAppendedId(uriExternal, id)
                val image = GalleryImage(
                    id = id,
                    filepath = filepath,
                    uri = contentUri,
                    name = name,
                    date = date ?: "",
                    size = size
                )
                galleryImageList.add(
                    image
                )
            }
        }
        return galleryImageList
    }

}
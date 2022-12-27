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

    private val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val projection = arrayOf(
        MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
        MediaStore.Images.ImageColumns.SIZE, // 크기
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
        MediaStore.Images.ImageColumns._ID
    )
    private val sortedOrder = "${MediaStore.Images.ImageColumns.DATE_ADDED} DESC"

    private val contentResolver by lazy {
        context.contentResolver
    }
//    private val imageCollection: Uri by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            MediaStore.Images.Media.getContentUri(
//                MediaStore.VOLUME_EXTERNAL
//            )
//        } else {
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        }
//    }
//    private val imageProjections by lazy {
//        arrayOf(
//            MediaStore.Images.ImageColumns._ID,
//            MediaStore.Images.ImageColumns.DATA,
//            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
//            MediaStore.Images.ImageColumns.DATE_TAKEN,
//            MediaStore.Images.ImageColumns.DISPLAY_NAME,
//            MediaStore.Images.ImageColumns.ORIENTATION,
//            MediaStore.Images.ImageColumns.WIDTH,
//            MediaStore.Images.ImageColumns.HEIGHT,
//            MediaStore.Images.ImageColumns.SIZE,
//        )
//    }

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
        val sort = MediaStore.Images.ImageColumns.DATE_TAKEN
        val query = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            val bundle = Bundle().apply {
                // selection
                putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection)
                putStringArray(
                    ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS,
                    selectionArgs
                )
                // sort
                putString(
                    ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    sort
                )
                putInt(
                    ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
                // limit, offset
                putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
            }
            contentResolver.query(uriExternal, projection, bundle, null)
        } else {
            contentResolver.query(
                uriExternal,
                projection,
                selection,
                selectionArgs,
                "$sort ASC LIMIT $limit OFFSET $offset"
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
                galleryImageList.add(
                    GalleryImage(
                        id = id,
                        filepath = filepath,
                        uri = contentUri,
                        name = name,
                        date = date ?: "",
                        size = size
                    )
                )
            }
        }
        return galleryImageList
//        return query
//
//
//        val query = context.contentResolver?.query(
//            uriExternal,
//            projection,
//            selection,
//            selectionArgs,
//            sortedOrder
//        )
//
//        query?.use { cursor ->
//            cursor.move((page - 1) * loadSize)
//            while (cursor.moveToNext() && cursor.position < loadSize * page) {
//                val id =
//                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
//                val name =
//                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
//                val filepath =
//                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
//                val size =
//                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE))
//                val date =
//                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN))
//                val contentUri = ContentUris.withAppendedId(uriExternal, id)
//                galleryImageList.add(
//                    GalleryImage(
//                        id = id,
//                        filepath = filepath,
//                        uri = contentUri,
//                        name = name,
//                        date = date ?: "",
//                        size = size
//                    )
//                )
//            }
//        }
//        return galleryImageList

    }


}
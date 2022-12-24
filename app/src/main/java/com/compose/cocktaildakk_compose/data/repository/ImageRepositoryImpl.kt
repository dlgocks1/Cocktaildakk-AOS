package com.compose.cocktaildakk_compose.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.domain.repository.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ImageRepository {

    override fun getAllPhotos(
        loadSize: Int,
        currentLocation: String?
    ): MutableList<GalleryImage> {
        val galleryImageList = mutableListOf<GalleryImage>()
        // 외장 메모리에 있는 URI를 받도록 함
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        // 커서에 가져올 정보에 대해서 지정한다.
        val query: Cursor?
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
            MediaStore.Images.ImageColumns.SIZE, // 크기
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
            MediaStore.Images.ImageColumns._ID
        )
        val resolver = context.contentResolver

        var selection: String? = null
        var selectionArgs: Array<String>? = null

        query = resolver?.query(
            uriExternal,
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Images.ImageColumns.DATE_ADDED} DESC"
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            val filePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val filepath = cursor.getString(filePathColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val date = cursor.getString(dateColumn)
                val contentUri = ContentUris.withAppendedId(uriExternal, id)
                galleryImageList.add(
                    GalleryImage(
                        id,
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
    }

}
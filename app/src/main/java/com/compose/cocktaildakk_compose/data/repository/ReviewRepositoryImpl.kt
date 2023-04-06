package com.compose.cocktaildakk_compose.data.repository

import android.graphics.Bitmap
import com.compose.cocktaildakk_compose.REVIEW
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.ReviewRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : ReviewRepository {

    private val failListener = OnFailureListener { p0 -> p0.printStackTrace() }

    override suspend fun putDataToStorage(
        setLoadingState: (Int) -> Unit,
        bitmaps: List<Bitmap>,
        userinfo: UserInfo,
        onUploadComplete: (List<String>) -> Unit,
    ) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val downloadURL = mutableListOf<String>()

        byteArrayOutputStream.use {
            bitmaps.forEach { bitmap ->
                val storageRef = storage.getReference(REVIEW)
                    .child("${userinfo.userKey}/${bitmap.generationId}")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
                val uploadTask = storageRef.putBytes(byteArrayOutputStream.toByteArray())

                uploadTask.addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        downloadURL.add(it.toString())
                        setLoadingState((downloadURL.size * 100) / bitmaps.size)
                        if (downloadURL.size == bitmaps.size) {
                            onUploadComplete(downloadURL.toList())
                        }
                    }.addOnFailureListener(failListener)
                }
                byteArrayOutputStream.reset()
            }
        }
    }

    override fun writeReview(
        review: Review,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        firestore.collection(REVIEW)
            .add(review)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                it.printStackTrace()
                onFailed()
            }
    }
}

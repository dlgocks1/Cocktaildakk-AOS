package com.compose.cocktaildakk_compose.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.ReviewRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class ReviewRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ReviewRepository {

    private val failListener = OnFailureListener { p0 -> p0.printStackTrace() }

    override fun putDataToStorage(
        setLoadingState: (Int) -> Unit,
        images: List<Bitmap>,
        userinfo: UserInfo
    ): List<String> {
        val storage = FirebaseStorage.getInstance("gs://cocktaildakk-compose.appspot.com")
        val byteArrayOutputStream = ByteArrayOutputStream()
        val downloadURL = mutableListOf<String>()
        var isLoading = false
        while (true) {
            if (!isLoading) {
                isLoading = true
                byteArrayOutputStream.use {
                    images.forEachIndexed { _, bitmap ->
                        val storageRef = storage.getReference("Review")
                            .child("${userinfo.userKey}/${bitmap.generationId}")
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        val uploadTask = storageRef.putBytes(byteArrayOutputStream.toByteArray())
                        uploadTask.addOnSuccessListener {
                            storageRef.downloadUrl.addOnSuccessListener {
                                downloadURL.add(it.toString())
                            }.addOnFailureListener(failListener)
                        }
                        byteArrayOutputStream.reset()
                    }
                }
            }
            setLoadingState((downloadURL.size * 100) / images.size)
            if (downloadURL.size == images.size) {
                return downloadURL.toList()
            }
        }
    }


    override fun writeReview(
        review: Review,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {

        firestore.collection("review")
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
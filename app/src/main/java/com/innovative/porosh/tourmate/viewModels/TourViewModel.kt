package com.innovative.porosh.tourmate.viewModels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.innovative.porosh.tourmate.model.ExpenseModel
import com.innovative.porosh.tourmate.model.MomentModel
import com.innovative.porosh.tourmate.model.TourModel
import com.innovative.porosh.tourmate.repos.TourRepository
import java.io.ByteArrayOutputStream

class TourViewModel: ViewModel() {

    val repository = TourRepository()

    fun addTour(tourModel: TourModel) {
        repository.addTour(tourModel)
    }

    fun updateTourStatus(id: String, status: Boolean) {
        repository.updateTourStatus(id, status)
    }

    fun addExpense(expenseModel: ExpenseModel, tourId: String) {
        repository.addExpense(expenseModel, tourId)
    }

    fun getToursByUser(userId: String) = repository.getToursByUser(userId)

    fun getTourById(tourId: String) = repository.getTourById(tourId)

    fun getAllExpense(tourId: String) = repository.getExpenses(tourId)

    fun getAllMoments(tourId: String) = repository.getMoments(tourId)

    fun getTotalExpense(list: List<ExpenseModel>) : Int {
        var total = 0
        list.forEach{
            total += it.amount!!
        }
        return total
    }

    fun uploadPhoto(bitmap: Bitmap, tourId: String, tourName: String) {
        val imageName = "${tourId}_${System.currentTimeMillis()}.jpg"
        val photoRef = Firebase.storage.reference.child("$tourName/$imageName")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,75, baos)
        val imageData= baos.toByteArray()
        val uploadTask = photoRef.putBytes(imageData)
        uploadTask.addOnSuccessListener {
            Log.d("check","Photo uploaded success")
        }.addOnFailureListener{
            Log.d("check","Photo uploaded failed")
        }

        val urlTask = uploadTask.continueWithTask{task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task->
            if (task.isSuccessful){
                val downloadUri = task.result
                val momentModel = MomentModel(imageName = imageName, imageUrl = downloadUri.toString())
                repository.addMoment(momentModel,tourId)
            }
        }

    }
}
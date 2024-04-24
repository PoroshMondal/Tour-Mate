package com.innovative.porosh.tourmate.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.innovative.porosh.tourmate.model.TourModel

class TourRepository {

    val db = Firebase.firestore

    fun addTour(tourModel: TourModel) {
        val doRef = db.collection(collection_tour).document()
        tourModel.id = doRef.id
        doRef.set(tourModel)
            .addOnSuccessListener {
                Log.d("check","db snapshot success")
            }
            .addOnFailureListener {
                Log.d("check","db snapshot failed")
            }
    }

    fun updateTourStatus(id: String, status: Boolean) {

    }

    /*fun addExpense(expenseModel: ExpenseModel, tourId: String) {

    }

    fun addMoment(momentModel: MomentModel, tourId: String) {

    }*/

    fun getToursByUser(userId: String) : LiveData<List<TourModel>> {
        val tourListLiveData = MutableLiveData<List<TourModel>>()

        return tourListLiveData
    }

    fun getTourById(tourId: String) : LiveData<TourModel> {
        val tourLiveData = MutableLiveData<TourModel>()

        return tourLiveData
    }

    /*fun getExpenses(tourId: String) : LiveData<List<ExpenseModel>> {
        val tourListLiveData = MutableLiveData<List<ExpenseModel>>()

        return tourListLiveData
    }

    fun getMoments(tourId: String) : LiveData<List<MomentModel>> {
        val tourListLiveData = MutableLiveData<List<MomentModel>>()

        return tourListLiveData
    }*/

    companion object {
        const val TAG = "db_error"
        const val collection_tour = "My Tours"
        const val collection_expense = "My Expenses"
        const val collection_photos = "My Photos"
    }
}
package com.innovative.porosh.tourmate.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.innovative.porosh.tourmate.data.model.ExpenseModel
import com.innovative.porosh.tourmate.model.MomentModel
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

    fun addExpense(expenseModel: ExpenseModel, tourId: String) {
        Log.d("check", "Tour ID: $tourId")
        val docRef = db.collection(collection_tour).document(tourId)
            .collection(collection_expense)
            .document()
        Log.d("check", "Expense ID: ${docRef.id}")
        expenseModel.expenseId = docRef.id
        docRef.set(expenseModel)
            .addOnSuccessListener {
                Log.d("check","expense db snapshot success")
            }
            .addOnFailureListener {
                Log.d("check","expense db snapshot failed")
            }
    }

    fun addMoment(momentModel: MomentModel, tourId: String) {
        val docRef = db.collection(collection_tour).document(tourId)
            .collection(collection_photos)
            .document()
        momentModel.momentId = docRef.id
        docRef.set(momentModel).addOnSuccessListener {
            Log.d("check","add photo db snapshot success")
        }.addOnFailureListener {
            Log.d("check","add photo db snapshot success")
        }
    }

    fun getToursByUser(userId: String) : LiveData<List<TourModel>> {
        val tourListLiveData = MutableLiveData<List<TourModel>>()

        db.collection(collection_tour)
            .whereEqualTo("userId",userId)
            .addSnapshotListener { value, error ->
            if (error !=null){
                return@addSnapshotListener
            }

            val temp = ArrayList<TourModel>()
            for (doc in value!!){
                temp.add(doc.toObject(TourModel::class.java))
            }
            tourListLiveData.postValue(temp)
        }

        return tourListLiveData
    }

    fun getTourById(tourId: String) : LiveData<TourModel> {
        val tourLiveData = MutableLiveData<TourModel>()

        db.collection(collection_tour).document(tourId)
            .addSnapshotListener { value, error ->
                if (error !=null){
                    return@addSnapshotListener
                }
                tourLiveData.postValue(value!!.toObject(TourModel::class.java))
            }

        return tourLiveData
    }

    fun getExpenses(tourId: String) : LiveData<List<ExpenseModel>> {
        val expenseListLiveData = MutableLiveData<List<ExpenseModel>>()

        db.collection(collection_tour).document(tourId)
            .collection(collection_expense)
            .addSnapshotListener { value, error ->
                if (error!=null){
                    return@addSnapshotListener
                }

                val temp = ArrayList<ExpenseModel>()
                for (doc in value!!){
                    temp.add(doc.toObject(ExpenseModel::class.java))
                }

                expenseListLiveData.postValue(temp)
            }

        return expenseListLiveData
    }

    fun getMoments(tourId: String) : LiveData<List<MomentModel>> {
        val momentListLiveData = MutableLiveData<List<MomentModel>>()

        db.collection(collection_tour).document(tourId)
            .collection(collection_photos)
            .addSnapshotListener{value, error ->
                if (error!=null){
                    return@addSnapshotListener
                }

                val temp = ArrayList<MomentModel>()
                for (doc in value!!){
                    temp.add(doc.toObject(MomentModel::class.java))
                }

                momentListLiveData.postValue(temp)
            }

        return momentListLiveData
    }

    companion object {
        const val TAG = "db_error"
        const val collection_tour = "My Tours"
        const val collection_expense = "My Expenses"
        const val collection_photos = "My Photos"
    }
}
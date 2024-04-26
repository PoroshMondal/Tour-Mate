package com.innovative.porosh.tourmate.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.innovative.porosh.tourmate.data.model.ExpenseModel
import com.innovative.porosh.tourmate.model.TourModel
import com.innovative.porosh.tourmate.repos.TourRepository

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

    //fun getAllMoments(tourId: String) = repository.getMoments(tourId)

    fun getTotalExpense(list: List<ExpenseModel>) : Int {
        var total = 0
        list.forEach{
            total += it.amount!!
        }
        return total
    }

    fun uploadPhoto(bitmap: Bitmap, tourId: String, tourName: String) {

    }
}
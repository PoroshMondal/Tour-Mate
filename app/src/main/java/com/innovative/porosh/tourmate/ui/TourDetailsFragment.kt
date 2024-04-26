package com.innovative.porosh.tourmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.customDialogs.AddExpenseDialog
import com.innovative.porosh.tourmate.customDialogs.ShowExpenseListDialog
import com.innovative.porosh.tourmate.data.model.ExpenseModel
import com.innovative.porosh.tourmate.databinding.FragmentTourDetailsBinding
import com.innovative.porosh.tourmate.viewModels.TourViewModel

class TourDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTourDetailsBinding
    private val tourViewModel: TourViewModel by viewModels()
    private var tourId: String ?= null
    private var expenseList = listOf<ExpenseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourDetailsBinding.inflate(inflater,container,false)

        val args: TourDetailsFragmentArgs by navArgs()
        tourId = args.tourId

        tourId?.let {
            tourViewModel.getTourById(it).observe(viewLifecycleOwner){
                binding.tourModel = it
            }
        }

        binding.detailsAddExpenseBtn.setOnClickListener {
            tourId?.let {
                AddExpenseDialog{expenseModel ->
                    tourViewModel.addExpense(expenseModel, it)
                }.show(childFragmentManager,"add_expense")
            }
        }

        tourId?.let {
            tourViewModel.getAllExpense(it).observe(viewLifecycleOwner){
                expenseList = it
                val totalExpense = tourViewModel.getTotalExpense(it)
                binding.totalExpense = totalExpense
            }
        }

        binding.detailsViewExpenseBtn.setOnClickListener {
            ShowExpenseListDialog(expenseList).show(childFragmentManager,"show_expense_list")
        }

        return binding.root
    }

}
package com.innovative.porosh.tourmate.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.customDialogs.AddExpenseDialog
import com.innovative.porosh.tourmate.customDialogs.ShowExpenseListDialog
import com.innovative.porosh.tourmate.customDialogs.ShowMomentListDialog
import com.innovative.porosh.tourmate.data.model.ExpenseModel
import com.innovative.porosh.tourmate.databinding.FragmentTourDetailsBinding
import com.innovative.porosh.tourmate.model.MomentModel
import com.innovative.porosh.tourmate.viewModels.TourViewModel

class TourDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTourDetailsBinding
    private val tourViewModel: TourViewModel by viewModels()
    private var tourId: String ?= null
    private var tourName: String ?= null
    private var expenseList = listOf<ExpenseModel>()
    private var momentList = listOf<MomentModel>()

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val bitmap = result.data?.extras?.get("data") as Bitmap
            tourViewModel.uploadPhoto(bitmap = bitmap,tourId = tourId!!, tourName = tourName!!)
        }
    }

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
                tourName = it.title
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

        tourId?.let {
            tourViewModel.getAllMoments(it).observe(viewLifecycleOwner){
                momentList = it
                binding.totalMoments = momentList.size
            }
        }

        binding.detailsViewExpenseBtn.setOnClickListener {
            ShowExpenseListDialog(expenseList).show(childFragmentManager,"show_expense_list")
        }

        binding.galleryBtn.setOnClickListener {
            ShowMomentListDialog(momentList).show(childFragmentManager,"show_moment_list")
        }

        binding.captureImageBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                cameraLauncher.launch(intent)
            }catch (e: ActivityNotFoundException){
                Log.e("error","message:" + e.message)
            }
        }

        return binding.root
    }

}
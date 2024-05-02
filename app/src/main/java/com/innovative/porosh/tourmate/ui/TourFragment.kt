package com.innovative.porosh.tourmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.adapters.TourAdapter
import com.innovative.porosh.tourmate.databinding.FragmentTourBinding
import com.innovative.porosh.tourmate.utils.Constants
import com.innovative.porosh.tourmate.viewModels.LoginViewModel
import com.innovative.porosh.tourmate.viewModels.TourViewModel

class TourFragment : Fragment() {

    private lateinit var binding: FragmentTourBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val tourViewModel: TourViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourBinding.inflate(inflater,container,false)
        val adapter = TourAdapter { id, action, status ->
            when(action){
                Constants.DETAILS_SCREEN -> {
                    val navAction = TourFragmentDirections.tourDetailsAction()
                    navAction.tourId = id
                    findNavController().navigate(navAction)
                }
                Constants.TOUR_STATUS_UPDATE -> {
                    tourViewModel.updateTourStatus(id, status)
                }
            }
        }
        binding.tourRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.tourRecyclerView.adapter = adapter
        tourViewModel.getToursByUser(loginViewModel.user!!.uid)
            .observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        binding.newTourFab.setOnClickListener {
            findNavController().navigate(R.id.new_tour_action)
        }

        return binding.root
    }

}
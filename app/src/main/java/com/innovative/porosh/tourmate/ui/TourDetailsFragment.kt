package com.innovative.porosh.tourmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.databinding.FragmentTourDetailsBinding

class TourDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTourDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

}
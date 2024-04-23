package com.innovative.porosh.tourmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.viewModels.LoginViewModel

class LauncherFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel.authStatusLiveData.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.AuthenticationStatus.AUTHENTICATED) {
                findNavController().navigate(R.id.tour_list_action)
            } else {
                findNavController().navigate(R.id.login_action)
            }
        }

        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

}
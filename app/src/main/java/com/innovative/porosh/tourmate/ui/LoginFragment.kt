package com.innovative.porosh.tourmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.databinding.FragmentLoginBinding
import com.innovative.porosh.tourmate.viewModels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        loginViewModel.authStatusLiveData.observe(viewLifecycleOwner){
            if (it == LoginViewModel.AuthenticationStatus.AUTHENTICATED){
                findNavController().navigate(R.id.login_to_tour_action)
            } /*else if (it == LoginViewModel.AuthenticationStatus.UNAUTHENTICATED) {

            }*/
        }

        loginViewModel.errMsgLiveData.observe(viewLifecycleOwner){
            binding.errMsgTV.text = it
        }

        binding.loginBtn.setOnClickListener {
            loginViewModel.login(
                binding.emailET.text.toString(),
                binding.passET.text.toString()
            )
        }

        binding.registerBtn.setOnClickListener {
            loginViewModel.register(
                binding.emailET.text.toString(),
                binding.passET.text.toString()
            )
        }

        return binding.root
    }

}
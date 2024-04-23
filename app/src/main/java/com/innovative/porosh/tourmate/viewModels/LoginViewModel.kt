package com.innovative.porosh.tourmate.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    enum class AuthenticationStatus {
        AUTHENTICATED, UNAUTHENTICATED
    }

    private val auth = FirebaseAuth.getInstance()
    var user = auth.currentUser

    var errMsgLiveData: MutableLiveData<String> = MutableLiveData()
    val authStatusLiveData: MutableLiveData<AuthenticationStatus> = MutableLiveData()

    init {
        user?.let {
            authStatusLiveData.postValue(AuthenticationStatus.AUTHENTICATED)
        }?:authStatusLiveData.postValue(AuthenticationStatus.UNAUTHENTICATED)
    }

    fun login(email: String, password: String) {

    }

    fun register(email: String, password: String) {

    }

    fun isUserValid() = false

    fun sendVerificationMail() {

    }

    fun logout() {

    }
}
package com.example.dagatiproject_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dagatiproject_app.data.LoginRequest
import com.example.dagatiproject_app.databinding.LoginLayoutBinding
import com.example.dagatiproject_app.util.SharedPref
import com.example.dagatiproject_app.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private var binding: LoginLayoutBinding? = null
    private var _loginViewModel: LoginViewModel? = null
    private val loginViewModel get() = _loginViewModel!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginLayoutBinding.inflate(inflater, container, false)

        setObserver()

        setButtonEvent()

        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        autoLogin()
    }

    private fun setButtonEvent() {
        binding!!.loginButton.setOnClickListener {
            login(binding!!.emailEdit.text.toString(), binding!!.pwEdit.text.toString())
        }
    }

    private fun login(email: String, pw: String) {
        loginViewModel.requestLogin(LoginRequest(email, pw))
    }

    private fun setObserver() {
        loginViewModel.loginResponse.observe(this) {
            SharedPref.putString(SharedPref.tokenKey, it.token)
        }
    }

    private fun autoLogin() {
        val email = SharedPref.getString(SharedPref.emailKey)
        val pw = SharedPref.getString(SharedPref.pwKey)

        if (email == null || pw == null) {
            return
        }

        binding!!.apply {
            emailEdit.setText(email)
            pwEdit.setText(pw)
        }
        login(email, pw)
    }

}
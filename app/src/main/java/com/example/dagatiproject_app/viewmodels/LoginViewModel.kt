package com.example.dagatiproject_app.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dagatiproject_app.data.LoginRequest
import com.example.dagatiproject_app.data.LoginResponse
import com.example.dagatiproject_app.util.DagatiAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val api = DagatiAPI.create()
    private val scope = CoroutineScope(Dispatchers.IO)
    val loginResponse = MutableLiveData<LoginResponse>()

    private val ceh = CoroutineExceptionHandler { _, exception ->
        Log.e(">>>>", "Exception occur $exception")
    }


    fun requestLogin(body: LoginRequest) {
        scope.launch(ceh) {
            api.requestLogin(body).let {
                if (it.isSuccessful) {
                    loginResponse.postValue(it.body())
                }
            }
        }
    }

}
package com.wcs.ebreedings.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wcs.ebreedings.R
import com.wcs.ebreedings.api.CallbackInterface

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, estate: String, context: Context) {
        val result = loginRepository.login(username, password, estate, context, object : CallbackInterface<String> {
            override fun onDataResponse(data: String?) {
                _loginResult.postValue(LoginResult(success = R.string.login_succeed))
            }

            override fun onDataFailure(data: String?) {
                _loginResult.postValue(LoginResult(error = R.string.login_failed))
            }

        })
    }

    fun loginDataChanged(userid: String, password: String) {
        if (!isUserNameValid(userid)) {
            _loginForm.value = LoginFormState(useridError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotEmpty()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

}

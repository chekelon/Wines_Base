package com.cursosant.winesbase.loginModule.model

import com.cursosant.winesbase.FakeFirebaseAuth
import com.cursosant.winesbase.R
import com.cursosant.winesbase.commonModule.utils.Constants

class LoginRepository(private val auth: FakeFirebaseAuth) {

    suspend fun checkAuth(): LoginState{
        val result = auth.isValidAuth()
        return if (result) {
            LoginState.AuthValid
        } else {

            LoginState.Fail(Constants.EC_AUTH, R.string.login_auth_fail)
        }
    }


    suspend fun login(username:String, pin:String): LoginState{
        val result = auth.login(username,pin)
        return if (result) {
            LoginState.LoginSuccess
        } else {
            LoginState.Fail(Constants.EC_LOGIN, R.string.login_login_fail)
        }
    }
}
package com.cursosant.winesbase.accountModule.model

import com.cursosant.winesbase.commonModule.utils.Constants
import com.cursosant.winesbase.FakeFirebaseAuth
import com.cursosant.winesbase.R

class AccountRepository(private val auth: FakeFirebaseAuth) {

    suspend fun signOut(): AccountState{
        val result = auth.signOut()
        return if(result){
            AccountState.SignOutSuccess
        }else {
            AccountState.Fail(Constants.EC_LOGIN,R.string.account_sign_out_fail)
        }
    }

    suspend fun getCurrentUser(): AccountState{
        auth.getCurrentUser()?.let { user ->
            return AccountState.RequestUserSuccess(user)
        }
        return AccountState.Fail(Constants.EC_LOGIN,R.string.account_sign_out_fail)

    }

}
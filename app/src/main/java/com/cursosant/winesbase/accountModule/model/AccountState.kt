package com.cursosant.winesbase.accountModule.model

import com.cursosant.winesbase.FirebaseUser

sealed class AccountState {

    data object Init: AccountState()
    data object ShowProgress: AccountState()
    data object HideProgress: AccountState()
    data object SignOutSuccess: AccountState()
    data class RequestUserSuccess(val user: FirebaseUser): AccountState()
    data class Fail(val code:Int,val msgRes:Int): AccountState()
}


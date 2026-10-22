package com.cursosant.winesbase.accountModule.intent

sealed class AccountIntent {
    data object RequestUser: AccountIntent()
    data object SignOut: AccountIntent()
}
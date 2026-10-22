package com.cursosant.winesbase.homeModule.model

import com.cursosant.winesbase.commonModule.entities.Wine

sealed class HomeState {
    data object Init: HomeState()
    data object ShowProgress: HomeState()
    data object HideProgress: HomeState()
    data class RequestWinesSuccess(val list: List<Wine>): HomeState()
    data class AddWineSuccess(val msgRes: Int): HomeState()
    data class Fail(val code:Int,val msgRes:Int): HomeState()
}
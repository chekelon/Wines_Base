package com.cursosant.winesbase.updateModule.model

import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.favoriteModule.model.FavouriteState

sealed class UpdateState {
    data object Init: UpdateState()
    data object ShowProgress: UpdateState()
    data object HideProgress: UpdateState()
    data class Fail(val code:Int,val msgRes:Int): UpdateState()
    data class RequestWineSuccess(val wine: Wine): UpdateState()
    data object UpdateWineSuccess: UpdateState()
}
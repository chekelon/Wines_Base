package com.cursosant.winesbase.updateModule.intent

import com.cursosant.winesbase.commonModule.entities.Wine

sealed class UpdateIntent {
    data class RequestWine(val id:Double): UpdateIntent()
    data class UpdateWine(val wine: Wine): UpdateIntent()
}
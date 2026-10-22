package com.cursosant.winesbase.homeModule.intent

import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.favoriteModule.intent.FavouriteIntent

sealed class HomeIntent {

    data object RequestWines: HomeIntent()
    data class AddWine(val wine: Wine): HomeIntent()
}
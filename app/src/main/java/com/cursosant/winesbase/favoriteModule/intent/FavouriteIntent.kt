package com.cursosant.winesbase.favoriteModule.intent

import com.cursosant.winesbase.commonModule.entities.Wine

sealed class FavouriteIntent {

    data object RequestWines: FavouriteIntent()
    data class AddWine(val wine: Wine): FavouriteIntent()
    data class DeleteWine(val wine:Wine): FavouriteIntent()
}
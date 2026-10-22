package com.cursosant.winesbase.favoriteModule.model

import com.cursosant.winesbase.R
import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.commonModule.utils.Constants



class FavouriteRepository(private val db: RoomDatabase) {

     fun getAlllWines(): FavouriteState{
        val result = db.getAllWines()
         return FavouriteState.RequestWinesSuccess(result)

    }

    fun addWine(wine: Wine): FavouriteState {
        val result = db.addWine(wine)
        return if (result == -1L) {
            FavouriteState.Fail(Constants.EC_SAVE_WINE, R.string.room_save_fail)
        }else{
            FavouriteState.AddWineSuccess(R.string.room_save_success)
        }

    }

    fun deleteWine(wine:Wine): FavouriteState{
        val result = db.deleteWine(wine)
        return if (result == 0) {
            FavouriteState.Fail(Constants.EC_SAVE_WINE, R.string.room_save_fail)
        }else{
            FavouriteState.AddWineSuccess(R.string.room_save_success)
        }
    }
}
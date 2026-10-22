package com.cursosant.winesbase.updateModule.model

import com.cursosant.winesbase.R
import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.commonModule.utils.Constants


class UpdateRepository(private val db: RoomDatabase) {

    fun requestWine(id:Double): UpdateState{

        return try {
            val result = db.getWineBy(id)
            UpdateState.RequestWineSuccess(result)
        }catch (e: Exception){
            UpdateState.Fail(Constants.EC_GET_WINE, R.string.room_save_fail)

        }
    }

    fun updateWine(wine: Wine): UpdateState{
        val result = db.updateWine(wine)
        return if(result != 0){
            UpdateState.UpdateWineSuccess

        }else{
            UpdateState.Fail(Constants.EC_UPDATE_WINE, R.string.room_save_fail)
        }

    }

}
package com.cursosant.winesbase.homeModule.model


import com.cursosant.winesbase.R
import com.cursosant.winesbase.commonModule.dataAccess.retrofit.WineService
import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.commonModule.utils.Constants
import kotlin.random.Random


class HomeRepository(private val db: RoomDatabase, private val service: WineService) {

    suspend fun getAlllWines(): HomeState{
        try {
            val serverOk = Random.nextBoolean()
            val wines = if(serverOk) service.getRedWines() else listOf()
            return if(wines.isNotEmpty()) HomeState.RequestWinesSuccess(wines)
            else HomeState.Fail(Constants.EC_REQUEST_NO_WINES, R.string.home_no_wines)
        } catch (e:Exception){
            return HomeState.Fail(Constants.EC_REQUEST, R.string.common_general_fail)
        }
    }



    fun addWine(wine: Wine): HomeState {
        val result = db.addWine(wine)
        return if (result == -1L) {
            HomeState.Fail(Constants.EC_SAVE_WINE, R.string.room_save_fail)
        }else{
            HomeState.AddWineSuccess(R.string.room_save_success)
        }

    }
}
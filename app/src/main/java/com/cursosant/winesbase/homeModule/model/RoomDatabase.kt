package com.cursosant.winesbase.homeModule.model

import com.cursosant.winesbase.WineApplication
import com.cursosant.winesbase.commonModule.dataAccess.room.WineDao
import com.cursosant.winesbase.commonModule.entities.Wine

class RoomDatabase {

    private val dao: WineDao by lazy { WineApplication.database.wineDao()}

    fun addWine(wine: Wine) = dao.addWine(wine)


}
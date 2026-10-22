package com.cursosant.winesbase.updateModule.model

import com.cursosant.winesbase.WineApplication
import com.cursosant.winesbase.commonModule.dataAccess.room.WineDao
import com.cursosant.winesbase.commonModule.entities.Wine

class RoomDatabase {

    private val dao: WineDao by lazy { WineApplication.database.wineDao()}

    fun getWineBy(id: Double) = dao.getWineById(id)

    fun updateWine(wine: Wine) = dao.updateWine(wine)
}
package com.cursosant.winesbase.favoriteModule.model

import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.WineApplication
import com.cursosant.winesbase.commonModule.dataAccess.room.WineDao

class RoomDatabase {

    private val dao: WineDao by lazy { WineApplication.database.wineDao()}

    fun getAllWines() = dao.getAllWines()

    fun addWine(wine: Wine) = dao.addWine(wine)

    fun deleteWine(wine: Wine) = dao.deleteWine(wine)

}
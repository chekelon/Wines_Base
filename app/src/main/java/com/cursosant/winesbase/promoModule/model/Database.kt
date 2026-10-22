package com.cursosant.winesbase.promoModule.model

import com.cursosant.winesbase.commonModule.dataAccess.local.getAllPromos

class Database {
    fun getPromos() = getAllPromos()
}
package com.cursosant.winesbase.promoModule.model

import com.cursosant.winesbase.R
import com.cursosant.winesbase.commonModule.utils.Constants

class PromoRepository(private val db:Database) {

    fun getPromos():PromoState{
        val result = db.getPromos()
        return if(result.isNotEmpty()){
            PromoState.RequestPromoSuccess(result)
        }else{
            PromoState.Fail(Constants.EC_REQUEST, R.string.promo_request_fail)
        }
    }
}
package com.cursosant.winesbase.promoModule.model

import com.cursosant.winesbase.commonModule.entities.Promo

sealed class PromoState {

    data object Init: PromoState()
    data class Fail(val code:Int,val msgRes:Int): PromoState()
    data class RequestPromoSuccess(val list:List<Promo>): PromoState()
}
package com.cursosant.winesbase.promoModule.intent




sealed class PromoIntent {
    data object RequestPromos: PromoIntent()
}
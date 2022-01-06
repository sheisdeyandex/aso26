package com.vavada.aso26.models.offers.offerslist

import android.graphics.drawable.Drawable
import android.net.Uri
import com.vavada.aso26.models.RecyclerViewItem


data class OffersListModel(
    val Title: String?,
    val Offers: ArrayList<Offers>,

    ): RecyclerViewItem()

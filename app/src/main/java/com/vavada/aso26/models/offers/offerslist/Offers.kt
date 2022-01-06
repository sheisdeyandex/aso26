package com.vavada.aso26.models.offers.offerslist

import com.vavada.aso26.models.RecyclerViewItem

data class Offers(  var Id: String = "none",
                    var Background: String,
                    var Foreground: String,
                    var Emoji: String?,
                    var EmojiCaption: String?,
                    var ButtonText: String): RecyclerViewItem() {
}

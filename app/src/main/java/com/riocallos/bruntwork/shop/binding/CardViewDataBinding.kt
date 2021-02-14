package com.riocallos.bruntwork.shop.binding

import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

/**
 * Custom data binding for ImageView.
 *
 */
class CardViewDataBinding {

    @BindingAdapter("bgColor")
    fun bind(cardView: MaterialCardView, bgColor: String?) {

        cardView.setCardBackgroundColor(Color.parseColor(bgColor))

    }

}

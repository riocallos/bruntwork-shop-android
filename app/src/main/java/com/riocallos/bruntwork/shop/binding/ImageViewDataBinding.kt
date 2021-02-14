package com.riocallos.bruntwork.shop.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * Custom data binding for ImageView.
 *
 */
class ImageViewDataBinding {

    /**
     * Bind image to RecyclerView.
     *
     * @property imageView [ImageView] is the view.
     * @property productId [String] for the image resource.
     */
    @BindingAdapter("productId")
    fun bind(imageView: ImageView, productId: String?) {

        imageView.setImageResource(imageView.context.resources.getIdentifier(productId, "drawable", imageView.context.packageName))

    }

}

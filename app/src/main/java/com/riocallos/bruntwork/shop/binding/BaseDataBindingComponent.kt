package com.riocallos.bruntwork.shop.binding

/**
 * Base class that extends DataBindingComponent to handle
 * data binding initialization.
 *
 */
class BaseDataBindingComponent : androidx.databinding.DataBindingComponent {

    override fun getCardViewDataBinding(): CardViewDataBinding {
        return CardViewDataBinding()
    }

    override fun getImageViewDataBinding(): ImageViewDataBinding {
        return ImageViewDataBinding()
    }

}

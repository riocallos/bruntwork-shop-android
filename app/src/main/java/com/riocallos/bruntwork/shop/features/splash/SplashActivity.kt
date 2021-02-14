package com.riocallos.bruntwork.shop.features.splash

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseActivity
import com.riocallos.bruntwork.shop.databinding.ActivitySplashBinding
import com.riocallos.bruntwork.shop.features.products.ProductsActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProducts()
    }

    private fun showProducts() {
        binding.cart.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Handler().postDelayed(Runnable {
                    startActivity(Intent(this@SplashActivity, ProductsActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity).toBundle())
                }, 900)
            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })

        binding.cart.playAnimation()
    }

}
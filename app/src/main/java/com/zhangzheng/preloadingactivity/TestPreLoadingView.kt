package com.zhangzheng.preloadingactivity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.zhangzheng.preloading.library.AbsPreLoadingView
import kotlinx.android.synthetic.main.activity_main.view.*

class TestPreLoadingView(context: Context) : AbsPreLoadingView(context) {

    override fun resId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            textView.text = "onCreate"
        }, 2000)
    }

    override fun onResume() {
        super.onResume()
    }

}
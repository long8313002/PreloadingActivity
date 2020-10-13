package com.zhangzheng.preloading.library

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlin.reflect.KClass

abstract class AbsPreLoadingActivity : Activity() {

    abstract fun  preLoadingViewClass() : KClass<out AbsPreLoadingView>

    private lateinit var preLoadingView: AbsPreLoadingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preLoadingView =preLoadingViewClass().getOrCreate(intent, this)
        setContentView(preLoadingView)
        preLoadingView.callCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        preLoadingView.callStart()
    }

    override fun onResume() {
        super.onResume()
        preLoadingView.callResume()
    }

    override fun onPause() {
        super.onPause()
        preLoadingView.callPause()
    }

    override fun onStop() {
        super.onStop()
        preLoadingView.callStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        preLoadingView.callDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        preLoadingView.onActivityResult(requestCode, resultCode, data)
    }

}
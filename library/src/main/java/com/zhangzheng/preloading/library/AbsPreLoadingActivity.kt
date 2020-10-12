package com.zhangzheng.preloading.library

import android.app.Activity
import android.content.Intent
import android.os.Bundle

abstract class AbsPreLoadingActivity : Activity() {

    abstract fun createPreLoadingView(): AbsPreLoadingView

    private lateinit var preLoadingView: AbsPreLoadingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preLoadingView = createPreLoadingView()
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
        preLoadingView.callDestory()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        preLoadingView.onActivityResult(requestCode, resultCode, data)
    }

}
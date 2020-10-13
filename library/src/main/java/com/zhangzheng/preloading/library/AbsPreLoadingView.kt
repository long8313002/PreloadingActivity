package com.zhangzheng.preloading.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlin.reflect.KClass


fun <T : AbsPreLoadingView> KClass<T>.getOrCreate(
    intent: Intent,
    context: Context
): AbsPreLoadingView {

    var cacheView = AbsPreLoadingView.getCache(intent)

    if (cacheView != null) {
        printE(message = "获取预加载视图")
        if(cacheView.parent is ViewGroup){
            (cacheView.parent as ViewGroup).removeView(cacheView)
        }
    } else {
        cacheView = java.getConstructor(Context::class.java).newInstance(context)
        cacheView?.attach(intent)
    }
    return cacheView!!
}


abstract class AbsPreLoadingView(context: Context) : FrameLayout(context) {

    companion object {
        private var cacheViewMap = mutableMapOf<Intent, AbsPreLoadingView>()

        fun getCache(intent: Intent) =
            cacheViewMap.entries.find {
                val keyIntent = it.key
                val keySet = keyIntent.extras?.keySet()
                val findKeySet = intent.extras?.keySet()

                if (keyIntent.component?.className != intent.component?.className) {
                    return@find false
                }

                if (keySet?.size ?: 0 != findKeySet?.size ?: 0) {
                    return@find false
                }
                if (keySet.isNullOrEmpty() && findKeySet.isNullOrEmpty()) {
                    return@find true
                }

                keySet!!.forEach {
                    val value = keyIntent.extras!![it]
                    if (intent.extras!![it] != value) {
                        return@find false
                    }
                }

                return@find true
            }?.value


        fun putCache(intent: Intent, view: AbsPreLoadingView) {
            cacheViewMap[intent] = view
        }

        fun remove(intent: Intent) {
            cacheViewMap.remove(intent)
        }
    }

    abstract fun resId(): Int

    var isPreLoaded = false

    private var autoDestroyPreLoading = true


    protected var intent: Intent? = null

    fun attach(intent: Intent, autoDestroyPreLoading: Boolean = true) {
        this.intent = intent
        this.autoDestroyPreLoading = autoDestroyPreLoading
    }


    fun callCreate(savedInstanceState: Bundle?) {
        if (!isPreLoaded) {
            LayoutInflater.from(context).inflate(resId(), this)
            onCreate(savedInstanceState)
        }
    }

    protected open fun onCreate(savedInstanceState: Bundle?) {

    }

    fun callStart() {
        if (!isPreLoaded) {
            onStart()
        }
    }

    protected open fun onStart() {

    }


    fun callResume() {
        onResume()
    }

    protected open fun onResume() {

    }

    fun callPause() {
        onPause()
    }

    protected open fun onPause() {

    }

    fun callStop() {
        onStop()
    }

    protected open fun onStop() {

    }

    fun callDestroy() {
        onDestroy()
        if (intent != null && autoDestroyPreLoading) {
            remove(intent!!)
        }
    }

    protected open fun onDestroy() {

    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }
}
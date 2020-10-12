package com.zhangzheng.preloading.library

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread

object PreLoading {

    private  val handler: Handler

    init {
        val ht = HandlerThread("ui_async")
        ht.start()
        handler = Handler(ht.looper)
    }

    fun <T : AbsPreLoadingView> preLoading(context: Context, intent: Intent, clazz: Class<T>) {

        val cacheView = AbsPreLoadingView.getCache(intent)

        if (cacheView != null) {
            printE(message = "重复预加载")
            return
        }

        handler.post {
            val instance = clazz.getConstructor(Context::class.java).newInstance(context)
            instance.attach(intent)
            instance.callCreate(null)
            instance.callStart()
            instance.callResume()
            instance.callPause()
            instance.isPreLoaded = true
            AbsPreLoadingView.putCache(intent, instance)
            printI(message = "已预加载完成")
        }

    }

}
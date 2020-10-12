package com.zhangzheng.preloading.library

import android.util.Log

var openLog = true

const val TAG = "PreLoadingActivity"

internal fun printI(key:String = TAG,message:String){
    if(openLog){
        Log.i(key,message)
    }
}

internal fun printW(key:String = TAG,message:String){
    if(openLog){
        Log.w(key,message)
    }
}

internal fun printE(key:String = TAG,message:String){
    if(openLog){
        Log.e(key,message)
    }

}
package com.zhangzheng.preloadingactivity

import com.zhangzheng.preloading.library.AbsPreLoadingActivity
import com.zhangzheng.preloading.library.getOrCreate

class TestActivity : AbsPreLoadingActivity() {
    override fun createPreLoadingView() = TestPreLoadingView::class.getOrCreate(intent, this)
}
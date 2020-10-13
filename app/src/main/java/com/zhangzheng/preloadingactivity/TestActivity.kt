package com.zhangzheng.preloadingactivity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import com.zhangzheng.preloading.library.AbsPreLoadingActivity
import com.zhangzheng.preloading.library.AbsPreLoadingView
import com.zhangzheng.preloading.library.getOrCreate
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_test_list.view.*
import kotlin.reflect.KClass

class TestActivity : AbsPreLoadingActivity() {
    override fun preLoadingViewClass() = TestPreLoadingView::class

}

class TestPreLoadingView(context: Context) : AbsPreLoadingView(context) {

    override fun resId() = R.layout.activity_test_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestTestData(intent?.getIntExtra("id",0)?:0)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun requestTestData(id:Int){

        Thread {
             Thread.sleep(1000)
            val listData = ArrayList<String>()
            listData.add("$id === > 1")
            listData.add("$id === > 2")
            listData.add("$id === > 3")
            listData.add("$id === > 4")
            listData.add("$id === > 5")
            listData.add("$id === > 6")
            listData.add("$id === > 7")
            listData.add("$id === > 8")
            listData.add("$id === > 9")
            listData.add("$id === > 10")
            listData.add("$id === > 11")
            listData.add("$id === > 12")

            Handler(Looper.getMainLooper()).post {
                listview.adapter = ArrayAdapter(context,android.R.layout.simple_list_item_1,listData)
            }
        }.start()
    }

}
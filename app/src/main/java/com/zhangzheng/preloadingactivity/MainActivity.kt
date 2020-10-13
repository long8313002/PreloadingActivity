package com.zhangzheng.preloadingactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.LayoutInflater
import com.zhangzheng.preloading.library.PreLoading
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this,TestActivity::class.java)
        intent.putExtra("id",1111)

        PreLoading.preLoading(this,intent,TestPreLoadingView::class.java,false)

        textView.setOnClickListener {
            startActivity(intent)
        }
    }
}

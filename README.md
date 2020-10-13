
排版地址：https://blog.csdn.net/long8313002/article/details/109046692



Android开发Activity页面预加载
概述
某些业务场景下，为了提高用户体验，我们可能需要在前一个页面就将下一个页面的数据准备好，减少用户后续操作的时间。因为Activity在设计上采用了低耦合，高度的隔离使得传统的预加载Activity方案不够优雅，现提供一种更加优雅的预加载方案，可以预先加载好布局和页面数据。

github：github.com/long8313002… 
使用
说明：因为库使用的是kotlin开发，需要在项目配置kotlin开发环境

库引用
    implementation 'com.zhangzheng.preloading.activity:library:1.0.0'
复制代码
使用示例
val intent = Intent(this,TestActivity::class.java)
intent.putExtra("id",1111)

PreLoading.preLoading(this,intent,TestPreLoadingView::class.java)

textView.setOnClickListener {
    startActivity(intent)
}
复制代码
       默认情况下，当打开预加载页面后，页面销毁后，预加载的实例也会一起被销毁。当下次再进行就需要重新加载布局和数据，如果需要保留预加载的数据，多次重复使用，可以设置参数 autoDestroy = false ，如下：

 PreLoading.preLoading(this,intent,TestPreLoadingView::class.java,false)
复制代码
预加载页面实现
考虑到不同的项目使用的基类Activity会有所不同，为了通用性，未直接定制Activity，而是提供 AbsPreLoadingView 来通过组合的方式来实现。为了使用方便，这边提供了一个继承于Activity的基类，实际使用中可以参考这个类来实现自己的基类，如下：

示范基类
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
复制代码
使用示范
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

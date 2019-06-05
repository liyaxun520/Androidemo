package com.example.download_demo

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import com.example.download_demo.entity.api.UploadApi
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.upload.ProgressRequestBody
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import com.example.download_demo.entity.resulte.RetrofitEntity
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import com.example.download_demo.entity.resulte.UploadResulte
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager
import android.support.v4.app.ActivityCompat
import rx.functions.Action1
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.upload.UploadProgressListener
import com.bumptech.glide.Glide
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity
import com.example.download_demo.entity.resulte.SubjectResulte
import com.example.download_demo.entity.api.SubjectPostApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


class MainActivity : RxAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        verifyStoragePermissions(this)
        initView()
//        upload()
    }

//    private fun upload() {
//        /*上传接口内部接口有token验证，所以需要换成自己的接口测试，检查file文件是否手机存在*/
//        var uplaodApi =  UploadApi()
//        var file =  File("/storage/emulated/0/Download/11.jpg");
//        var requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        var part = MultipartBody.Part.createFormData("file_name", file.getName(),  ProgressRequestBody)
//        uplaodApi.setPart(part);
//    }

    private fun initView() {
        btn_simple.setOnClickListener(this)
        btn_rx.setOnClickListener(this)
        btn_rx_mu_down.setOnClickListener(this)
        btn_rx_uploade.setOnClickListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_simple->{
                onButton9Click()
            }
            R.id.btn_rx->{
                simpleDo()
            }
            R.id.btn_rx_mu_down->{
                val intent = Intent(this, DownLaodActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_rx_uploade->{
                uploadeDo()
            }
        }
    }

    //    完美封装简化版
    private fun simpleDo() {
        val postEntity = SubjectPostApi(simpleOnNextListener,this@MainActivity)
        postEntity.isAll = true
        val manager = HttpManager.getInstance()
        manager.doHttpDeal(postEntity)
    }

    //   回调一一对应
    var simpleOnNextListener: HttpOnNextListener<*> = object : HttpOnNextListener<List<SubjectResulte>>() {
        override fun onNext(subjects: List<SubjectResulte>) {
            tv_msg.setText("网络返回：\n$subjects")
        }

        override fun onCacheNext(cache: String) {
            /*缓存回调*/
            val gson = Gson()
            val type : Type = object : TypeToken<BaseResultEntity<List<SubjectResulte>>>() {
            }.getType()

            val resultEntity :BaseResultEntity<Any> = gson.fromJson(cache, type)

            tv_msg.setText("缓存返回：\n" + resultEntity.data.toString())
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        override fun onError(e: Throwable) {
            super.onError(e)
            tv_msg.setText("失败：\n$e")
        }

        /*用户主动调用，默认是不需要覆写该方法*/
        override fun onCancel() {
            super.onCancel()
            tv_msg.setText("取消请求")
        }
    }


    /*********************************************文件上传 */

    private fun uploadeDo() {
        val file = File("/storage/emulated/0/Download/11.jpg")
        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val part = MultipartBody.Part.createFormData("file_name", file.name, ProgressRequestBody(requestBody,
                UploadProgressListener { currentBytesCount, totalBytesCount ->
                    /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
                    Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(Action1<Long> {
                        tv_msg.setText("提示:上传中")
                        number_progress_bar.setMax(totalBytesCount.toInt())
                        number_progress_bar.setProgress(currentBytesCount.toInt())
                    })
                }))
        val uplaodApi = UploadApi(httpOnNextListener, this)
        uplaodApi.part = part
        val manager = HttpManager.getInstance()
        manager.doHttpDeal(uplaodApi)
    }


    /**
     * 上传回调
     */
    var httpOnNextListener: HttpOnNextListener<*> = object : HttpOnNextListener<UploadResulte>() {
        override fun onNext(o: UploadResulte) {
            tv_msg.setText("成功")
            Glide.with(this@MainActivity).load(o.headImgUrl).skipMemoryCache(true).into(img)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            tv_msg.setText("失败：$e")
        }

    }


    /**********************************************************正常不封装使用**********************************/

    /**
     * Retrofit加入rxjava实现http请求
     */
    private fun onButton9Click() {
        val BASE_URL = "http://www.izaodao.com/Api/"
        //手动创建一个OkHttpClient并设置超时时间
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        //        加载框
        val pd = ProgressDialog(this)

        val apiService = retrofit.create(HttpPostService::class.java)
        val observable = apiService.getAllVedioBy(true)
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Subscriber<RetrofitEntity>() {
                            override fun onCompleted() {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss()
                                }
                            }

                            override fun onError(e: Throwable) {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss()
                                }
                            }

                            override fun onNext(retrofitEntity: RetrofitEntity) {
                                tv_msg.setText("无封装：\n" + retrofitEntity.data.toString())
                            }

                            override fun onStart() {
                                super.onStart()
                                pd.show()
                            }
                        }

                )
    }


    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")


    /**
     * 申请权限
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {

        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}

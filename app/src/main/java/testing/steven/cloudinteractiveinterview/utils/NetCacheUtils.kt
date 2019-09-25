package testing.steven.cloudinteractiveinterview.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Build
import android.view.View
import android.widget.ImageView
import testing.steven.cloudinteractiveinterview.api.ICallbackNotify
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetCacheUtils(localCacheUtils: LocalCacheUtils, memoryCacheUtils: MemoryCacheUtils) {
    private var mLocalCacheUtils: LocalCacheUtils ?= null
    private var mMemoryCacheUtils: MemoryCacheUtils ?= null


    init {
        mLocalCacheUtils = localCacheUtils
        mMemoryCacheUtils = memoryCacheUtils
    }



    /**
     * 从网络下载图片
     * @param ivPic 显示图片的imageview
     * @param url   下载图片的网络地址
     */
    fun getBitmapFromNet(iCallbackNotify: ICallbackNotify<*>, url: String) {
        BitmapTask().execute(iCallbackNotify, url)//启动AsyncTask

    }



    fun getBitmapFromNet(url: String): Bitmap? {
        //启动AsyncTask
        return null
    }

    /**
     * AsyncTask就是对handler和线程池的封装
     * 第一个泛型:参数类型
     * 第二个泛型:更新进度的泛型
     * 第三个泛型:onPostExecute的返回结果
     */
    internal inner class BitmapTask : AsyncTask<Any, Void, Bitmap>() {

        private var iCallbackNotify: ICallbackNotify<Any>? = null
        private var url: String? = null

        /**
         * 后台耗时操作,存在于子线程中
         * @param params
         * @return
         */
        protected  override fun doInBackground(params: Array<Any>): Bitmap? {
            iCallbackNotify = params[0] as ICallbackNotify<Any>
            url = params[1] as String

            return downLoadBitmap(url)
        }


        /**
         * 耗时方法结束后执行该方法,主线程中
         * @param result
         */
        protected override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                iCallbackNotify?.dataFetched(result)
                println("从网络缓存图片啦.....")

                //从网络获取图片后,保存至本地缓存
                mLocalCacheUtils?.setBitmapToLocal(url!!, result)
                //保存至内存中
                mMemoryCacheUtils?.setBitmapToMemory(url!!, result)

            }
        }
    }


    /**
     * 网络下载图片
     * @param url
     * @return
     */
    fun downLoadBitmap(url: String?): Bitmap? {
        var conn: HttpURLConnection? = null
        try {
            conn = URL(url).openConnection() as HttpURLConnection
            conn!!.setConnectTimeout(5000)
            conn!!.setReadTimeout(5000)
            conn!!.setRequestMethod("GET")

            val responseCode = conn!!.getResponseCode()
            if (responseCode == 200) {
                //图片压缩
                val options = BitmapFactory.Options()
                options.inSampleSize = 2//宽高压缩为原来的1/2
                options.inPreferredConfig = Bitmap.Config.ARGB_4444

                //Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null,options);
                return BitmapFactory.decodeStream(conn!!.getInputStream())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
        } finally {
            if (conn != null) {
                conn!!.disconnect()
            }
        }

        return null
    }
}
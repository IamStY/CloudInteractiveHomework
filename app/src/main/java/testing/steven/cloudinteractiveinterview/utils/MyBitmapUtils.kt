package testing.steven.cloudinteractiveinterview.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import testing.steven.cloudinteractiveinterview.api.ICallbackNotify

class MyBitmapUtils {
    private var mNetCacheUtils: NetCacheUtils ?= null
    private var mLocalCacheUtils: LocalCacheUtils ?= null
    private var mMemoryCacheUtils: MemoryCacheUtils ?= null

  init {
        mMemoryCacheUtils = MemoryCacheUtils()
        mLocalCacheUtils = LocalCacheUtils()
        mNetCacheUtils = NetCacheUtils(mLocalCacheUtils!!, mMemoryCacheUtils!!)
    }



    fun retrieveImage(url: String, iCallbackNotify: ICallbackNotify<*>)   {
        var bitmap: Bitmap?  = mMemoryCacheUtils?.getBitmapFromMemory(url)
        if (bitmap != null) {
            iCallbackNotify.dataFetched(bitmap)
            return
        }


        bitmap = mLocalCacheUtils?.getBitmapFromLocal(url)
        if (bitmap != null) {

            mMemoryCacheUtils?.setBitmapToMemory(url, bitmap)
            iCallbackNotify.dataFetched(bitmap)
            return
        }
        mNetCacheUtils?.getBitmapFromNet( iCallbackNotify, url)
    }



}
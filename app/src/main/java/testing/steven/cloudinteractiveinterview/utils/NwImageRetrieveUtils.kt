package testing.steven.cloudinteractiveinterview.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import testing.steven.cloudinteractiveinterview.interfaces.IBitmapNotify
import testing.steven.cloudinteractiveinterview.interfaces.ICallbackNotify
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/*******
 * reference :https://blog.csdn.net/u012560369/article/details/80621483
 */
class NwImageRetrieveUtils(imageFileUtils: ImageFileUtils, memCacheUtils: MemCacheUtils) {
    private var mImageFileUtils: ImageFileUtils? = null
    private var mMemCacheUtils: MemCacheUtils? = null


    init {
        mImageFileUtils = imageFileUtils
        mMemCacheUtils = memCacheUtils
    }

    fun getBitmapFromNet(iBitmapNotify: IBitmapNotify, url: String) {
        try {
            var bm: Bitmap? = null
            val `in` = URL(url).openStream()
            bm = BitmapFactory.decodeStream(`in`)
            if (bm != null) {
                // mem + local 都沒, 直接assign
                iBitmapNotify.dataFetched(bm)
                mMemCacheUtils?.setBitmapToMemory(url, bm)
                mImageFileUtils?.setBitmapToLocal(url, bm)
            } else {
                iBitmapNotify.failure()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
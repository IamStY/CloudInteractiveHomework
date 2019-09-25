package testing.steven.cloudinteractiveinterview.utils

import android.graphics.Bitmap
import android.util.LruCache
/*******
 * reference :https://blog.csdn.net/u012560369/article/details/80621483
 */
class MemCacheUtils {
    private var mMemoryCache: LruCache<String, Bitmap> ?= null
    init{
        val maxMemory = Runtime.getRuntime().maxMemory() / 8
        mMemoryCache = object : LruCache<String, Bitmap>(maxMemory.toInt()) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount
            }
        }

    }


    fun getBitmapFromMemory(url: String?): Bitmap? {
        return if (url == null || url.isEmpty()) {
            null
        } else mMemoryCache?.get(url)

    }


    fun setBitmapToMemory(url: String, bitmap: Bitmap) {
        mMemoryCache?.put(url, bitmap)
    }


}

package testing.steven.cloudinteractiveinterview.utils

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCacheUtils {
    private var mMemoryCache: LruCache<String, Bitmap> ?= null
    init{
        val maxMemory = Runtime.getRuntime().maxMemory() / 8//得到手机最大允许内存的1/8,即超过指定内存,则开始回收
        //需要传入允许的内存最大值,虚拟机默认内存16M,真机不一定相同
        mMemoryCache = object : LruCache<String, Bitmap>(maxMemory.toInt()) {
            //用于计算每个条目的大小
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount
            }
        }

    }


    /**
     * 从内存中读图片
     * @param url
     */
    fun getBitmapFromMemory(url: String?): Bitmap? {
        //Bitmap bitmap = mMemoryCache.get(url);//1.强引用方法
        /*2.弱引用方法
          SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
          if (bitmapSoftReference != null) {
              Bitmap bitmap = bitmapSoftReference.get();
              return bitmap;
          }
          */
        return if (url == null || "" == url) {
            null
        } else mMemoryCache?.get(url)

    }

    /**
     * 往内存中写图片
     * @param url
     * @param bitmap
     */
    fun setBitmapToMemory(url: String, bitmap: Bitmap) {
        //mMemoryCache.put(url, bitmap);//1.强引用方法
        /*2.弱引用方法
          mMemoryCache.put(url, new SoftReference<>(bitmap));
          */
        mMemoryCache?.put(url, bitmap)
    }


}

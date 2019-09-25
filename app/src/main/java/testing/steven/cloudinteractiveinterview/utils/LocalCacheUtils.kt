package testing.steven.cloudinteractiveinterview.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class LocalCacheUtils {
    private val CACHE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/my/images"
    /**
     * 从本地读取图片
     * @param url
     */
    fun getBitmapFromLocal(url: String): Bitmap? {
        try {

            val file = File(CACHE_PATH, url)
            return BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     * @param url
     * @param bitmap
     */
    fun setBitmapToLocal(url: String, bitmap: Bitmap) {
        try {

            val file = File(CACHE_PATH, url)

            //通过得到文件的父文件,判断父文件是否存在
            val parentFile = file.parentFile
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
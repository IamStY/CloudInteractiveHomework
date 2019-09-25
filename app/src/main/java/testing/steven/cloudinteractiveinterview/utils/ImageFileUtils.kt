package testing.steven.cloudinteractiveinterview.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import android.content.ContextWrapper


/*******
 * reference :https://blog.csdn.net/u012560369/article/details/80621483
 */
class ImageFileUtils(context: Context) {
    private var CACHE_PATH: File? = null

    init {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            CACHE_PATH =
                File(Environment.getExternalStorageDirectory().getAbsolutePath(), "imageDir")
        } else {
            val cw = ContextWrapper(context)
            CACHE_PATH = cw.getDir("imageDir", Context.MODE_PRIVATE)
        }

    }

    fun getBitmapFromLocal(url: String): Bitmap? {
        try {

            val file = File(CACHE_PATH, url)
            return BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun setBitmapToLocal(url: String, bitmap: Bitmap) {
        try {

            val file = File(CACHE_PATH, url)
            val parentFile = file.parentFile
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
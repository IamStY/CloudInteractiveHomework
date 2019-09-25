package testing.steven.cloudinteractiveinterview.utils

import android.content.Context
import android.graphics.Bitmap
import testing.steven.cloudinteractiveinterview.interfaces.IBitmapNotify

class BitmapEntranceUtils(context: Context) {

    private var mNwImageRetrieveUtils: NwImageRetrieveUtils? = null
    private var mImageFileUtils: ImageFileUtils? = null
    private var mMemCacheUtils: MemCacheUtils? = null

    init {
        mMemCacheUtils = MemCacheUtils()
        mImageFileUtils = ImageFileUtils(context)
        mNwImageRetrieveUtils = NwImageRetrieveUtils(mImageFileUtils!!, mMemCacheUtils!!)
    }


    fun retrieveImage(url: String, iBitmapNotify: IBitmapNotify) {
        var bitmap: Bitmap? = mMemCacheUtils?.getBitmapFromMemory(url)
        if (bitmap != null) {
            iBitmapNotify.dataFetched(bitmap)
            return
        }
        bitmap = mImageFileUtils?.getBitmapFromLocal(url)
        if (bitmap != null) {
            mMemCacheUtils?.setBitmapToMemory(url, bitmap)
            iBitmapNotify.dataFetched(bitmap)
            return
        }
        mNwImageRetrieveUtils?.getBitmapFromNet(iBitmapNotify, url)
    }



}
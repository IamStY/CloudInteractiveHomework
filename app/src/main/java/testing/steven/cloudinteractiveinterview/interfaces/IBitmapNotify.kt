package testing.steven.cloudinteractiveinterview.interfaces

import android.graphics.Bitmap

interface IBitmapNotify {
    fun dataFetched(data:Bitmap?)
    fun failure()

}
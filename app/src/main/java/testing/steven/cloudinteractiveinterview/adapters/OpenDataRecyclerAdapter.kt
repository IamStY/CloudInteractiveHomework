package testing.steven.cloudinteractiveinterview.adapters



import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import testing.steven.cloudinteractiveinterview.R
import testing.steven.cloudinteractiveinterview.datamodels.CloudDataModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory



class OpenDataRecyclerAdapter(dataModelArrayList: ArrayList<CloudDataModel> , activity :Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var dataModelArrayList = ArrayList<CloudDataModel>()
    internal var activity   : Activity ?= null

    init {
        this.dataModelArrayList.addAll(dataModelArrayList)
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val cellView = LayoutInflater.from(context).inflate(R.layout.adapter_items, parent, false)
        return ViewHolder(cellView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as ViewHolder
        val tv_title = myHolder.tv_title
        val tv_id = myHolder.tv_id
        val iv_image = myHolder.iv_image
        val cloudDataModel = dataModelArrayList[position]

        tv_title.text = cloudDataModel.title
        tv_id.text = cloudDataModel.id
        tv_title.setTag("tv_title$position")

        val runnable = Runnable {
            var bm: Bitmap? = null
            try {
                val `in` = java.net.URL( cloudDataModel.thumbnailUrl!!).openStream()
                bm = BitmapFactory.decodeStream(`in`)
                if(bm!=null&&tv_title.getTag()=="tv_title$position"){
                    activity?.runOnUiThread {
                        iv_image.setImageBitmap(bm)
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
        val thread = Thread(runnable)
        thread.start()

    }

    fun setDataArrayList(cloudDataModels: ArrayList<CloudDataModel>) {
        this.dataModelArrayList.clear()
        this.dataModelArrayList.addAll(cloudDataModels)
        notifyDataSetChanged()
    }
    fun preventMemLeak( ) {
        this.activity = null
    }
    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tv_id: TextView = itemView.findViewById(R.id.tv_id)
        internal var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        internal var iv_image: ImageView = itemView.findViewById(R.id.iv_image)


    }
}

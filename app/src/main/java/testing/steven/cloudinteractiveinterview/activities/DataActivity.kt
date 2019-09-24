package testing.steven.cloudinteractiveinterview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_data.*
import testing.steven.cloudinteractiveinterview.R
import testing.steven.cloudinteractiveinterview.adapters.OpenDataRecyclerAdapter
import testing.steven.cloudinteractiveinterview.datamodels.CloudDataModel
import testing.steven.cloudinteractiveinterview.viewmodel.MainViewModel

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        initView()
        initData()

    }
    private fun initView() {

        var openDataRecyclerAdapter = OpenDataRecyclerAdapter(ArrayList<CloudDataModel>(),this)
        prv_open_list.adapter = openDataRecyclerAdapter
        prv_open_list.layoutManager = GridLayoutManager(this,4,RecyclerView.VERTICAL,false)

        prv_open_list.setHasFixedSize(true)


    }



    private fun assignAdapterData(data: ArrayList<CloudDataModel>) {
        var adapter = prv_open_list.adapter as OpenDataRecyclerAdapter
        adapter.setDataArrayList(data)
    }

    private fun initData() {

        val model = ViewModelProviders.of(this).get(MainViewModel::class.java)

        model.fetchData(this).observe(this, Observer<ArrayList<CloudDataModel>> { openDataModels ->
            assignAdapterData(openDataModels)

        })

    }

    override fun onDestroy() {
        super.onDestroy()
          var adapterData = prv_open_list.adapter as OpenDataRecyclerAdapter
        adapterData.preventMemLeak()

    }
}

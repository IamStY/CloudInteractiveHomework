package testing.steven.cloudinteractiveinterview.viewmodel

import android.content.Context
import android.widget.Toast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import java.util.ArrayList

import testing.steven.cloudinteractiveinterview.api.ApiRequestManager
import testing.steven.cloudinteractiveinterview.interfaces.ICallbackNotify
import testing.steven.cloudinteractiveinterview.datamodels.CloudDataModel

class MainViewModel : ViewModel() {

    private var openDataLiveData: MutableLiveData<ArrayList<CloudDataModel>>? = null

    fun fetchData(context: Context): LiveData<ArrayList<CloudDataModel>> {

        if (openDataLiveData == null) {
            openDataLiveData = MutableLiveData()
        }
        retrieveAPI(context)
        return openDataLiveData!!
    }


    private fun retrieveAPI(context: Context) {


        ApiRequestManager.getInstance()
            .getData(context, object :
                ICallbackNotify<ArrayList<CloudDataModel>> {
                override fun dataFetched(data: Any?) {
                    openDataLiveData?.postValue(data as ArrayList<CloudDataModel>)
                }

                override fun failure() {
                    Toast.makeText(context, "APIFailure", Toast.LENGTH_LONG).show()

                }
            })

    }

}

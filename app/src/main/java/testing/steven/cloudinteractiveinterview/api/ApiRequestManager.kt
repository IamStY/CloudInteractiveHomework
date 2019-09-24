package testing.steven.cloudinteractiveinterview.api


import android.content.Context

import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

import testing.steven.cloudinteractiveinterview.datamodels.CloudDataModel
import java.util.*

class ApiRequestManager {
    private val gson = Gson()
    private var requestQueue: RequestQueue? = null
    // function - get data from api
    fun getData(context: Context, iCallback_notify: ICallbackNotify<*>) {
        val functionURL = "https://jsonplaceholder.typicode.com/photos"
        sendGetRequest(object : TypeToken<ArrayList<CloudDataModel>>() {

        }.type, context, functionURL, iCallback_notify)
    }

    // all get request entrance -
    private fun sendGetRequest(
        type: Type,
        context: Context,
        functionURL: String,
        iCallback_notify: ICallbackNotify<*>?
    ) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context)
        }
        val stringRequest = object : StringRequest(Request.Method.GET, functionURL, { response ->

        }, { error -> iCallback_notify?.failure() }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"

                return headers
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                var responseString = ""
                if (response != null) {
                    responseString = response.statusCode.toString()
                }
                val responseHeaders = response.headers
                if (response.statusCode == 200 || response.statusCode == 304) {
                    val responseRoot = String(response.data)
                    val obj = gson.fromJson<Any>(responseRoot, type)
                     iCallback_notify?.dataFetched(obj)

                } else {

                    // Here we are, we got a 401 response and we want to do something with some header field; in this example we return the "Content-Length" field of the header as a succesfully response to the Response.Listener<String>


                    return Response.success(
                        responseHeaders["Content-Length"],
                        HttpHeaderParser.parseCacheHeaders(response)
                    )
                }

                return Response.success(
                    responseString,
                    HttpHeaderParser.parseCacheHeaders(response)
                )
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue!!.add(stringRequest)


    }

    companion object {
        private var instance: ApiRequestManager? = null
        @Synchronized
        fun getInstance(): ApiRequestManager {
            if (instance != null) {
                return instance!!
            } else {
                instance = ApiRequestManager()
                return instance!!
            }
        }
    }
}

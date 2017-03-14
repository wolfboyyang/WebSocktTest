package com.huawei.androidwebsockettest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.huawei.websocketclienttest.Elastic

class MainActivity : AppCompatActivity() {
    //var client: MyWebSocketClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //client = MyWebSocketClient()
        //client?.Connect()
        val e = Elastic().execute("Hello, Elastic")
        Log.i("MainActivity", "response:" + e)
    }
}

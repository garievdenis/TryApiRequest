package com.example.tryapirequest


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var tvMain: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMain = findViewById(R.id.tvMain)
        button = findViewById(R.id.btn)

        val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        button.text = "Get fact about random year"
        button.setOnClickListener {
            tvMain.text = executorService.submit(Callable {
                httpRequest("http://numbersapi.com/random/year")
            }).get()
        }
    }

    @Throws(IOException::class)
    fun httpRequest(urlString: String):String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        var data: Int = connection.inputStream.read()
        var str = ""
        while (data != -1){
            str += data.toChar()
            data = connection.inputStream.read()
        }
        Log.d("API_QWE", str)
        return str
    }
}
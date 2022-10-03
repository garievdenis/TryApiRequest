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
        /*Инициальизация исполнителя для потока*/
        val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        button.text = "Get fact about random year"
        button.setOnClickListener {
            /*получение данных из потока для отображения в textView*/
            tvMain.text = executorService.submit(Callable {
                httpRequest("http://numbersapi.com/random/year")
            }).get()
        }
    }
    /*Функция для работы в отдельном потоке*/
    @Throws(IOException::class)
    fun httpRequest(urlString: String):String {
        /*Созддание экземпляра URL*/
        val url = URL(urlString)
        /*Создание экзепляра класса для соедниения по протоколу HTTP*/
        val connection = url.openConnection() as HttpURLConnection
        /*Установка метода запроса*/
        connection.requestMethod = "GET"
        /*Отправка запроса и чтение полученых данных*/
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
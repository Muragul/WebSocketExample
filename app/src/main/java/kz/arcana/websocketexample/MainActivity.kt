package kz.arcana.websocketexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.arcana.websocketexample.databinding.ActivityMainBinding
import okhttp3.*
import okio.ByteString

class MainActivity : AppCompatActivity() {

    private val baseUrl = "wss://socketsbay.com/wss/v2/2/demo/"
    private var binding: ActivityMainBinding? = null
    private var client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        bindViews()
    }

    private fun bindViews() {
        binding?.rvMessages?.adapter = Adapter()
        start()
    }

    private fun start() {
        val request = Request.Builder()
            .url(baseUrl)
            .build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                runOnUiThread {
                    binding?.tvTitle?.text = "Open"
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                runOnUiThread {
                    binding?.tvTitle?.text = "Failure"
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                receiveMessage(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                receiveMessage(bytes.toString())
            }
        }
        client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

    private fun receiveMessage(text: String) {
        runOnUiThread {
            (binding?.rvMessages?.adapter as Adapter).addMessage(text)
        }
    }


}
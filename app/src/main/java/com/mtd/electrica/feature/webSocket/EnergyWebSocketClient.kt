package com.mtd.electrica.feature.webSocket

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class EnergyWebSocketClient(url: String, private val onMessageReceived: (String) -> Unit) :
    WebSocketListener() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket

    init {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, this)
        // Ensure the client does not shutdown when the app exits
        client.dispatcher.executorService.shutdown()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        onMessageReceived(text)
    }

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        Log.d(TAG, "WebSocket opened")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        Log.e(TAG, "WebSocket error", t)
    }

    fun close() {
        webSocket.close(1000, "App closed")
    }
}

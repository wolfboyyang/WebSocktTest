/**
 * Created by youi1 on 2017/2/26.
 */

import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class MyWebSocketListener() : WebSocketListener() {
    companion object {
        private val NORMAL_CLOSURE_STATUS = 1000
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Knock, knock!")
        webSocket.send("Hello")
        webSocket.send(ByteString.decodeHex("deadbeef"))
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Receiving: " + text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("Receiving: " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, reason)
        println("Closing: $code  $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response) {
        t.printStackTrace()
    }
}

val localServerUrl = "ws://localhost:9502/ws"
val echoWebocketUrl = "ws://echo.websocket.org"

fun main(args: Array<String>) {
    println("Client Test:")
    val client = OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build()
    val request = Request.Builder().url(localServerUrl).build()
    val listener = MyWebSocketListener()
    val socket = client.newWebSocket(request, listener)

    // Trigger shutdown of the dispatcher's executor so this process can
    // exit cleanly.
    client.dispatcher().executorService().shutdown()
}

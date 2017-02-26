import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer
import java.nio.ByteBuffer
import javax.websocket.*
import javax.websocket.server.ServerEndpoint


/**
 * Created by youi1 on 2017/2/26.
 */

@ServerEndpoint(value="/ws")
class TestServerHandler {
    @OnOpen
    fun onConnect(session : Session) {
        println("Connected")
    }

    @OnMessage
    fun onMessage(session : Session, text : String) {
        println("onMessage: " + text)
    }

    @OnMessage
    fun onMessage(session : Session, bytes : ByteBuffer) {
        println("onMessage: " + bytes.toString())
    }

    @OnMessage
    fun onPing(session : Session, bytes : PongMessage) {
        println("ping/pong")
    }

    @OnClose
    fun onClose(session : Session) {
        println("onClose")
    }

    @OnError
    fun onError(session : Session, error : Throwable) {
        println("onError" to error.toString())
    }
}

fun main(args: Array<String>) {
    println("Server Test:")

    val server = Server(9502)

    val handler = ServletContextHandler(ServletContextHandler.SESSIONS)
    handler.contextPath = "/"
    server.handler = handler

    val wsContainer = WebSocketServerContainerInitializer.configureContext(handler);
    wsContainer.addEndpoint(TestServerHandler::class.java)

    server.start()
}

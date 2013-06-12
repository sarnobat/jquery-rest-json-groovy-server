import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.apache.commons.io.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


class MyHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		println('Request received');
		JSONObject json = new JSONObject();
		json.put("myKey","myValue");
		println('Request headers: ' + t.getRequestHeaders());
		println('Request URI' + t.getRequestURI());
		t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
		t.sendResponseHeaders(200, json.toString().length());
		OutputStream os = t.getResponseBody();
		os.write(json.toString().getBytes());
		os.close();
	}
}
    
HttpServer server = HttpServer.create(new InetSocketAddress(4444), 0);
server.createContext("/", new MyHandler());
server.setExecutor(null); // creates a default executor
server.start();

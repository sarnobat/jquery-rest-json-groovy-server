
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	public Map<String, String> getQueryMap(String query)  
	{  
		Pattern pattern = Pattern.compile("/\\?.*");
		
		Matcher matcher = pattern.matcher(query);
		String[] params = query.split("&");  
		Map<String, String> map = new HashMap<String, String>();  
		for (String param : params)  
		{  
			String name = param.split("=")[0];  
			String value = param.split("=")[1];  
			map.put(name, value);  
		}  
		return map;  
	}  

	public void handle(HttpExchange t) throws IOException {
		JSONObject json = new JSONObject();
		String query = t.getRequestURI();
		Map<String, String> map = getQueryMap(query);  
		String  value = map.get("param1");
		json.put("myKey",value);
		println('Request headers: ' + t.getRequestHeaders());
		println('Request URI' + t.getRequestURI());
		println('value: ' + value);
		json.put("foo","bar");
		t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
		println('handle() - 5');
		t.getResponseHeaders().add("Content-type", "application/json");
		println('handle()' - 6);
		t.sendResponseHeaders(200, json.toString().length());
		println('handle() - 7');
		OutputStream os = t.getResponseBody();
		println('handle() - 8');
		try {
			os.write(json.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			println(e.getStackTrace());
		}
		println('handle() - 9');
		os.close();
		println('handle() - 10');
	}
}
    
HttpServer server = HttpServer.create(new InetSocketAddress(4444), 0);
server.createContext("/", new MyHandler());
server.setExecutor(null); // creates a default executor
server.start();

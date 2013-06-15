
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
		println('getQueryMap() - 1');
		Pattern pattern = Pattern.compile("/\\?.*");
		
		println('getQueryMap() - 1.1');
		Matcher matcher = pattern.matcher(query);
		println('getQueryMap() - 1.2 - ' + matcher.group());
		String[] params = query.split("&");  
		println('getQueryMap() - 2');
		Map<String, String> map = new HashMap<String, String>();  
		println('getQueryMap() - 3');
		for (String param : params)  
		{  
			println('getQueryMap() - 4');
			String name = param.split("=")[0];  
			println('getQueryMap() - 5');
			String value = param.split("=")[1];  
			println('getQueryMap() - 6');
			map.put(name, value);  
			println('getQueryMap() - 7: ' + value);
		}  
		println('getQueryMap() - 8');
		return map;  
	}  

	public void handle(HttpExchange t) throws IOException {
		println('Request received');
		JSONObject json = new JSONObject();
		print('1');
		String query = t.getRequestURI();
		print('2');
		Map<String, String> map = getQueryMap(query);  
		print('3');
		String  value = map.get("param1");
		print('4');
		json.put("myKey",value);
		println('Request headers: ' + t.getRequestHeaders());
		println('Request URI' + t.getRequestURI());
		println('value: ' + value);
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

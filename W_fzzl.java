package fzzl_test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class W_fzzl {

	public final OkHttpClient client = new OkHttpClient();

	@Before
	public void before() throws MalformedURLException {

	}

	@Test//demo
	public void test() throws Exception {

		// demo okhttp get request
		Request request = new Request.Builder().url("https://www.baidu.com").build();

		Response response = client.newCall(request).execute();
		String responseData = response.body().string();

		System.out.println("Server: " + response.header("Server"));
		System.out.println("Date: " + response.header("Date"));
		System.out.println("Vary: " + response.headers("Vary"));
		System.out.println(responseData);

		// demo okhttp post request
		RequestBody formBody = new FormBody.Builder().add("search", "Jurassic Park").build();
		Request request1 = new Request.Builder().url("https://en.wikipedia.org/w/index.php").post(formBody).build();

		Response response1 = client.newCall(request1).execute();
		if (!response1.isSuccessful())
			throw new IOException("Unexpected code " + response1);

		System.out.println(response1.body().string());

	}

	@Test//登陆
	public void login() throws Exception {

		// post json
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", "13371635921");
		map.put("pwd", "admin123");
		map.put("vcd", "1111");
		map.put("vid", "cms11125496");

		String jsonold = gson.toJson(map);
		String json = jsonold.replace("\\", "").replace("\"[", "[").replace("]\"", "]").replace("\"{", "{")
				.replace("}\"", "}");

		System.out.println(json);

		// demo okhttp post request
		RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request request = new Request.Builder().url("http://192.168.200.49:5555/hdzy/fzzl/login?tp=w_fzzl")
				.post(requestBody).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);
		System.out.println(response.body().string());
	}
	
	
	@Test//获取方剂详情
	public void get_fangjidetail() throws Exception {
		
		// login 
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", "13371635921");
		map.put("pwd", "admin123");
		map.put("vcd", "1111");
		map.put("vid", "cms11125496");

		String jsonold = gson.toJson(map);
		String json = jsonold.replace("\\", "").replace("\"[", "[").replace("]\"", "]").replace("\"{", "{")
				.replace("}\"", "}");

		//System.out.println(json);

		//okhttp post json
		RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request request = new Request.Builder().url("http://192.168.200.49:5555/hdzy/fzzl/login?tp=w_fzzl")
				.post(requestBody).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);
		
		String resultjson = response.body().string();
		JsonParser parser = new JsonParser();
		JsonObject resjson = parser.parse(resultjson).getAsJsonObject();
		JsonObject logdata = resjson.get("data").getAsJsonObject();
		String uid = logdata.get("uid").getAsString();
		String token = logdata.get("token").getAsString();
		//System.out.println(logdata);

		// demo okhttp post request		
		RequestBody formBody = new FormBody.Builder().add("fjid", "2150").build();
		Request request1 = new Request.Builder()
				.url("http://192.168.200.49:5555/hdzy/fzzl/getRecommInfo?tp=w_fzzl")
				.addHeader("uid",uid)
				.addHeader("token",token)
				.post(formBody).build();

		Response response1 = client.newCall(request1).execute();
		if (!response1.isSuccessful())
			throw new IOException("Unexpected code " + response1);
		System.out.println(response1.body().string());
	}
	


}

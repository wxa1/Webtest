package main.java.opensns;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import main.java.tools.settings;

public class login {

	private  CloseableHttpClient httpclient = HttpClients.createDefault();
	private  String remember;
	private  String username;
	private  String password;
	private  String sql;
	private  String nickname;
	private  String url;
	
	
	@Before
	public void before() throws MalformedURLException {

		username = "test_java2@test.com";
		nickname = "test_java2";
		remember = "0 ";
		password = "test_java2";
		
	}

	@Test
	public void loginTest() {
		String url = settings.env("test");
		String loginURL = url + "/sns/index.php?s=/ucenter/member/login.html";
		HttpPost httppost = new HttpPost(loginURL);

		// ����Post�������
		List<NameValuePair> formparams = new ArrayList<>();
		formparams.add(new BasicNameValuePair("username", username));
		formparams.add(new BasicNameValuePair("remember", remember));
		formparams.add(new BasicNameValuePair("password", password));
		formparams.add(new BasicNameValuePair("from", "http://192.168.200.198:8008/sns/index.php?s=/home/index/register.html"));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httppost);
			 HttpEntity entity = response.getEntity();
			 String postResult = EntityUtils.toString(entity, "UTF-8");
			System.out.println("请求结果：" + postResult);

//			sql = "SELECT * FROM `ocenter_member` where nickname = '" + nickname + "'";
//			// System.out.printlng(sql);
//			dbtools.queryDB("test", sql, "nickname", nickname);

		} catch (Exception e) {
			e.printStackTrace();
		}

		httppost.releaseConnection();
	}

}

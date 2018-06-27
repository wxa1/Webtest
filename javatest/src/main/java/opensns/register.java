package main.java.opensns;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import main.java.tools.dbtools;
import main.java.tools.settings;

public class register {

	private  CloseableHttpClient httpclient = HttpClients.createDefault();
	private  String role;
	private  String username;
	private  String reg_type;
	private  String nickname;
	private  String password;
	private  String sql;
	private  String loginURL;
	private  String url;
	
	
	@Before
	public void before() throws MalformedURLException {

		role = "1";
		username = "test_java2@test.com";
		reg_type = "email";
		nickname = "test_java2";
		password = "test_java2";
		
	}

	@Test
	public void zhuceTest() {
		String url = settings.env("test");
		String loginURL = url + "/sns/index.php?s=/ucenter/member/register.html";
		HttpPost httppost = new HttpPost(loginURL);

		// ����Post�������
		List<NameValuePair> formparams = new ArrayList<>();
		formparams.add(new BasicNameValuePair("role", role));
		formparams.add(new BasicNameValuePair("username", username));
		formparams.add(new BasicNameValuePair("reg_type", reg_type));
		formparams.add(new BasicNameValuePair("nickname", nickname));
		formparams.add(new BasicNameValuePair("password", password));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			// CloseableHttpResponse response =
			httpclient.execute(httppost);
			// HttpEntity entity = response.getEntity();
			// String postResult = EntityUtils.toString(entity, "UTF-8");
			// System.out.println("请求结果：" + postResult);

			sql = "SELECT * FROM `ocenter_member` where nickname = '" + nickname + "'";
			// System.out.printlng(sql);
			dbtools.queryDB("test", sql, "nickname", nickname);

		} catch (Exception e) {
			e.printStackTrace();
		}

		httppost.releaseConnection();
	}

}

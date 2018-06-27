package main.java.function_test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
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
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import main.java.tools.DataExtractTool;
import main.java.tools.dbtools;
import main.java.tools.settings;



@RunWith(Parameterized.class)
public class register_func {

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private String role;
	private String username;
	private String reg_type;
	private String nickname;
	private String password;
	private String sql;
	@Parameters
	public static Collection<Object> spreadsheetData() throws IOException {
		  InputStream excelFile = new FileInputStream("E:/scripts/register_cases.xls");
		  return new DataExtractTool(excelFile).getData();
		 }
	
	public register_func(String username,String nickname,String reg_type,String password,Double role) {
		  super();
		  this.username = username;
		  this.nickname = nickname;
		  this.reg_type = reg_type;
		  this.password = password;
		  this.role = String.valueOf(role);
		 }


	@BeforeClass
	public static void before() throws MalformedURLException {
		
		 dbtools.delsqlDB("test", "DELETE FROM `ocenter_member` WHERE `uid` !=1");
		 dbtools.exesql("test", "alter table `ocenter_member` AUTO_INCREMENT=2");
		
		 dbtools.delsqlDB("test", "DELETE FROM `ocenter_ucenter_member` Where `id`!=1");
		 dbtools.exesql("test", "alter table `ocenter_ucenter_member` AUTO_INCREMENT=2");
	}

	@Test
	public void Testzhuce() {
		String url = settings.env("test");
		String loginURL = url + "/sns/index.php?s=/ucenter/member/register.html";
		HttpPost httppost = new HttpPost(loginURL);
		System.out.println(username);

		// 创建Post请求参数
		List<NameValuePair> formparams = new ArrayList<>();
		formparams.add(new BasicNameValuePair("role",role));
		formparams.add(new BasicNameValuePair("username", username));
		formparams.add(new BasicNameValuePair("reg_type", reg_type));
		formparams.add(new BasicNameValuePair("nickname", nickname));
		formparams.add(new BasicNameValuePair("password", password));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String postResult = EntityUtils.toString(entity, "UTF-8");
			System.out.println("接口请求返回的结果：" + postResult);

			sql = "SELECT * FROM `ocenter_member` where nickname = '" + nickname + "'";
			dbtools.queryDB("test", sql, "nickname", nickname);

		} catch (Exception e) {
			e.printStackTrace();
		}

		httppost.releaseConnection();
	}
	
//	@AfterClass
//	public static void after() throws MalformedURLException {
//
//		 dbtools.delsqlDB("test", "DELETE FROM `ocenter_member` WHERE `uid` !=1");
//		 dbtools.delsqlDB("test", "DELETE FROM `ocenter_ucenter_member` Where `id`!=1");
//		 
//	}

}

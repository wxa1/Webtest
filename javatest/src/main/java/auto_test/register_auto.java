package main.java.auto_test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
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

import main.java.tools.dbtools;
import main.java.tools.settings;

//@RunWith(Parameterized.class)
public class register_auto {

	public CloseableHttpClient httpclient = HttpClients.createDefault();
	public static String role;
	public static String username;
	public static String reg_type;
	public static String nickname;
	public static String password;
	public static String sql;

 @BeforeClass
 public static void beforeclass() throws MalformedURLException {
	//
	 dbtools.delsqlDB("test", "DELETE FROM `ocenter_member` WHERE `uid` !=1");
	 dbtools.exesql("test", "alter table `ocenter_member` AUTO_INCREMENT=2");
	
	 dbtools.delsqlDB("test", "DELETE FROM `ocenter_ucenter_member` Where `id`!=1");
	 dbtools.exesql("test", "alter table `ocenter_ucenter_member` AUTO_INCREMENT=2");
	//
 }

	// @Before
	// public void before() throws MalformedURLException {
	//
	//
	//
	// }

	@Test
	public void Testzhuce() {
		String url = settings.env("test");
		String loginURL = url + "/sns/index.php?s=/ucenter/member/register.html";
		HttpPost httppost = new HttpPost(loginURL);

		for (int i = 0; i < 2; i++) {

			int[] roles = { 1, 2 };
			Random rad = new Random();
			int index = rad.nextInt(roles.length);
			role = String.valueOf(roles[index]);

			String[] reg_types = { "email", "phone" };
			Random rad1 = new Random();
			int index1 = rad1.nextInt(reg_types.length);
			reg_type = reg_types[index1];

			String name = RandomStringUtils.randomAlphanumeric(10);
			username = name+"@test.com";
			System.out.println(name);
			nickname = name;
			password = name;

			// ����Post�������
			List<NameValuePair> formparams = new ArrayList<>();
			formparams.add(new BasicNameValuePair("role", role));
			formparams.add(new BasicNameValuePair("username", username));
			formparams.add(new BasicNameValuePair("reg_type", reg_type));
			formparams.add(new BasicNameValuePair("nickname", nickname));
			formparams.add(new BasicNameValuePair("password", password));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
				CloseableHttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				String postResult = EntityUtils.toString(entity, "UTF-8");
				System.out.println("请求结果" + postResult);

				sql = "SELECT * FROM `ocenter_member` where nickname = '" + nickname + "'";
				dbtools.queryDB("test", sql, "nickname", nickname);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		httppost.releaseConnection();
	}

	// @AfterClass
	// public static void after() throws MalformedURLException {
	//
	// dbtools.delsqlDB("test", "DELETE FROM `ocenter_member` WHERE `uid` !=1");
	// dbtools.delsqlDB("test", "DELETE FROM `ocenter_ucenter_member` Where
	// `id`!=1");
	//
	// }

}

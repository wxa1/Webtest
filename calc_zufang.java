package main.java.calc_test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.tools.CSVUtils;
import main.java.tools.DataExtractTool;
import main.java.tools.LogUtil;


//通过输入的xls用例调用智能组方算法接口，并根据固定格式生成log文件，供医学部人员评价智能组方结果；

/*输出log示例
 * f76dfc915ed8b2a4的原始症状： 
主诉：感冒三天，头晕无力；刻下症：{"s":["头痛","喘"],"i":["无汗","有汗"]}；舌象：["舌苔薄白"]；脉象：["脉浮","脉紧"]；证候详情：["内伤脾肾","中风伤寒"]
智能证候提示：["风寒束表证","气虚伤风","表热","表里俱寒证","表里俱实"]
推荐方剂1： 方剂id：2383；方剂名称：调荣养卫汤；方剂组成：[人参, 黄芪, 当归, 羌活, 防风, 白术, 陈皮, 柴胡, 地黄, 甘草, 细辛, 川芎]
推荐方剂2： 方剂id：2176；方剂名称：知母汤；方剂组成：[知母, 麻黄, 升麻, 石膏, 甘草]
推荐方剂列表：{2383=调荣养卫汤, 2176=知母汤, 2151=感冒汤2号, 2106=九味羌活汤, 2129=麻黄杏仁汤, 2233=麻黄汤, 2274=柴胡散, 2302=升麻发表汤, 2419=益气散风汤, 2333=感冒汤1号方} 
 */

@RunWith(Parameterized.class)
public class calc_zufang {

	private CloseableHttpClient httpclient = HttpClients.createDefault();

	private String uid = "22222";
	private String uip = "192.168.200.88";
	private String id;
	private BigInteger sj;
	private String xm;
	private String xb;
	private String sfz;
	private String mz;
	private int nl;
	private int sg;
	private int tz;
	private String hyzk;
	private String zy;
	private String zz;
	private String gmw;
	private String jws;
	private String jzs;
	private String zs;
	private String yjfl;
	private String ejfl;
	private String kxz;
	private String mx;
	private String sx;
	private String xyjb;
	private int qqbs;
	private String zhxx;
	private int cftjsm;
	private int zhycsm;
	private int cfzs;
	private int zhsm;


	@Parameters
	public static Collection<Object> spreadsheetData() throws IOException {
		InputStream excelFile = new FileInputStream("D:/zufang/12197430.xls");
		return new DataExtractTool(excelFile).getData();
	}

	public calc_zufang(String id, String sj, String xm, String xb, String sfz, String mz, String nl, String sg,
			String tz, String hyzk, String zy, String zz, String gmw, String jws, String jzs, String zs, String yjfl,
			String ejfl, String kxz, String mx, String sx, String xyjb, String qqbs, String zhxx, String cftjsm,
			String cfzs, String zhsm, String zhycsm) {
		super();
		this.id = id;
		this.sj = new BigInteger(sj);
		this.xm = xm;
		this.xb = xb;
		this.sfz = sfz;
		this.mz = mz;
		this.nl = Integer.valueOf(nl);
		this.sg = Integer.valueOf(sg);
		this.tz = Integer.valueOf(tz);
		this.hyzk = hyzk;
		this.zy = zy;
		this.zz = zz;
		this.gmw = gmw.replaceAll("\\\\", "");
		this.jws = jws;
		this.jzs = jzs;
		this.zs = zs;
		this.yjfl = yjfl;
		this.ejfl = ejfl;
		this.kxz = kxz;
		this.mx = mx;
		this.sx = sx;
		this.xyjb = xyjb;
		this.qqbs = Integer.valueOf(qqbs);
		this.zhxx = zhxx;
		this.cftjsm = Integer.valueOf(cftjsm);
		this.cfzs = Integer.valueOf(cfzs);
		this.zhsm = Integer.valueOf(zhsm);
		this.zhycsm = Integer.valueOf(zhycsm);

	}

	@BeforeClass
	public static void before() throws MalformedURLException {

		CSVUtils.deleteFile("D:/logs/", "zufang.log");

	}

	@Test
	public void Testcalc() {

		String loginURL = calcenv("gao");
		HttpPost httppost = new HttpPost(loginURL);

		Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("sj", sj);
		map.put("xm", xm);
		map.put("xb", xb);
		map.put("sfz", sfz);
		map.put("mz", mz);
		map.put("nl", nl);
		map.put("sg", sg);
		map.put("tz", tz);
		map.put("hyzk", hyzk);
		map.put("zy", zy);
		map.put("zz", zz);
		map.put("gmw", gmw);
		map.put("jws", jws);
		map.put("jzs", jzs);
		map.put("zs", zs);
		map.put("yjfl", yjfl);
		map.put("ejfl", ejfl);
		map.put("kxz", kxz);
		map.put("mx", mx);
		map.put("sx", sx);
		map.put("xyjb", xyjb);
		map.put("qqbs", qqbs);
		map.put("zhxx", zhxx);
		map.put("cftjsm", cftjsm);
		map.put("cfzs", cfzs);
		map.put("zhsm", zhsm);
		map.put("zhycsm", zhycsm);

		// System.out.println(map);

		String data = gson2.toJson(map);
		String datanew = data.replace("\\", "").replace("\"[", "[").replace("]\"", "]").replace("\"{", "{")
				.replace("}\"", "}");
		// System.out.println(datanew);

		// String data1 = CSVUtils.transMapToString(map);
		// System.out.println(data1);

		//
		List<NameValuePair> formparams = new ArrayList<>();
		formparams.add(new BasicNameValuePair("uid", uid));
		formparams.add(new BasicNameValuePair("uip", uip));
		formparams.add(new BasicNameValuePair("data", datanew));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
    		String postResult = EntityUtils.toString(entity, "UTF-8");
			// System.out.println("请求结果" + postResult);

			JsonParser parser = new JsonParser();
			JsonObject Jsobj = parser.parse(postResult).getAsJsonObject();
			// System.out.println(Jsobj);

			JsonArray zhts = Jsobj.get("zhts").getAsJsonArray();
			
			LogUtil.zufang(id + "的原始症状： "+ "\n" + "主诉：" + zs + "刻下症：" + kxz + "舌象：" + sx + "脉象：" + mx + "证候详情："
					+ zhxx);
			LogUtil.zufang("智能证候提示：" + zhts);

			//获取推荐方剂
			JsonArray tjfj = Jsobj.get("tjfj").getAsJsonArray();
			String fjid = null;
			String fjmc = null;
			for (int i = 0; i < tjfj.size(); i++) {
				JsonObject fj_detail = tjfj.get(i).getAsJsonObject();

				fjid = fj_detail.get("fjid").getAsString();
				fjmc = fj_detail.get("fjmc").getAsString();
				JsonArray zcxq = fj_detail.get("zcxq").getAsJsonArray();
				List<String> ymlist = new ArrayList<String>();
				for (int j = 0; j < zcxq.size(); j++) {
					JsonObject zcxq_detail = zcxq.get(j).getAsJsonObject();
					String ym = zcxq_detail.get("ym").getAsString();
					ymlist.add(ym);
					}
				 System.out.println(ymlist);
				
				LogUtil.zufang("推荐方剂"+(i+1)+"： "+"方剂id：" + fjid + "方剂名称：" + fjmc + "方剂组成：" + ymlist);
			}

			JsonArray fjlb = Jsobj.get("fjlb").getAsJsonArray();
			Map<String, String> fjmap = new LinkedHashMap<String, String>();
			for (int k = 0; k < fjlb.size(); k++) {
				JsonObject fjlb_detail = fjlb.get(k).getAsJsonObject();

				fjid = fjlb_detail.get("fjid").getAsString();
				fjmc = fjlb_detail.get("fjmc").getAsString();
				fjmap.put(fjid, fjmc);	
			}
			String fjlist = fjmap.toString();
			LogUtil.zufang("推荐方剂列表："+fjlist +"\n");			

		} catch (Exception e) {
			e.printStackTrace();
		}

		httppost.releaseConnection();
	}
	
	
	//选择测试算法
	public static String calcenv(String calcenv) {

		String url = null;
		if (calcenv == "yang") {
			url = "http://192.168.200.216:8060/rec";
		} else if (calcenv == "gao") {
			url = "http://192.168.200.204:7000/r/1";
		} 
		return url;
	}
	

	// @AfterClass
	// public static void after() throws MalformedURLException {
	// }

}

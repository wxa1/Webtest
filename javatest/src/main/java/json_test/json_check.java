package main.java.json_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import main.java.tools.CSVUtils;
import main.java.tools.LogUtil;

//功能1：检查方剂json文件的数据，包括必填项/数据格式/重复数据等；
//功能2：提取方剂中的关键信息，便于构造智能组方算法的测试用例；

public class json_check {

	public static void main(String[] args) throws Exception {

		CSVUtils.deleteFile("D:/", "fangjiall.csv");
		CSVUtils.deleteFile("D:/logs/", "fangji.log");
		File csv = new File("D:/fangjiall.csv");
		OutputStreamWriter bwnew = new OutputStreamWriter(new FileOutputStream(csv, true), "UTF-8"); //
		bwnew.write("\"方剂ID\"" + "," + "\"方剂名称\"" + "," + "\"一级菜单" + "," + "\"二级菜单\"" + "," + "\"症状\"" + "," + "\"症状详情\""+ "," + "\"证候诊断" + "," + "\"处方组成\"" + "," + "\"舌象\"" + "," + "\"脉象\"" + "," + "\"西医疾病\"");
		bwnew.write("\r\n");

		JsonParser parser = new JsonParser();

		try {

			JsonArray array = (JsonArray) parser
					.parse(new InputStreamReader(new FileInputStream("D:/all832.json"), "UTF-8"));

			LogUtil.main("方剂总数：" + array.size());
			List<String> fjid_list = new ArrayList<String>();
			List<String> fjmc_list = new ArrayList<String>();

			for (int i = 0; i < array.size(); i++) {

				// LogUtil.main("-----------------");

				JsonObject subObject = array.get(i).getAsJsonObject();

				// 必填项检查
				String fjid = subObject.get("id").getAsString();
				if (fjid.isEmpty()) {
					LogUtil.main("方剂id为空");
				}
				String fjmc = subObject.get("fjmc").getAsString();
				if (fjmc.isEmpty()) {
					LogUtil.main("方剂名称为空");
				}
				String yjlm = subObject.get("yjlm").getAsString();
				if (yjlm.isEmpty()) {
					LogUtil.main("一级分类为空");
				}
				String ejlm = subObject.get("ejlm").getAsString();
				if (ejlm.isEmpty()) {
					LogUtil.main("二级分类为空");
				}

				// 症状检查
				String zz = subObject.get("zz").getAsString();
				if (zz.isEmpty()) {
					LogUtil.main(fjid + "," + " " + "," + "症状为空");
				}
				boolean quoteFlag = false;
				if (zz.contains("\"")) { 
					zz = zz.replaceAll("\"", "\"\"");
					zz = "\"" + zz + "\"";
					quoteFlag = true;
				}
				if (zz.contains(",") && !quoteFlag) {
					zz = "\"" + zz + "\"";
				}

				//症状详情检查
				JsonArray zzxq = subObject.get("zzxq").getAsJsonArray();
				if (zzxq.size() <= 0) {
					LogUtil.main(fjid + "," + " " + "," + "症状详情为空");
				}
				String zzxq_new = zzxq.toString();
				if (zzxq_new.contains("\"")) { 
					zzxq_new = zzxq_new.replaceAll("\"", "\"\"");
					zzxq_new = "\"" + zzxq_new + "\"";
					quoteFlag = true;
				}
				if (zzxq_new.contains(",") && !quoteFlag) { 
					zzxq_new = "\"" + zzxq_new + "\"";
				}
				// LogUtil.main(zzxq_new);

				// 证候诊断检查
				JsonArray zhzd = subObject.get("zhzd").getAsJsonArray();
				if (zhzd.size() <= 0) {
					LogUtil.main(fjid + "," + " " + "," + "证候诊断为空");
				}
				String zhzd_new = zhzd.toString();
				if (zhzd_new.contains("\"")) {
					zhzd_new = zhzd_new.replaceAll("\"", "\"\"");
					zhzd_new = "\"" + zhzd_new + "\"";
					quoteFlag = true;
				}
				if (zhzd_new.contains(",") && !quoteFlag) {
					zhzd_new = "\"" + zhzd_new + "\"";
				}
				// LogUtil.main(zhzd_new);

				//组成详情检查
				JsonArray zcxq = subObject.get("zcxq").getAsJsonArray();

				if (zcxq.size() <= 0) {
					LogUtil.main(fjid + "," + "组成详情为空");
				}
				String zcxq_new = zcxq.toString();
				if (zcxq_new.contains("\"")) {
					zcxq_new = zcxq_new.replaceAll("\"", "\"\"");
					zcxq_new = "\"" + zcxq_new + "\"";
					quoteFlag = true;
				}
				if (zcxq_new.contains(",") && !quoteFlag) { 
					zcxq_new = "\"" + zcxq_new + "\"";
				}
				// LogUtil.main(zcxq_new);

				// 药品名称提取
				for (int j = 0; j < zcxq.size(); j++) {
					JsonObject zcxq_detail = zcxq.get(j).getAsJsonObject();

					String ym = zcxq_detail.get("ym").getAsString();
					if (ym.isEmpty()) {
						LogUtil.main(fjid + "," + "药名为空");
					}
					String ypm = zcxq_detail.get("ypm").getAsString();
					if (ypm.isEmpty()) {
						LogUtil.main(fjid + "," + ym + "," + "饮片名为空");
					}
					int jl = zcxq_detail.get("jl").getAsInt();
					if (jl<=0) {
						LogUtil.main(fjid + "," + ym + "," + "剂量不合法");
					}
					String dw = zcxq_detail.get("dw").getAsString();
					String dw1 = "g";
					String dw2 = "mg";
					if (dw.isEmpty()) {
						LogUtil.main(fjid + "," + ym + "," + "单位为空");
					} else if (dw.equals(dw1) == false && dw.equals(dw2) == false) { //
						LogUtil.main(fjid + "," + ym + "," + "单位不合法");
					}

				}

				fjid_list.add(fjid);
				fjmc_list.add(fjmc);

				// 舌象数据提取
				JsonArray sx = subObject.get("sx").getAsJsonArray();
				String sx_new = sx.toString();
				if (sx_new.contains("\"")) {
					sx_new = sx_new.replaceAll("\"", "\"\"");
					sx_new = "\"" + sx_new + "\"";
					quoteFlag = true;
				}
				if (sx_new.contains(",") && !quoteFlag) {
					sx_new = "\"" + sx_new + "\"";
				}

				// 脉象数据提取
				JsonArray mx = subObject.get("mx").getAsJsonArray();
				String mx_new = mx.toString();
				if (mx_new.contains("\"")) { 
					mx_new = mx_new.replaceAll("\"", "\"\"");
					mx_new = "\"" + mx_new + "\"";
					quoteFlag = true;
				}
				if (mx_new.contains(",") && !quoteFlag) {
					mx_new = "\"" + mx_new + "\"";
				}

				JsonArray xyjb = subObject.get("xyjb").getAsJsonArray();
				String xyjb_new = xyjb.toString();
				if (xyjb_new.contains("\"")) { 
					xyjb_new = xyjb_new.replaceAll("\"", "\"\"");
					xyjb_new = "\"" + xyjb_new + "\"";
					quoteFlag = true;
				}
				if (xyjb_new.contains(",") && !quoteFlag) { 
					xyjb_new = "\"" + xyjb_new + "\"";
				}

				bwnew.write(fjid + "," + fjmc + "," + yjlm + "," + ejlm + "," + zz + "," + zzxq_new + "," + zhzd_new
						+ "," + zcxq_new + "," + sx_new + "," + mx_new + "," + xyjb_new);
				bwnew.write("\r\n");
			
				
				//用量及频次检查
				try {
					int yl = subObject.get("yl").getAsInt();
		        } catch (NumberFormatException e) {		        	
		        	LogUtil.main(fjid + "," + " " + "," + "用量格式不合法");
		        	//System.out.println(e); 
		        } 
			    
				try {
					int pc = subObject.get("yl").getAsInt();
		        } catch (NumberFormatException e) {		
		        	LogUtil.main(fjid + "," + " " + "," + "频次格式不合法");
		            //System.out.println(e); 
		        } 
				
			
			
			
			
			}
			bwnew.close();
					

			//方剂ID及名称查重
			List<String> duplicate = getDuplicateElements(fjid_list);
			if (!duplicate.isEmpty()) {
				LogUtil.main("重复的方剂ID" + duplicate);
			}

			List<String> duplicate1 = getDuplicateElements(fjmc_list);
			if (!duplicate1.isEmpty()) {
				LogUtil.main("重复的方剂名称：" + duplicate1 + "共有" + duplicate1.size() + "条");
			}

		} catch (JsonIOException e) {

			e.printStackTrace();

		} catch (JsonSyntaxException e) {

			e.printStackTrace();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

	}

	//list判重并取出重复值
	public static <E> List<E> getDuplicateElements(List<E> list) {
		return list.stream()
				.collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
				.entrySet().stream()
				.filter(entry -> entry.getValue() > 1) 
				.map(entry -> entry.getKey()) 
				.collect(Collectors.toList());
	}

	// 判定对象是否整数
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	
}

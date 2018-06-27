package main.java.json_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import main.java.tools.CSVUtils;

//合并同一目录下的多个方剂json文件，生成一个新的文件

public class gen_newjson {

	public static void main(String[] args) throws Exception {

		
		String filePath = "D:/fangji";
		ArrayList<File> FileList = new ArrayList<File>();
		FileList = CSVUtils.getFiles(filePath);
		//System.out.println(FileList.get(0));

		JsonArray fangji = new JsonArray();
		
		// ArrayList<String> iconNameList = new ArrayList<String>();//返回文件名数组
		// for(int i=0;i<FileList.size();i++){
		// String curpath = FileList.get(i).getPath();//获取文件路径
		// iconNameList.add(curpath.substring(curpath.lastIndexOf("\\")+1));//将文件名加入数组
		// }
		// System.out.println(iconNameList);

		JsonParser parser = new JsonParser();
		for (int i = 0; i < FileList.size(); i++) {
			try {

				JsonObject object = (JsonObject) parser
						.parse(new InputStreamReader(new FileInputStream(FileList.get(i)), "UTF-8"));
				fangji.add(object);
			}

			catch (JsonIOException e) {

				e.printStackTrace();

			} catch (JsonSyntaxException e) {

				e.printStackTrace();

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}
		}
		CSVUtils.deleteFile("D:/", "fangji.json");
		File json = new File("D:/fangji.json");
		OutputStreamWriter bwnew = new OutputStreamWriter(new FileOutputStream(json, true), "UTF-8"); //
		bwnew.write(fangji.toString());
		bwnew.close();
		System.out.println("文件合并完成！");
	}
}
package main.java.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CSVUtils {

	public static void main(String[] args) {
		try {

			File csv = new File("E:/test.csv"); //

			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); //
			bw.write("\"����\"" + "," + "\"1988\"" + "," + "\"1992\"");
			bw.newLine();
			bw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除该目录filePath下的所有文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 * @param fileName
	 *            文件名称
	 */
	public static void deleteFile(String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return;
					}
				}
			}
		}
	}

	/**
	 * 检查数组中是否有重复值
	 * 
	 * @param 数组名称
	 *            文件目录路径
	 */
	public static boolean checkRepeat(String[] array) {
		Set<String> set = new HashSet<String>();
		for (String str : array) {
			set.add(str);
		}
		if (set.size() != array.length) {
			return false;// 有重复
		} else {
			return true;// 不重复
		}
	}

	
	//获取文件夹下全部文件
	public static ArrayList<File> getFiles(String path) throws Exception {
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileIndex : files) {
				if (fileIndex.isDirectory()) {
					getFiles(fileIndex.getPath());
				} else {
					fileList.add(fileIndex);
				}
			}
		}
		return fileList;
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String transMapToString(Map map){  
		  java.util.Map.Entry entry;  
		  StringBuffer sb = new StringBuffer();  
		  for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
		  {  
		    entry = (java.util.Map.Entry)iterator.next();  
		      sb.append(entry.getKey().toString()).append( ":" ).append(null==entry.getValue()?"":  
		      entry.getValue().toString()).append (iterator.hasNext() ? "," : "");  
		  }  
		  return sb.toString();  
		} 

}
package main.java.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import main.java.tools.dbtools;
import main.java.tools.settings;

public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		abc();
		dbtools.conn("zentao");
		int sj = Integer.valueOf("123456");
		System.out.println(sj);

	}

	public static void abc() {

		int[] a = { 1, 2, 3, 3, 3, 2, 2, 4, 3, 1, 2, 3 };
		int b = a.length;
		Map<Integer, Integer> c = new HashMap<>();
		for (int i = 0; i < b; i++) {
			int y = a[i];
			if (y <= b) {
				c.put(a[i], y);
			}
			System.out.println(c);
		}
	}
	
	


}

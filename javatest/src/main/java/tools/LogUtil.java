package main.java.tools;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtil {
	
	public static void main(String info) throws Exception {
		//日志记录器
		Logger logger = Logger.getLogger("tools");
		
		//需要记录的日志消息
		LogRecord lr = new LogRecord(Level.INFO, info);
		
		//日志处理器
		FileHandler fileHandler = addfileHandler("D:/logs/fangji.log", true);

		//为处理器设置日志格式：Formatter
		fileHandler.setFormatter(new MyLogHander()); 
		
		//注册处理器
		logger.addHandler(fileHandler);
		//记录日志消息
		logger.log(lr);
		fileHandler.close();

	}
	
	public static void zufang(String info) throws Exception {
		//日志记录器
		Logger logger = Logger.getLogger("tools");
		
		//需要记录的日志消息
		LogRecord lr = new LogRecord(Level.INFO, info);
		
		//日志处理器
		FileHandler fileHandler = addfileHandler("D:/logs/zufang.log", true);

		//为处理器设置日志格式：Formatter
		fileHandler.setFormatter(new MyLogHander()); 
		
		//注册处理器
		logger.addHandler(fileHandler);
		//记录日志消息
		logger.log(lr);
		fileHandler.close();

	}
	

	
	public static class MyLogHander extends Formatter {
        @Override
        public String format(LogRecord record) {
                return  record.getMessage()+"\n";
        } 

	}
	
	public static FileHandler addfileHandler(String pattern, boolean append) throws Exception, IOException {
        // 控制台输出的handler
		FileHandler fileHandler = new FileHandler(pattern, append);
		return  fileHandler;
    }
	
}

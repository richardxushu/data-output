package com.taotaosou.data;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Richard.xu
 * @version 2013-10-31 下午04:32:45
 */
public class Booter {
	
	public static void main(String[] args) throws InterruptedException,
			IOException {
		
		// classloader the dependencies jars
		final String lib = System.getProperty("user.dir") + "/lib";
		final String[] extjars = JVMUtil.addAllJarsToClassPath(lib);
		for (String jarname : extjars) {
			System.out.println("add path [" + jarname + "]");
		}
		
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		
//		ctx.getBean("fullProductReader");
		
		System.out.println("init spring context = " + ctx.toString());
	}
	
}

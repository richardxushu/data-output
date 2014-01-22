/*
 * CopyRight (c) 2005-2012 Taotaosou Co, Ltd. All rights reserved.
 * Filename:    JVMUtil.java
 * Creator:     joe.zhao
 * Create-Date: 上午11:45:38
 */
package com.taotaosou.data;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: JVMUtil, v 0.1 2013年11月11日 上午11:45:38 Exp $
 */
public abstract class JVMUtil {
	
	public final static boolean appendtoClassPath(String name) {
		try {
			ClassLoader clsLoader = ClassLoader.getSystemClassLoader();
			Method appendToClassPathMethod = clsLoader.getClass()
					.getDeclaredMethod("appendToClassPathForInstrumentation",
							String.class);
			if (null != appendToClassPathMethod) {
				appendToClassPathMethod.setAccessible(true);
				appendToClassPathMethod.invoke(clsLoader, name);
			}
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public final static String[] addAllJarsToClassPath(String dirName) {
		List<String> ret = new ArrayList<String>();
		
		File dir = new File(dirName);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					ret.addAll(Arrays.asList(addAllJarsToClassPath(file
							.getAbsolutePath())));
				} else {
					String filename = file.getName().toLowerCase();
					if (filename.endsWith(".jar")) {
						if (appendtoClassPath(file.getAbsolutePath())) {
							ret.add(file.getAbsolutePath());
						}
					}
				}
			}
		}
		
		return ret.toArray(new String[0]);
	}
	
}

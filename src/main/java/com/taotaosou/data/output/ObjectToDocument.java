/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output;

import java.lang.reflect.Field;
import java.util.List;

import org.dom4j.Element;

import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;

/**
 * 类ObjectToDocument.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年2月11日 下午2:39:08
 */
public class ObjectToDocument {

    public static void pirntFieldValue(Object obj) {
        Class cls = obj.getClass();

        try {
            Field fArr[] = cls.getDeclaredFields();
            for (Field f : fArr) {
                System.out.print(f.getName() + "---");
                f.setAccessible(true);
                System.out.println(f.get(obj));

            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 反射生成xml
     * 
     * @param obj
     * @return
     */
    public static void addItemElement(Element document, Object obj) {
        Element item = document.addElement("item");
        Class cls = obj.getClass();
        try {
            Field fArr[] = cls.getDeclaredFields();
            for (Field f : fArr) {
                f.setAccessible(true);
                
                Element subElement = item.addElement(f.getName());
                String value = "";
                if (f.get(obj) != null) value = f.get(obj).toString();

                if ("java.lang.String".equals(f.getType().getName()) ) {
                    value = "<![CDATA[" + value +"]]>";
                }else if("java.util.List".equals(f.getType().getName())){
                    if(value.length()<3){
                        value ="";
                    }else{
                        value=value.substring(1, value.length()-1);
                    }
                    value = "<![CDATA[" + value +"]]>";
                }
                
                subElement.setText(value);
            }
            // picurl.setText("<![CDATA[" + pl.getMainImageUrl() + "]]>");

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

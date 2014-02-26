/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.output.strategy;

import java.lang.reflect.Field;

import org.dom4j.Element;

/**
 * 类TszXmlFormatBehavior.java的实现描述：转化成360的格式:
 *  <flag:0><pic_url:http://img.taotaosou.cn/image3/M05/00/15/CgAAQVFCv6kIAAAAAAFAP0X4nnQAAAIjwCK9gIAAUBX451.jpg><page_url:http://gouwu.taotaosou.com/product-1001448857.html><pic_title: 灬全场特价灬卡哇伊袖珍双色太阳花包草编包沙滩包咖啡手拎包 ><pic_type: 包袋 ><pic_tag: 包袋 , 女包 ><pic_desc:><price: 11.0><onsale:1><update_time:2013-04-26 10:51:33>
 * @author Richard.xu 2014年2月26日 上午11:01:31
 */
public class TszXmlFormatBehavior implements FormatBehavior {

    /* (non-Javadoc)
     * @see com.taotaosou.data.output.strategy.FormatBehavior#format()
     */
    @Override
    public String format(Object obj) {
        
        if(obj==null)
            return "";
        
        StringBuilder resultStrBuilder = new StringBuilder();
        
        Class cls = obj.getClass();
        try {
            Field fArr[] = cls.getDeclaredFields();
            for (Field f : fArr) {
                f.setAccessible(true);
                
                resultStrBuilder.append("<");
                resultStrBuilder.append(f.getName());
                resultStrBuilder.append(":");
                
                String value = "";
                if (f.get(obj) != null) 
                    value = f.get(obj).toString();

                if("java.util.List".equals(f.getType().getName())){
                    if(value.length()<3){
                        value ="";
                    }else{
                        value=value.substring(1, value.length()-1);
                    }
                }
                resultStrBuilder.append(value).append(">");
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return resultStrBuilder.toString();
    }

}

/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output;


/**
 * 类FileWriterWrapper.java的实现描述：writer的封装
 * 
 * @author Richard.xu 2014年2月25日 下午2:04:53
 */
public interface FileWriterWrapper {

    
    public int getId();
    
    public void init();

    public void flush();

    public void close();

    public void reload();

    /**
     * 输出一行json文件
     */
    public void writeProduct(String data);

}

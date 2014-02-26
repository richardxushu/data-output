/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.cachedata;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.taotaosou.data.increase.ProductAllIncHandler;
import com.taotaosou.data.util.PooledHttpClient;

/**
 * 类BackCategoryCache.java的实现描述：后台类目号和类目名的缓存,定时更新
 * 
 * @author Richard.xu 2014年2月19日 上午11:13:02
 */
public class BackCategoryCache {

    private static final Logger              logger        = LoggerFactory.getLogger(ProductAllIncHandler.class);

    private String                           backCategoryUrl;

    private PooledHttpClient                 httpConnector;

    private volatile static Map<String, String>   categoryMap   = new HashMap<String, String>(3000);

    private ScheduledExecutorService         schThreadPool = Executors.newScheduledThreadPool(1);

    public void init() {
        reSetCategoryMap();
        schThreadPool.scheduleAtFixedRate(new Runnable() {

            public void run() {
                logger.info("start update the category cache");
                reSetCategoryMap();
            }
        }, 1, 1, TimeUnit.DAYS);
    }

    public synchronized void reSetCategoryMap() {
        String str = this.httpConnector.getURLContentAsStr(backCategoryUrl, "UTF-8", 30000);
        // 解析器
        JsonParser parse = new JsonParser();
        JsonElement element = parse.parse(str);
        // 对应搭配数组
        JsonArray array = element.getAsJsonArray();
        if (array != null && array.size() > 0) {
            for (JsonElement e : array) {
                JsonArray category = e.getAsJsonArray();
                int id = category.get(0).getAsInt();
                String name = category.get(1).getAsString();
                categoryMap.put(id + "", name);
            }
        }

    }
    
    public String getCategoryName(String backCategoryCode){
        return categoryMap.get(backCategoryCode);
    }

    public void close() {
        schThreadPool.shutdown();
    }

    
    /**
     * @return the backCategoryUrl
     */
    public String getBackCategoryUrl() {
        return backCategoryUrl;
    }

    
    /**
     * @param backCategoryUrl the backCategoryUrl to set
     */
    public void setBackCategoryUrl(String backCategoryUrl) {
        this.backCategoryUrl = backCategoryUrl;
    }

    
    /**
     * @return the httpConnector
     */
    public PooledHttpClient getHttpConnector() {
        return httpConnector;
    }

    
    /**
     * @param httpConnector the httpConnector to set
     */
    public void setHttpConnector(PooledHttpClient httpConnector) {
        this.httpConnector = httpConnector;
    }
    
    
}

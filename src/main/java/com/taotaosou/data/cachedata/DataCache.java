/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.cachedata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 类DataCache.java的实现描述：一些初始化要加载的数据
 * 1.tag的list
 * 2.百度类目对应的category_code缓存
 * 
 * 
 * @author Richard.xu 2014年1月17日 下午2:03:45
 */
public class DataCache {
    
    private String skirtsCodes;
    private String accessoriesCodes;
    private String shoesCodes;
    private String pantsCodes;
    private String jacketCodes;
    private String bagsCodes;
    
    private Set<String> tagSet;
    
    private Map<String,String> baiduTagMap = new HashMap<String,String>();
    
    
    public void init(){
        String[] skirtsArr =  skirtsCodes.split(",");
        String[] accessoriesArr =  accessoriesCodes.split(",");
        String[] shoesArr =  shoesCodes.split(",");
        String[] pantsArr =  pantsCodes.split(",");
        String[] jacketArr =  jacketCodes.split(",");
        String[] bagsArr =  bagsCodes.split(",");
        for(String code : skirtsArr){
            baiduTagMap.put(code, "裙子");
        }
        for(String code : accessoriesArr){
            baiduTagMap.put(code, "配饰");
        }
        for(String code : shoesArr){
            baiduTagMap.put(code, "鞋子");
        }
        for(String code : pantsArr){
            baiduTagMap.put(code, "裤子");
        }
        for(String code : jacketArr){
            baiduTagMap.put(code, "上衣");
        }
        for(String code : bagsArr){
            baiduTagMap.put(code, "包包");
        }
    }
    
    
    /**
     * @return the baiduTagMap
     */
    public Map<String, String> getBaiduTagMap() {
        return baiduTagMap;
    }


    
    /**
     * @param baiduTagMap the baiduTagMap to set
     */
    public void setBaiduTagMap(Map<String, String> baiduTagMap) {
        this.baiduTagMap = baiduTagMap;
    }


    /**
     * @return the skirtsCodes
     */
    public String getSkirtsCodes() {
        return skirtsCodes;
    }


    
    /**
     * @param skirtsCodes the skirtsCodes to set
     */
    public void setSkirtsCodes(String skirtsCodes) {
        this.skirtsCodes = skirtsCodes;
    }


    
    /**
     * @return the accessoriesCodes
     */
    public String getAccessoriesCodes() {
        return accessoriesCodes;
    }


    
    /**
     * @param accessoriesCodes the accessoriesCodes to set
     */
    public void setAccessoriesCodes(String accessoriesCodes) {
        this.accessoriesCodes = accessoriesCodes;
    }


    
    /**
     * @return the shoesCodes
     */
    public String getShoesCodes() {
        return shoesCodes;
    }


    
    /**
     * @param shoesCodes the shoesCodes to set
     */
    public void setShoesCodes(String shoesCodes) {
        this.shoesCodes = shoesCodes;
    }


    
    /**
     * @return the pantsCodes
     */
    public String getPantsCodes() {
        return pantsCodes;
    }


    
    /**
     * @param pantsCodes the pantsCodes to set
     */
    public void setPantsCodes(String pantsCodes) {
        this.pantsCodes = pantsCodes;
    }


    
    /**
     * @return the jacketCodes
     */
    public String getJacketCodes() {
        return jacketCodes;
    }


    
    /**
     * @param jacketCodes the jacketCodes to set
     */
    public void setJacketCodes(String jacketCodes) {
        this.jacketCodes = jacketCodes;
    }


    
    /**
     * @return the bagsCodes
     */
    public String getBagsCodes() {
        return bagsCodes;
    }


    
    /**
     * @param bagsCodes the bagsCodes to set
     */
    public void setBagsCodes(String bagsCodes) {
        this.bagsCodes = bagsCodes;
    }


    /**
     * @return the tagSet
     */
    public Set<String> getTagSet() {
        return tagSet;
    }

    
    /**
     * @param tagSet the tagSet to set
     */
    public void setTagSet(Set<String> tagSet) {
        this.tagSet = tagSet;
    }

}

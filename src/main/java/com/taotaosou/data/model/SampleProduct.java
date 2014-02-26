/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.model;

import java.util.ArrayList;
import java.util.List;

import com.taotaosou.common.uri.image.ImageURIBroker;
import com.taotaosou.data.cachedata.BackCategoryCache;
import com.taotaosou.data.cachedata.DataCache;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.output.strategy.FormatBehavior;
import com.taotaosou.data.output.strategy.JsonFormatBehavior;
import com.taotaosou.data.output.strategy.TszXmlFormatBehavior;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.data.util.PriceUtil;
import com.taotaosou.data.util.TempOp;

/**
 * 类SampleProduct.java的实现描述：样例输出类
 * @author Richard.xu 2014年1月11日 下午4:42:46
 */
public class SampleProduct implements BaseProduct{

    private long         proId;       // 淘淘搜商品ID
    private String       op;          // 操作，添加或者删除
    private String       htmlUrl;       // Detail页面的URL， http://gouwu.taotaosou.com/product-1266686180.html
    private String       htmlTitle;   // 页面标题，“商品标题”+“类目词”+“比价”（
    private String       proTitle;    
    private String       picUrl;      // 图片URL，淘淘搜图片地址
    private String       anchorText;  // 图片作为连接时的锚文字, 同proTitle
    private String       altText;     // 图片作为连接时的锚文字, 同proTitle
    private double       price;
    private List<String> tag;
    private String       category;
    private String       description; // 同htmlTitle
    
    

    public void convert(ProductLabel proLabel, TsearchClientProxy tsearchClientProxy, BackCategoryCache cateCache) {
        this.proId = proLabel.getId();
        this.op="add";//todo
        
        this.category = cateCache.getCategoryName(proLabel.getProductCategoryCode());
        if(this.category==null)
            this.category = "";
        
        this.htmlUrl="http://gouwu.taotaosou.com/product-"+proLabel.getId()+".html";
        this.htmlTitle = proLabel.getProductTitle()+"-"+this.category+"-比价"; 
        this.proTitle = proLabel.getProductTitle();
        
        this.picUrl = ImageURIBroker.getInstance().getURI(proLabel.getMainImagePath());
        this.anchorText = this.proTitle;
        this.altText = this.proTitle;
        
        // set the price
        int priceInt;
        if(proLabel.getPromoPrice() == null||proLabel.getPromoPrice() == -1||proLabel.getPromoPrice() ==0){
            priceInt = proLabel.getPrice();
        }else{
            priceInt=proLabel.getPromoPrice();
        }
        
        this.price = PriceUtil.formatPriceDouble(priceInt);
        //this.tag = tsearchClientProxy.getAnalyzedWords(proTitle);
        List<String> tagList = new ArrayList<String>();
        tagList.add(this.category);
        this.tag = tagList;//tag todo
        
        this.description = this.htmlTitle;
    }
    
    public void convert(ProductLabelPBDataMessage proLabel, TsearchClientProxy tsearchClientProxy, BackCategoryCache cateCache) {
        this.proId = proLabel.getId();
        this.op="add";//todo
        
        this.category = cateCache.getCategoryName(proLabel.getProductCategoryCode());
        if(this.category==null)
            this.category = "";

        this.htmlUrl="http://gouwu.taotaosou.com/product-"+proLabel.getId()+".html";
        
        this.htmlTitle = proLabel.getProductTitle()+"-"+this.category+"-比价"; //todo
        this.proTitle = proLabel.getProductTitle();
        
        this.picUrl = ImageURIBroker.getInstance().getURI(proLabel.getMainImagePath());
        this.anchorText = this.proTitle;
        this.altText = this.proTitle;
        
        // set the price
        int priceInt;
        if(proLabel.getPromoPrice() <= 0){
            priceInt = proLabel.getPrice();
        }else{
            priceInt=proLabel.getPromoPrice();
        }
        
        this.price = PriceUtil.formatPriceDouble(priceInt);
        //this.tag = tsearchClientProxy.getAnalyzedWords(proTitle);
        List<String> tagList = new ArrayList<String>();
        tagList.add(this.category);
        this.tag = tagList;//tag todo
        
        this.description = this.htmlTitle;
    }

    
    /**
     * @return the proId
     */
    public long getProId() {
        return proId;
    }

    
    /**
     * @param proId the proId to set
     */
    public void setProId(long proId) {
        this.proId = proId;
    }

    
    /**
     * @return the op
     */
    public String getOp() {
        return op;
    }

    
    /**
     * @param op the op to set
     */
    public void setOp(String op) {
        this.op = op;
    }

    
    /**
     * @return the htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    
    /**
     * @param htmlUrl the htmlUrl to set
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    
    /**
     * @return the htmlTitle
     */
    public String getHtmlTitle() {
        return htmlTitle;
    }

    
    /**
     * @param htmlTitle the htmlTitle to set
     */
    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    
    /**
     * @return the proTitle
     */
    public String getProTitle() {
        return proTitle;
    }

    
    /**
     * @param proTitle the proTitle to set
     */
    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    
    /**
     * @return the picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }

    
    /**
     * @param picUrl the picUrl to set
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    
    /**
     * @return the anchorText
     */
    public String getAnchorText() {
        return anchorText;
    }

    
    /**
     * @param anchorText the anchorText to set
     */
    public void setAnchorText(String anchorText) {
        this.anchorText = anchorText;
    }

    
    /**
     * @return the altText
     */
    public String getAltText() {
        return altText;
    }

    
    /**
     * @param altText the altText to set
     */
    public void setAltText(String altText) {
        this.altText = altText;
    }

    
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    
    /**
     * @return the tag
     */
    public List<String> getTag() {
        return tag;
    }

    
    /**
     * @param tag the tag to set
     */
    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    
    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    
    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /* (non-Javadoc)
     * @see com.taotaosou.data.model.BaseProduct#formatString(com.taotaosou.data.output.strategy.FormatBehavior)
     */
    @Override
    public String formatString(FormatBehavior behavior) {
        return behavior.format(this);
    }
    
    public String formatString() {
        FormatBehavior behavior = new JsonFormatBehavior();
        return this.formatString(behavior);
    }


}

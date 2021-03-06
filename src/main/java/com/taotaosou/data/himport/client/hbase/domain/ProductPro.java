/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.himport.client.hbase.domain;

import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;

/**
 * 类ProductPro.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年1月20日 下午2:22:57
 */
public class ProductPro {

    private Long   id;          // tts商品id
    private String sourceId;    // 源商品id
    private String sellerName;  // 卖家
    private String title;
    private String buy_url;
    private int    price;
    private String operation;   // test
    private String mainImgUrl;
    private String imagePath;
    private String categoryCode;

    public ProductPro() {

    }

    public ProductPro(ProductLabel productLabel) {
        this.id = productLabel.getId();
        this.sourceId = productLabel.getSourceProductId();
        this.sellerName = productLabel.getSellerName();
        this.title = productLabel.getProductTitle();
        this.price = productLabel.getPrice();
        this.imagePath = productLabel.getMainImagePath();
        this.mainImgUrl = productLabel.getMainImageUrl();
        this.categoryCode = productLabel.getProductCategoryCode();
    }

    public ProductPro(ProductLabelPBDataMessage plMsg) {
        this.id = plMsg.getId();
        this.sourceId = plMsg.getSourceProductId();
        this.sellerName = plMsg.getSellerName();
        this.title = plMsg.getProductTitle();
        this.price = plMsg.getPrice();
        this.imagePath = plMsg.getMainImagePath();
        this.mainImgUrl = plMsg.getMainImageUrl();
        this.categoryCode = plMsg.getProductCategoryCode();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the sourceId
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * @param sourceId the sourceId to set
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * @return the sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName the sellerName to set
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the buy_url
     */
    public String getBuy_url() {
        return buy_url;
    }

    /**
     * @param buy_url the buy_url to set
     */
    public void setBuy_url(String buy_url) {
        this.buy_url = buy_url;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the mainImgUrl
     */
    public String getMainImgUrl() {
        return mainImgUrl;
    }

    /**
     * @param mainImgUrl the mainImgUrl to set
     */
    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the categoryCode
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * @param categoryCode the categoryCode to set
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

}

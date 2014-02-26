/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.model;

import java.util.ArrayList;
import java.util.List;

import com.taotaosou.data.cachedata.DataCache;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.data.util.PriceUtil;
import com.taotaosou.search.client.model.ProductDetail;
import com.taotaosou.search.client.service.SearchClient;

/**
 * 类ProductBaidu.java的实现描述：baidu图片输出Bean
 * 
 * @author Richard.xu 2014年1月11日 下午4:42:46 // goodsId ：商品ID // goodsType:商品类型，取值范围（上衣、裤子、裙子、鞋子、包包、配饰）。具体见与主站类目映射见附件。 //
 * column:类目，固定为服饰 // tag:图片tag,支持多个，使用标题分词，分词级别为2。 // title:商品名称 // content:描述 // obj_url:商品原图地址 //
 * from_url：主站商品detail地址。 // buy_url: 主站商品detail地址。 // site:电商平台域名，固定为taobao.com // pic_type:图片结构，固定为0 //
 * price:价格，价格不能为0，纯数字，保留1位小数点，单位是“元“，不带金额单位 // num:固定为99999 // user_name: tts2012731
 */
public class ProductBaidu {

    private String       goodsId;
    private String       goodsType;
    private String       column    = "服饰";
    private List<String> tag;
    private String       title;
    private String       content="";
    private List<String> obj_url;
    private String       from_url;
    private String       buy_url;
    private String       site      = "taobao.com";
    private int          pic_type  = 0;
    private double       price;
    private int          num       = 999;
    private String       user_name = "tts2012731";
    private String       time      = System.currentTimeMillis() + "";

    public ProductBaidu convert(ProductLabelPBDataMessage proLabel, TsearchClientProxy tsearchClientProxy,
                                DataCache dataCache) {
        this.goodsId = proLabel.getSourceProductId();

        this.goodsType = dataCache.getBaiduTagMap().get(proLabel.getProductCategoryCode());
        this.title = proLabel.getProductTitle();
        this.tag = tsearchClientProxy.getAnalyzedWords(title);
        List<String> imgList = new ArrayList<String>();
        imgList.add(proLabel.getMainImagePath());
        this.obj_url = imgList;
        this.from_url = "http://www.taotaosou.com/product-" + proLabel.getId()
                        + "--.html?utm_source=baidupic&utm_medium=pic&utm_campaign=baidu";
        this.buy_url = this.from_url;

        // set the price
        int priceInt = proLabel.getPromoPrice();
        if (priceInt == -1 || priceInt == 0) {
            priceInt = proLabel.getPrice();
        }
        this.price = PriceUtil.formatPriceDouble(priceInt);

        return this;
    }

    /**
     * @return the goodsId
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId the goodsId to set
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return the goodsType
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * @param goodsType the goodsType to set
     */
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
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
     * @return the obj_url
     */
    public List<String> getObj_url() {
        return obj_url;
    }

    /**
     * @param obj_url the obj_url to set
     */
    public void setObj_url(List<String> obj_url) {
        this.obj_url = obj_url;
    }

    /**
     * @return the from_url
     */
    public String getFrom_url() {
        return from_url;
    }

    /**
     * @param from_url the from_url to set
     */
    public void setFrom_url(String from_url) {
        this.from_url = from_url;
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
     * @return the site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return the pic_type
     */
    public int getPic_type() {
        return pic_type;
    }

    /**
     * @param pic_type the pic_type to set
     */
    public void setPic_type(int pic_type) {
        this.pic_type = pic_type;
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
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}

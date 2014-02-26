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
 * 类SampleProduct.java的实现描述：360输出bean <op> 必选 add 增加； del 为删除 <pic_url> 必选 图片的 url ，对应 picUrl <pic_from_url> 必选 网页 url
 * ，对应 htmlUrl <pic_title> 必选 图片标题，对应 protitle <pic_type> 必选 商品分类 , 如： “ 短外套 ”“ 西装 ” ，对应 tag <pic_tag> 必选 商品标签 ( 可选 )
 * ，对应 tag <pic_price> 必选 商品价格，对应 price <pic_desc> 必选 单图描述【没有则于 title 相同或为空】单图描述，对应 description < update_time > 必选
 * 本网站的生成时间，格式： yyyy mm dd hhmm 如 : 201 301012359 ，内容为空，字段保留 备注 文本内容格式： <![CDATA[ 文本内容 ]]>: 若此项无文本内容，则为空格 单个 XML 不超过 100
 * 万图片
 * 
 * @author Richard.xu 2014年1月11日 下午4:42:46
 */
public class Product360 implements BaseProduct {

    private int            flag;                               // 操作，添加或者删除 : 0 增加； 1 更新； 2 删除
    private String         pic_url;                            // 图片url
    private String         page_url;                           // 网页 url ，对应 htmlUrl
    private String         pic_title;                          // 图片标题，对应 protitle
    private double         price;
    private List<String>   pic_tag;                            // 商品分类 , 如： “ 短外套 ”“ 西装 ” ，对应 tag
    private String         pic_type;                           // 商品分类 , 如： “ 短外套 ”“ 西装 ” ，对应 tag
    private String         pic_desc;                           // 同htmlTitle
    private String         update_time;                        // 同htmlTitle
    private int            onsale;                             // 0下架, 1上架
    
    public void convert(ProductLabel proLabel, TsearchClientProxy tsearchClientProxy, BackCategoryCache cateCache) {
        this.flag = 0;// todo

        this.pic_type = cateCache.getCategoryName(proLabel.getProductCategoryCode());

        this.pic_url = ImageURIBroker.getInstance().getURI(proLabel.getMainImagePath());
        this.page_url = "http://gouwu.taotaosou.com/product-" + proLabel.getId() + ".html";
        this.pic_title = proLabel.getProductTitle() + "-" + this.pic_type + "-比价";
        // set the price
        int priceInt;
        if (proLabel.getPromoPrice() == null || proLabel.getPromoPrice() == -1 || proLabel.getPromoPrice() == 0) {
            priceInt = proLabel.getPrice();
        } else {
            priceInt = proLabel.getPromoPrice();
        }

        this.price = PriceUtil.formatPriceDouble(priceInt);

        List<String> tagList = new ArrayList<String>();
        tagList.add(this.pic_type);
        this.pic_tag = tagList;

        this.onsale = 1;// todo in the second version
        this.pic_desc = this.pic_title;
    }

    /*
     * (non-Javadoc)
     * @see com.taotaosou.data.model.BaseProduct#convert(com.taotaosou.data.mq.proto.ProductLabelDataMessage.
     * ProductLabelPBDataMessage, com.taotaosou.data.tsearch.TsearchClientProxy,
     * com.taotaosou.data.cachedata.BackCategoryCache)
     */
    @Override
    public void convert(ProductLabelPBDataMessage proLabel, TsearchClientProxy tsearchClientProxy,
                        BackCategoryCache cateCache) {
        // TODO Auto-generated method stub

    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * @return the page_url
     */
    public String getPage_url() {
        return page_url;
    }

    /**
     * @param page_url the page_url to set
     */
    public void setPage_url(String page_url) {
        this.page_url = page_url;
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
     * @return the pic_url
     */
    public String getPic_url() {
        return pic_url;
    }

    /**
     * @param pic_url the pic_url to set
     */
    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    /**
     * @return the pic_title
     */
    public String getPic_title() {
        return pic_title;
    }

    /**
     * @param pic_title the pic_title to set
     */
    public void setPic_title(String pic_title) {
        this.pic_title = pic_title;
    }

    /**
     * @return the pic_tag
     */
    public List<String> getPic_tag() {
        return pic_tag;
    }

    /**
     * @param pic_tag the pic_tag to set
     */
    public void setPic_tag(List<String> pic_tag) {
        this.pic_tag = pic_tag;
    }

    /**
     * @return the pic_type
     */
    public String getPic_type() {
        return pic_type;
    }

    /**
     * @param pic_type the pic_type to set
     */
    public void setPic_type(String pic_type) {
        this.pic_type = pic_type;
    }

    /**
     * @return the pic_desc
     */
    public String getPic_desc() {
        return pic_desc;
    }

    /**
     * @param pic_desc the pic_desc to set
     */
    public void setPic_desc(String pic_desc) {
        this.pic_desc = pic_desc;
    }

    /**
     * @return the update_time
     */
    public String getUpdate_time() {
        return update_time;
    }

    /**
     * @param update_time the update_time to set
     */
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    /**
     * @return the onsale
     */
    public int getOnsale() {
        return onsale;
    }

    /**
     * @param onsale the onsale to set
     */
    public void setOnsale(int onsale) {
        this.onsale = onsale;
    }
    /* (non-Javadoc)
     * @see com.taotaosou.data.model.BaseProduct#formatString(com.taotaosou.data.output.strategy.FormatBehavior)
     */
    @Override
    public String formatString(FormatBehavior behavior) {
        return behavior.format(this);
    }
    
    public String formatString() {
        FormatBehavior behavior = new TszXmlFormatBehavior();
        return this.formatString(behavior);
    }

}

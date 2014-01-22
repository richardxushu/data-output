/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.model;

import java.util.List;

import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.tts.zsm.model.FindProductVO;
import com.taotaosou.tts.zsm.model.QandADetailBean;

/**
 * 类StarFind.java的实现描述：明星问答内容输出
 *  user_name: tts2012731
    pic_type ：图片类型，固定为0
    column:类目，固定为服饰。
    from_url:问答detail页面。
    obj_url:问题的原始图，尺寸必须大于400*400
    
    title:答案商品标题
    content ：第一个答案商品标题。
    tag:第一个答案商品标题使用2级分词。
    goodIds ：问题第一个答案的商品ID 
    time: 时间戳 格式1271850067
    
 * @author Richard.xu 2014年1月13日 下午5:23:10
 */
public class StarFind {
    
    private String user_name = "tts2012731";
    private int pic_type  = 0;
    private String column="服饰";
    private String from_url;
    private String content="";
    private List<String> tag;
    private List<String> obj_url;
    private String title;
    private String time;
    private List<String> goodIds;
    
    
    
    
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
     * @return the time
     */
    public String getTime() {
        return time;
    }
    
    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }
    
    /**
     * @return the goodsId
     */
    public List<String> getGoodIds() {
        return goodIds;
    }
    
    /**
     * @param goodsId the goodsId to set
     */
    public void setGoodIds(List<String> goodIds) {
        this.goodIds = goodIds;
    }
    
    
    

}

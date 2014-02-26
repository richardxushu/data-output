/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.dataBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotaosou.data.cachedata.DataCache;
import com.taotaosou.data.himport.client.hbase.domain.ProductPro;
import com.taotaosou.data.model.ProductBaidu;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.data.util.PriceUtil;

/**
 * 类OutputDataBuilder.java的实现描述：TODO 类实现描述 
 * @author Richard.xu 2014年1月21日 上午11:48:51
 */
public class OutputDataBuilder {
    
    @Autowired
    private DataCache           dataCache;
    
    @Autowired
    private TsearchClientProxy  tsearchClientProxy;
    
    
    public ProductBaidu createBaiduProduct(ProductPro product){
        ProductBaidu proBaidu = new ProductBaidu();
        proBaidu.setGoodsId(product.getSourceId());
        proBaidu.setGoodsType(dataCache.getBaiduTagMap().get(product.getCategoryCode()));
        proBaidu.setTitle(product.getTitle());
        proBaidu.setTag(tsearchClientProxy.getAnalyzedWords(product.getTitle()));
        List<String> imgList = new ArrayList<String>();
        imgList.add(product.getImagePath());
        proBaidu.setObj_url(imgList);
        proBaidu.setFrom_url("http://www.taotaosou.com/product-" + product.getId()
                        + "--.html?utm_source=baidupic&utm_medium=pic&utm_campaign=baidu");
        proBaidu.setBuy_url(proBaidu.getFrom_url());
        proBaidu.setPrice(PriceUtil.formatPriceDouble(product.getPrice()));

        return proBaidu;
    }
    
//    public ProductBaidu createBaiduProduct(ProductPro product){
//        ProductBaidu proBaidu = new ProductBaidu();
//        proBaidu.setGoodsId(product.getSourceId());
//        proBaidu.setGoodsType(dataCache.getBaiduTagMap().get(product.getCategoryCode()));
//        proBaidu.setTitle(product.getTitle());
//        proBaidu.setTag(tsearchClientProxy.getAnalyzedWords(product.getTitle()));
//        List<String> imgList = new ArrayList<String>();
//        imgList.add(product.getImagePath());
//        proBaidu.setObj_url(imgList);
//        proBaidu.setFrom_url("http://www.taotaosou.com/product-" + product.getId()
//                        + "--.html?utm_source=baidupic&utm_medium=pic&utm_campaign=baidu");
//        proBaidu.setBuy_url(proBaidu.getFrom_url());
//        proBaidu.setPrice(PriceUtil.formatPriceDouble(product.getPrice()));
//
//        return proBaidu;
//    }

}

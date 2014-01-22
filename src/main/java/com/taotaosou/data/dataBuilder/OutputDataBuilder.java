///*
// * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
// * confidential and proprietary information of Taotaosou.com ("Confidential
// * Information"). You shall not disclose such Confidential Information and shall
// * use it only in accordance with the terms of the license agreement you entered
// * into with Taotaosou.com.
// */
//package com.taotaosou.data.dataBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.taotaosou.data.himport.client.hbase.domain.ProductPro;
//import com.taotaosou.data.model.ProductBaidu;
//import com.taotaosou.data.util.PriceUtil;
//
///**
// * 类OutputDataBuilder.java的实现描述：TODO 类实现描述 
// * @author Richard.xu 2014年1月21日 上午11:48:51
// */
//public class OutputDataBuilder {
//    
//    public ProductBaidu createBaiduProduct(ProductPro product){
//        ProductBaidu proBaidu = new ProductBaidu();
//        proBaidu.set
//        
//        
//        
//        
//        
//        this.goodsId = proLabel.getSourceProductId();
//
//        this.goodsType = dataCache.getBaiduTagMap().get(proLabel.getProductCategoryCode());
//        this.title = proLabel.getProductTitle();
//        this.tag = tsearchClientProxy.getAnalyzedWords(title);
//        List<String> imgList = new ArrayList<String>();
//        imgList.add(proLabel.getMainImagePath());
//        this.obj_url = imgList;
//        this.mainImgUrl = proLabel.getMainImageUrl(); 
//        this.from_url = "http://www.taotaosou.com/product-" + proLabel.getId()
//                        + "--.html?utm_source=baidupic&utm_medium=pic&utm_campaign=baidu";
//        this.buy_url = this.from_url;
//
//        // set the price
//        int priceInt = proLabel.getPromoPrice();
//        if (priceInt == -1 || priceInt == 0) {
//            priceInt = proLabel.getPrice();
//        }
//        this.price = PriceUtil.formatPriceDouble(priceInt);
//
//        this.operation = proLabel.getOperation();
//
//        return this;
//    }
//
//}

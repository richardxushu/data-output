/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.increase;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotaosou.common.pool.IBatchExecutor;
import com.taotaosou.data.cachedata.DataCache;
import com.taotaosou.data.himport.client.mq.ProductLabelNotifyClient;
import com.taotaosou.data.model.ProductBaidu;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.output.JsonFileManager;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.search.client.service.SearchClient;

/**
 * 类ProductAIncHandler.java的实现描述：A库商品增量处理
 * 
 * @author Richard.xu 2014年1月11日 上午10:02:52
 */
public class ProductAIncHandler implements IBatchExecutor<ProductLabelPBDataMessage> {

    private final static Gson   GSON   = new GsonBuilder().disableHtmlEscaping().create();

    private static final Logger logger = LoggerFactory.getLogger(ProductAIncHandler.class);

    @Resource
    private JsonFileManager     jsonFileManager;

    @Autowired
    private TsearchClientProxy  tsearchClientProxy;

    @Autowired
    private DataCache           dataCache;

    /*
     * (non-Javadoc)
     * @see com.taotaosou.common.pool.IBatchExecutor#execute(java.util.List)
     */
    @Override
    public void execute(List<ProductLabelPBDataMessage> records) {
        for (ProductLabelPBDataMessage pl : records) {
            ProductBaidu product = new ProductBaidu();
            product.convert(pl, tsearchClientProxy, dataCache);
            String data = GSON.toJson(product);
            jsonFileManager.writeProduct(data);
        }
    }

}

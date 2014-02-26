/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.increase;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotaosou.common.pool.IBatchExecutor;
import com.taotaosou.data.constants.DataConstants;
import com.taotaosou.data.himport.client.mq.ProductLabelNotifyClient;
import com.taotaosou.data.model.ProductBaidu;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.output.FileManager;
import com.taotaosou.data.output.JsonFileManager;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.search.client.service.SearchClient;

/**
 * 类ProductAIncHandler.java的实现描述：全库商品增量处理
 * 
 * @author Richard.xu 2014年1月11日 上午10:02:52
 */
public class ProductAllIncHandler implements IBatchExecutor<ProductLabelPBDataMessage> {

    private final static Gson   GSON   = new GsonBuilder().disableHtmlEscaping().create();

    private static final Logger logger = LoggerFactory.getLogger(ProductAllIncHandler.class);

    @Resource(name = "jsonFileManager")
    private FileManager         fileManager;

    @Autowired
    private TsearchClientProxy  tsearchClientProxy;

    /*
     * (non-Javadoc)
     * @see com.taotaosou.common.pool.IBatchExecutor#execute(java.util.List)
     */
    /*
     * (non-Javadoc)
     * @see com.taotaosou.common.pool.IBatchExecutor#execute(java.util.List)
     */
    @Override
    public void execute(List<ProductLabelPBDataMessage> records) {
        // filter todo...先简单的filter一下，后面这部分可以抽出来
        List<ProductLabelPBDataMessage> baiduPicList = new ArrayList<ProductLabelPBDataMessage>();
        List<ProductLabelPBDataMessage> tszList = new ArrayList<ProductLabelPBDataMessage>();

        for (ProductLabelPBDataMessage pl : records) {
            // logger.info("pl.getId()-----------------------------: " + pl.getId());
            // logger.info("pl.getOperation(): " + pl.getOperation());
            // logger.info("pl.getOnsaleFlag: " + pl.getOnsaleFlag());

            // if (pl.getIntegrity() > 4 && pl.getUpdatedFields() != null
            // && pl.getUpdatedFields().indexOf("mainImagePath") != -1) {

            if (pl.getIntegrity() < 6) {

                logger.info("pl.getId()-----------------------------: " + pl.getId());
                logger.info("pl.getIntegrity(): " + pl.getIntegrity());
                logger.info("pl.getOperation(): " + pl.getOperation());
                logger.info("pl.getOnsaleFlag: " + pl.getOnsaleFlag());
                logger.info("pl.getUpdatedFields: " + pl.getUpdatedFields());

                baiduPicList.add(pl);
            }
        }

        fileManager.processNotify(baiduPicList, DataConstants.BAIDU_INC_WRITER_ID);
        // fileManager.processNotify(tszList, DataConstants.TSZ_INC_WRITER_ID);
    }

}

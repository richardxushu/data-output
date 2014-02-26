/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotaosou.common.lang.DateUtil;
import com.taotaosou.data.cachedata.BackCategoryCache;
import com.taotaosou.data.constants.DataConstants;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.model.BaseProduct;
import com.taotaosou.data.model.ProductBaidu;
import com.taotaosou.data.model.SampleProduct;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.tsearch.TsearchClientProxy;

/**
 * 类JsonMananger.java的实现描述：以json格式输出文件
 * 
 * @author Richard.xu 2014年1月11日 上午11:41:11
 */
public class JsonFileManager implements FileManager {

    private static final Logger             logger    = Logger.getLogger(JsonFileManager.class);

    private final static Gson               GSON      = new GsonBuilder().disableHtmlEscaping().create();

    @Autowired
    private TsearchClientProxy              tsearchClientProxy;

    @Autowired
    private BackCategoryCache               backCategoryCache;

    // ioc writer, todo... 以后多起来可以用db来配置, 不然增加一个writer就要改代码
    @Autowired
    private FileWriterWrapper               baiduAllWriter;
    @Autowired
    private FileWriterWrapper               baiduIncWriter;
    @Autowired
    private FileWriterWrapper               tszIncWriter;

    private BaseProduct                     product;

    private Map<Integer, FileWriterWrapper> writerMap = new ConcurrentHashMap<Integer, FileWriterWrapper>();

    public void setProduct(BaseProduct product) {
        this.product = product;
    }

    public synchronized void init() {
        writerMap.put(baiduAllWriter.getId(), baiduAllWriter);
        writerMap.put(baiduIncWriter.getId(), baiduIncWriter);
        writerMap.put(tszIncWriter.getId(), tszIncWriter);
    }

    public void flush() {
        // try {
        // proWriter.flush();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

    }

    public void close() {
        // try {
        // if (proWriter != null) proWriter.close();
        // } catch (IOException e) {
        // logger.error("close error: ", e);
        // }
    }

    /*
     * (non-Javadoc)
     * @see com.taotaosou.data.output.FileManager#process(java.util.List)
     */
    public void process(List<ProductLabel> plList, Integer writerId) {
        try {
            FileWriterWrapper writer = writerMap.get(writerId);
            for (ProductLabel proLabel : plList) {
                if (proLabel.getMainImagePath() == null || proLabel.getMainImagePath().equals("")) continue;

                this.product.convert(proLabel, tsearchClientProxy, backCategoryCache);
                String proData = this.product.formatString();
                writer.writeProduct(proData);
            }
            writer.flush();
        } catch (Exception e) {
            logger.error("process data error: ", e);
        }
    }

    public synchronized void processNotify(List<ProductLabelPBDataMessage> plbList, Integer writerId) {
        try {
            FileWriterWrapper writer = writerMap.get(writerId);

            for (ProductLabelPBDataMessage pl : plbList) {
                this.product.convert(pl, tsearchClientProxy, backCategoryCache);
                String proData = this.product.formatString();
                writer.writeProduct(proData);
            }
            writer.flush();
        } catch (Exception e) {
            logger.error("process data error: ", e);
        }
    }

    /**
     * 按小时来重新加载writer
     */
    public synchronized void reloadIncWriter() {
        writerMap.get(DataConstants.BAIDU_INC_WRITER_ID).reload();
        // writerMap.get(DataConstants.TSZ_INC_WRITER_ID).reload();
    }

    /*
     * (non-Javadoc)
     * @see com.taotaosou.data.output.FileManager#reloadWriter(java.lang.Integer)
     */
    @Override
    public void reloadWriter(Integer writerId) {
        // TODO Auto-generated method stub

    }

}

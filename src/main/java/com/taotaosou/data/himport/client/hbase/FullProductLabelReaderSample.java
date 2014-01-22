/**
 * Copyright ©2011-2013, TAOTAOSOU. All right reserved. File: FullProductLabelReaderSample.java Package:
 * com.taotaosou.tsearch.index.data.full Creator: <a href="mailto:lanxin.liao@gmail.com">lansine</a> Since: 2013-8-19
 * 下午4:13:49
 */

package com.taotaosou.data.himport.client.hbase;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taotaosou.data.client.hbase.BatchDataProcessCallBack;
import com.taotaosou.data.client.hbase.HBaseClient;
import com.taotaosou.data.client.hbase.util.TableExporter;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;

/**
 * 全量读取器
 * 
 * @author lanxin.liao
 * @version 2013-8-19 下午4:13:49
 */
public final class FullProductLabelReaderSample implements BatchDataProcessCallBack<ProductLabel> {

    /**
     * Logger for this class
     */
    private static final Logger         logger              = LoggerFactory.getLogger(FullProductLabelReaderSample.class);

    private static final int            THREADPOOL_WAITTIME = 5;                                                          // 单位:小时
    private static final int            DEFAULT_BATCH_SIZE  = 1000;

    @Resource(name = "productLabelXMLHandler")
    private ProductLabelHandler         handler;

    /**
     * 表导出工具
     */
    private TableExporter<ProductLabel> exporter            = null;

    private String                      tableName           = null;

    private String                      familyName          = null;

    private int                         batchSize           = DEFAULT_BATCH_SIZE;

    private HBaseClient                 hbaseClient;

    @Override
    public void process(List<ProductLabel> objects) {
        handler.handle(objects);
    }

    public void init() {
        logger.info("begin to init exporter...");
        exporter = new TableExporter<ProductLabel>(tableName, familyName, hbaseClient);
    }

    public void fullRead() {
        long t1 = System.currentTimeMillis();
        handler.start();

        logger.info("begin to full read...");
        exporter.export(this, ProductLabel.class, THREADPOOL_WAITTIME);
        handler.finish();
        long t2 = System.currentTimeMillis();
        logger.info("finish export cost time: " + (t2-t1));
        
        logger.info("finish export. ");
    }

    /**
     * @param tableName tableName
     */

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param familyName familyName
     */

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @param hbaseClient hbaseClient
     */

    public void setHbaseClient(HBaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    @Override
    public int getBatchSize() {
        return batchSize;
    }

    @Override
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * @param handler handler
     */

    public void setHandler(ProductLabelHandler handler) {
        this.handler = handler;
    }

}

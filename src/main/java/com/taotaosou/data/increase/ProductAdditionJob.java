/**
 * Taotaosou.com Inc. Copyright (c) 2010-2012 All Rights Reserved.
 */
package com.taotaosou.data.increase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.taotaosou.common.pool.DelayExecuteBuffer;
import com.taotaosou.data.himport.client.mq.ProductLabelNotifyClient;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.output.XMLFileManager;

/**
 * 类ProductAIncHandler.java的实现描述：mq client定时任务
 * 
 * @author Richard.xu 2014年1月11日 上午10:02:52
 */

public class ProductAdditionJob {

    private static final Logger      logger        = Logger.getLogger(ProductAdditionJob.class);

    // 全库
    @Autowired
    @Qualifier("productLabelClient")
    private ProductLabelNotifyClient productLabelClient;
    // AB库
    @Autowired
    @Qualifier("smartProductLabelClient")
    private ProductLabelNotifyClient smartProductLabelClient;

    private Executor                 executor      = Executors.newFixedThreadPool(2);

    private boolean                  isInitSuccess = false;

    private boolean                  open          = true;

    // @Resource
    // public TopicStatisticsManager topicStatManager;

    /**
     * @return the open
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open the open to set
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Resource
    private DelayExecuteBuffer<ProductLabelPBDataMessage> productABuffer;

    @Resource
    private DelayExecuteBuffer<ProductLabelPBDataMessage> productAllBuffer;

    public void init() {
        if(!this.isOpen())
            return;
        
        logger.info("正在进行ProductAcceptor的初始化工作");
        try {
            productLabelClient.init();
            logger.info("全库增量client初始化完毕");
            smartProductLabelClient.init();
            logger.info("优质库增量client初始化完毕");
            isInitSuccess = true;
        } catch (JMSException e) {
            logger.error("ProductAcceptor启动出现错误，不能进行增量!", e);
            isInitSuccess = false;
            throw new IllegalArgumentException("T-Notify Product Client初始化出错");
        }
    }

    public void start() {
        if(!this.isOpen())
            return;
        
        if (null == productLabelClient || null == smartProductLabelClient || !isInitSuccess) throw new IllegalArgumentException(
                                                                                                                                "T-Notify ProductLabel Client初始化出错");
        logger.info("商品增量更新信息程序启动成功，开始获取商品增量数据");
        // 全库增量
        executor.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    ProductLabelPBDataMessage productLabelPB = null;
                    try {
                        productLabelPB = productLabelClient.collectMessage();
                        if (null == productLabelPB) {
                            logger.warn("[ALL] ProductLabelPB is NULL!!!");
                            return;
                        } else if (productLabelPB.getOperation() != null && productLabelPB.getOperation().length() >= 1) {
                            // logger.info(message);
                            // 将mq中的增量信息进行处理。
                            productAllBuffer.add(productLabelPB);

                        } else {
                            logger.warn("[AB] Operation is Error!!!");
                            return;
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                        return;
                    }
                    // 将mq中的增量信息进行处理。
                    // productAdditionHandler.addReadyToHandlerProduct(productLabelPB);
                }
            }
        });
        // 优质商品增量
        executor.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    ProductLabelPBDataMessage productLabelPB = null;

                    try {
                        productLabelPB = smartProductLabelClient.collectMessage();
                        if (null == productLabelPB) {
                            logger.warn("[AB] ProductLabelPB is NULL!!!");
                            return;
                        } else if (productLabelPB.getOperation() != null && productLabelPB.getOperation().length() >= 1) {

                            // 将mq中的增量信息进行处理。
                            // productABuffer.add(productLabelPB);

                        } else {
                            logger.warn("[AB] Operation is Error!!!");
                            return;
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                        return;
                    }

                }
            }
        });
    }

}

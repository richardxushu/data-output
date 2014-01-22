///**
// * Taotaosou.com Inc.
// * Copyright (c) 2010-2012 All Rights Reserved.
// */
//package com.taotaosou.tsearch.index.data.increase;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//import javax.annotation.Resource;
//import javax.jms.JMSException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import com.taotaosou.data.himport.client.mq.ProductLabelNotifyClient;
//import com.taotaosou.data.increase.ProductIncreaseJob;
//import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
//
///**
// * @author ChaoKai Wen email:regis.wen@taotaosou.com
// * @version
// * @since Ver 1.1
// * @Date 2012-5-17
// */
//public class ProductAdditionAcceptor implements Runnable {
//    
//    private static final Logger logger = LoggerFactory.getLogger(ProductIncreaseJob.class);
//
//
//    // 全库
//    @Autowired
//    @Qualifier("productLabelClient")
//    private ProductLabelNotifyClient productLabelClient;
//    // AB库
//    @Autowired
//    @Qualifier("smartProductLabelClient")
//    private ProductLabelNotifyClient smartProductLabelClient;
//
//    @Resource
//    private ProductAdditionHandler   productAdditionHandler;
//
//    private Executor                 executor      = Executors.newFixedThreadPool(2);
//
//    private boolean                  isInitSuccess = false;
//
////    @Resource
////    public TopicStatisticsManager    topicStatManager;
//
//    public void init() {
//        logger.info("正在进行ProductAcceptor的初始化工作");
//        try {
//            productLabelClient.init();
//            logger.info("全库增量client初始化完毕");
//            smartProductLabelClient.init();
//            logger.info("优质库增量client初始化完毕");
//            isInitSuccess = true;
//        } catch (JMSException e) {
//            logger.error("ProductAcceptor启动出现错误，不能进行增量!", e);
//            isInitSuccess = false;
//            throw new IllegalArgumentException("T-Notify Product Client初始化出错");
//        }
//    }
//
//    @Override
//    public void run() {
//        if (null == productLabelClient || null == smartProductLabelClient || !isInitSuccess)
//            throw new IllegalArgumentException("T-Notify ProductLabel Client初始化出错");
//        logger.info("商品增量更新信息程序启动成功，开始获取商品增量数据");
//        // 全库增量
//        executor.execute(new Runnable() {
//
//            @Override
//            public void run() {
//                while (true) {
//                    ProductLabelPBDataMessage productLabelPB = null;
//                    try {
//                        productLabelPB = productLabelClient.collectMessage();
//                        if (null == productLabelPB) {
//                            logger.warn("[ALL] ProductLabelPB is NULL!!!");
//                            return;
//                        } else {
//                            logger.warn("[ALL] Operation is Error!!!");
//                            return;
//                        }
//                    } catch (Exception e) {
//                        logger.error("", e);
//                        return;
//                    }
//                    // 将mq中的增量信息进行处理。
//                    productAdditionHandler.addReadyToHandlerProduct(productLabelPB);
//                }
//            }
//        });
//        // 优质商品增量
//        executor.execute(new Runnable() {
//
//            @Override
//            public void run() {
//                while (true) {
//                    ProductLabelPBDataMessage productLabelPB = null;
//                    try {
//                        productLabelPB = smartProductLabelClient.collectMessage();
//                        if (null == productLabelPB) {
//                            logger.warn("[AB] ProductLabelPB is NULL!!!");
//                            return;
//                        } else if (productLabelPB.getOperation() != null
//                                   && productLabelPB.getOperation().length() >= 1) {
//                            topicStatManager.statProAB.incSendCount(productLabelPB.getOperation()
//                                .charAt(0));
//                            topicStatManager.statProAB.incrSendCountToday(productLabelPB
//                                .getTimestamp(), productLabelPB.getOperation().charAt(0));
//                            //
//                            if (topicStatManager.statProAB.getFirstMessageTime() < 0)
//                                topicStatManager.statProAB.setFirstMessageTime(productLabelPB
//                                    .getTimestamp());
//                            topicStatManager.statProAB.setLatestMessageTime(productLabelPB
//                                .getTimestamp());
//                        } else {
//                            logger.warn("[AB] Operation is Error!!!");
//                            return;
//                        }
//
//                    } catch (Exception e) {
//                        logger.error("", e);
//                        return;
//                    }
//                    // 将mq中的增量信息进行处理。
//                    productAdditionHandler.addReadyToHandlerProduct(productLabelPB);
//                }
//            }
//        });
//    }
//}

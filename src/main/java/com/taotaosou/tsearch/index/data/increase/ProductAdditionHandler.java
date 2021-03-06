///**
// * Taotaosou.com Inc.
// * Copyright (c) 2010-2012 All Rights Reserved.
// */
//package com.taotaosou.tsearch.index.data.increase;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.taotaosou.common.core.CollectionUtil;
//import com.taotaosou.common.core.able.AbstractLogable;
//import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
//import com.taotaosou.tsearch.common.message.IndexMessage.IndexIncreseRequest;
//import com.taotaosou.tsearch.common.message.Module.IncreaseIndexProductInfo;
//import com.taotaosou.tsearch.common.message.Module.IndexProductInfo;
//import com.taotaosou.tsearch.index.data.manager.MessageSendManager;
//import com.taotaosou.tsearch.index.data.util.ProductUtil;
//
///**
// *
// * @author   ChaoKai Wen
// *           email:regis.wen@taotaosou.com
// * @version  
// * @since    Ver 1.1
// * @Date     2012-5-18
// */
//public class ProductAdditionHandler extends AbstractLogable implements Runnable {
//
//    private LinkedBlockingQueue<ProductLabelPBDataMessage> needToHandlers = null;
//
//    private Integer queueSize = 10000;
//
//    private boolean DEBUG = logger.isDebugEnabled();
//    @Resource
//    private MessageSendManager messageSendManager;
//
//    private AtomicInteger handleringCount = new AtomicInteger(0);
//
//    public void init() {
//        needToHandlers = new LinkedBlockingQueue<ProductLabelPBDataMessage>(queueSize);
//    }
//
//    /**
//     * 添加新的商品到待处理队列中
//     * 
//     * @param product 商品的增量信息
//     * */
//    public void addReadyToHandlerProduct(ProductLabelPBDataMessage product) {
//        if (DEBUG) {
//            logger.debug("ProductHandler中新接受到新的商品增量消息,添加进待处理队列中,增量商品的ID号是[{}]", product.getId());
//        }
//        try {
//            needToHandlers.put(product);
//        } catch (InterruptedException e) {
//            logger.error("", e);
//        }
//    }
//
//    @Override
//    public void run() {
//        //        logger.info("开始处理商品队列中待处理的增量消息");
//        handleringCount.incrementAndGet();
//        try {
//            LinkedList<ProductLabelPBDataMessage> readyToHandlers = new LinkedList<ProductLabelPBDataMessage>();
//            needToHandlers.drainTo(readyToHandlers);
//            if (CollectionUtil.isEmpty(readyToHandlers)) {
//                return;
//            }
//            try {
//                long readStartTime = System.currentTimeMillis();
//
//                // 转换成PB Message，然后传送到待发送队列中
//                processAndSendToHandler(readyToHandlers);
//
//                logger.info("商品增量记录数:{},处理+发送时间{}ms", readyToHandlers.size(),
//                    System.currentTimeMillis() - readStartTime);
//
//            } catch (Exception err) {
//                logger.error("处理数据出现错误", err);
//            }
//
//            readyToHandlers.clear();
//        } finally {
//            handleringCount.decrementAndGet();
//        }
//    }
//
//    /**
//     * 处理一批需要增量的信息
//     * 1.转换mq中的消息格式；
//     * 2.发送给index-Handler；
//     * @param readyToHandlers
//     */
//    private void processAndSendToHandler(LinkedList<ProductLabelPBDataMessage> productInc) {
//        IndexIncreseRequest.Builder messageBuilder = IndexIncreseRequest.newBuilder();
//
//        long readStartTime = System.currentTimeMillis();
//        Iterator<ProductLabelPBDataMessage> ite = productInc.iterator();
//        while (ite.hasNext()) {
//            ProductLabelPBDataMessage productLabelPB = ite.next();
//            if (null == productLabelPB)
//                continue;
//            //操作类型
//            String op = productLabelPB.getOperation();
//
//            IndexProductInfo indexProductInfo = ProductUtil
//                .convertToIndexProductInfo(productLabelPB);
//
//            if (indexProductInfo == null) {
//                continue;
//            } else if (!"d".equals(op)
//                       && StringUtils.isBlank(indexProductInfo.getProductCategoryCode())) {
//                logger.error("{}'s productCategoryCode is null, op={}", indexProductInfo.getId(),
//                    op);
//                continue;
//            }
//            IncreaseIndexProductInfo.Builder productBuilder = IncreaseIndexProductInfo.newBuilder()
//                .setOpearation(op)//操作符
//                .setProductId(indexProductInfo.getId())//商品id
//                .setIndexProductInfo(indexProductInfo);//商品信息
//
//            messageBuilder.addIncreaseProductInfos(productBuilder);
//        }
//        logger.info("商品增量数据处理时间:{}ms", System.currentTimeMillis() - readStartTime);
//        // 直接发送数据
//        messageSendManager.sendIncreaseMessageAndWait(messageBuilder.build());
//    }
//
//    public boolean isInIncreaseHandler() {
//        return handleringCount.get() > 0;
//    }
//
//    public void setQueueSize(Integer queueSize) {
//        this.queueSize = queueSize;
//    }
//
//}

///**
// * Taotaosou.com Inc.
// * Copyright (c) 2010-2012 All Rights Reserved.
// */
//package com.taotaosou.tsearch.index.data.increase;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * 增量任务
// * 
// * @author <a href="mailto:yishun@taotaosou.com">Eason</a>
// * @version 1.0
// * @since 2013年8月27日 上午9:51:57
// */
//@Component
//public class ProductIncreaseJob {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProductIncreaseJob.class);
//
//    @Resource
//    private ProductAdditionAcceptor productAdditionAcceptor;
//    @Resource
//    private ProductAdditionHandler productAdditionHandler;
//
//    private ExecutorService additionAcceptorExecutor;
//    private ScheduledExecutorService additionHandlerExecutor;
//    @Value("${increment.handler.interval.ms}")
//    private Long productHandlerInterval;
//    @Value("${increment.handler.process.num}")
//    private int handlerProcessNum;
//    private long MAX_WAIT_TIME = 5; // 分钟
//
//    private volatile boolean fullReadRunning = false; // true表示全量索引正在运行，此时不能进行增量
//    private boolean running = false; // 标志线程是否在运行
//
//    /**
//     * 启动增量线程
//     * 可以重复启动
//     */
//    public void start() {
//        while (fullReadRunning) {
//            try {
//                logger.info("全量索引任务正在运行，还不能启动增量...");
//                TimeUnit.SECONDS.sleep(60);
//            } catch (InterruptedException e) {
//                logger.error("", e);
//            }
//        }
//        synchronized (this) {
//            if (!running) {
//                logger.info("商品增量间隔时间：{}ms", productHandlerInterval);
//
//                logger.info("启动商品增量监听器...");
//                additionAcceptorExecutor = Executors.newSingleThreadExecutor();
//                additionAcceptorExecutor.execute(productAdditionAcceptor);
//                logger.info("启动商品发送处理器");
//                additionHandlerExecutor = Executors.newScheduledThreadPool(handlerProcessNum);
//                for (int i = 0; i < handlerProcessNum; i++) {
//                    additionHandlerExecutor.scheduleWithFixedDelay(productAdditionHandler,
//                        i * productHandlerInterval / handlerProcessNum, productHandlerInterval,
//                        TimeUnit.MILLISECONDS);
//                }
//
//                running = true;
//            }
//        }
//        logger.info("增量线程已经启动");
//    }
//
//    /**
//     * 结束增量线程，在全量更新前需要调用该方法
//     * 可以重复执行
//     *
//     * @param fullRead 表示是否要进行全量索引
//     */
//    public void stop(boolean fullRead) {
//        fullReadRunning = fullRead || fullReadRunning;
//        synchronized (this) {
//            if (running) {
//                logger.info("关闭商品增量监听器...");
//                additionAcceptorExecutor.shutdown();
//                logger.info("关闭商品增量处理器");
//                additionHandlerExecutor.shutdown();
//
//                try {
//                    additionAcceptorExecutor.awaitTermination(MAX_WAIT_TIME, TimeUnit.MINUTES);
//                    additionHandlerExecutor.awaitTermination(MAX_WAIT_TIME, TimeUnit.MINUTES);
//                } catch (InterruptedException e) {
//                    logger.error("", e);
//                }
//                running = false;
//            }
//        }
//        while (productAdditionHandler.isInIncreaseHandler()) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                logger.error("", e);
//            }
//        }
//        logger.info("增量线程已经关闭");
//    }
//
//    /**
//     * 全量索引任务完成后调用该方法
//     */
//    public void fullReadFinished() {
//        fullReadRunning = false;
//    }
//}

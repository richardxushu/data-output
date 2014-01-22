package com.taotaosou.data.himport.client.hbase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.taotaosou.data.cachedata.SellerIdCache;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.output.XMLAppendFileManager;
import com.taotaosou.data.output.XMLFileManager;

/**
 * 处理hbase数据
 * @author richard.xu
 * @version 1.0
 */
@Service("productLabelXMLHandler")
public class ProductLabelXMLHandler implements ProductLabelHandler {

    private static final Logger      logger                 = LoggerFactory
                                                                .getLogger(ProductLabelXMLHandler.class);

    private static final long        MONITOR_INTERVAL       = 5;
    // 统计商品数量
    private AtomicInteger            productCount           = new AtomicInteger(0);
    // 统计id为空的商品数量
    private AtomicInteger            invalidProductCount    = new AtomicInteger(0);
    // 统计下架/删除的商品数量
    private AtomicInteger            outOfStackProductCount = new AtomicInteger(0);

    // 全量数据获取监控器
    private ScheduledExecutorService monitor;

//    @Autowired
//    private MessageSendManager       messageSendManager;
//    @Autowired
//    private EmailManager             emailManager;

//    @Value("${index.isRemotePush}")
//    private boolean                  isRemotePush;                                                           //建完全量索引后是否推送

    private CountDownLatch           latch;                                                                  // 控制所有数据传送线程结束后才发送结束消息

    private StringBuilder            mail;
    
    @Resource(name = "xmlManager")
    private XMLFileManager xmlManager;
    
//    @Resource(name = "xmlAppendManager")
//    private XMLAppendFileManager xmlManager;

    @Override
    public void start() {
        // 初始化商品数量
        productCount.set(0);
//        latch = new CountDownLatch(1);
        // 初始化监控器
        monitor = Executors.newScheduledThreadPool(1);
        monitor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                logger.info("已遍历商品数量:{}", productCount.get());
                
//                if (messageSendManager.getActiveTaskCount() == 0) {
//                    latch.countDown();
//                }
            }
        }, MONITOR_INTERVAL, MONITOR_INTERVAL, TimeUnit.SECONDS);
        // 记录起始时间
        logger.info("开始启动全量数据读取...");
//        xmlManager = new XMLFileManager();
//        xmlManager.init();
    }

    @Override
    public void handle(List<ProductLabel> productLabels) {
        if (productLabels != null && !productLabels.isEmpty()) {
            int size = productLabels.size();
            productCount.addAndGet(size);
            List<ProductLabel> validProducts = new ArrayList<ProductLabel>();
            for (ProductLabel productLabelInfo : productLabels) {
                // 过滤id为空的商品
                if (productLabelInfo == null || productLabelInfo.getId() == null) {
                    invalidProductCount.incrementAndGet();
                    continue;
                }
                // 过滤下架或删除商品
                if (productLabelInfo.getOnsaleFlag() != null
                    && productLabelInfo.getOnsaleFlag().intValue() == 0
                    || productLabelInfo.getDeletedFlag() != null
                    && productLabelInfo.getDeletedFlag().intValue() == 1) {
                    outOfStackProductCount.incrementAndGet();
                    continue;
                }
                if(!SellerIdCache.idSet.contains(productLabelInfo.getSellerId()+"")){
                    continue;
                }
                
                validProducts.add(productLabelInfo);
                
            }
            xmlManager.process(validProducts);
        }
    }

    @Override
    public void finish() {
        // 等待发送任务执行完毕
        logger.info("等待全量数据处理完毕...");
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            logger.error("", e);
//        }
//        long endTime = System.currentTimeMillis();
        
        xmlManager.finish();
        
        // 关闭监控线程
        monitor.shutdown();
        // 发送邮件

    

        logger.info("遍历商品数量:" + productCount.get());
        logger.info("id为空的商品数量:" + invalidProductCount.get());
        logger.info("下架或删除的商品数量:" + outOfStackProductCount.get());
        
        // 商品数量清零
        productCount.set(0);
        invalidProductCount.set(0);
        outOfStackProductCount.set(0);
        
//        // 推送索引文件
//        remotePush(isRemotePush);
    }

//    /**
//     * 如果参数为true，则通知handler将索引推送至search服务器
//     *
//     * @param push
//     */
//    private void remotePush(boolean push) {
//        //建好全量索引后是否推送
//        if (push) {
//            try {
//                logger.info("通知handler开始索引推送...");
//                messageSendManager.remotePush();
//            } catch (Exception err) {
//                logger.error("数据推送失败,索引切换中止!");
//            }
//            return;
//        }
//    }

    private void appendMailContent(String content) {
        mail.append(content).append("<br />");
    }

}

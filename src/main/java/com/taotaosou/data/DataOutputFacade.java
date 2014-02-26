/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotaosou.data.himport.client.hbase.FullProductLabelReaderSample;
import com.taotaosou.data.increase.ProductAdditionJob;

/**
 * 类DataOutput.java的实现描述：启动控制类
 * 
 * @author Richard.xu 2014年2月20日 下午8:53:15
 */
@Service
public class DataOutputFacade {

    @Value("${full.data.output.now}")
    private boolean                      fullOutput;        // 是否建全量

    @Resource
    private FullProductLabelReaderSample fullProductReader;

    @Resource
    private ProductAdditionJob           productAdditionJob;

    @PostConstruct
    public void startNow() {
        if (this.fullOutput) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    buildFullData(); // 建全量
                }
            }).start();
        } else {
            // additionJob.start(); // 启动增量
        }
        
        productAdditionJob.start();// 启动增量
    }

    public synchronized void buildFullData() {
        fullProductReader.fullRead();
    }

}

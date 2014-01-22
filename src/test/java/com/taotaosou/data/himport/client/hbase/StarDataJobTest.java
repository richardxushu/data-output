/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.himport.client.hbase;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.increase.StarDataJob;
import com.taotaosou.data.output.JsonFileManager;

/**
 * 类JsonFileManangerTest.java的实现描述：TODO 类实现描述 
 * @author Richard.xu 2014年1月14日 下午8:01:41
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StarDataJobTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private StarDataJob starDataJob;
    
//    @Ignore
    @Test
    public void testOutputData() {
        starDataJob.outputData();
    }
    
}

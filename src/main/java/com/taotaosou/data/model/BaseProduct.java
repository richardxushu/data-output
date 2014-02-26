/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.model;

import com.taotaosou.data.cachedata.BackCategoryCache;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.output.strategy.FormatBehavior;
import com.taotaosou.data.tsearch.TsearchClientProxy;

/**
 * 类BaseProduct.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年2月26日 上午10:34:36
 */
public interface BaseProduct {
    
    public String formatString();
    
    public String formatString(FormatBehavior behavior);

    public void convert(ProductLabelPBDataMessage proLabel, TsearchClientProxy tsearchClientProxy,
                        BackCategoryCache cateCache);

    public void convert(ProductLabel proLabel, TsearchClientProxy tsearchClientProxy, BackCategoryCache cateCache);

}

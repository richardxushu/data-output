/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output;

import java.io.IOException;
import java.util.List;

import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.model.BaseProduct;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;

/**
 * 类FileManager.java的实现描述：写文件输出类
 * 
 * @author Richard.xu 2014年2月10日 下午2:06:43
 */
public interface FileManager {

    public void init();

    public void flush();

    public void close();
    
    public void setProduct(BaseProduct product);
    
    public void reloadWriter(Integer writerId);
    
    public void process(List<ProductLabel> plList, Integer writerId);
    
    public void processNotify(List<ProductLabelPBDataMessage> plbList, Integer writerId);
    
}

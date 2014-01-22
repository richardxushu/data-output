/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.increase;

import com.taotaosou.data.himport.client.mq.ProductLabelNotifyClient;

/**
 * 类ProductIncHandler.java的实现描述：TODO 类实现描述 
 * @author Richard.xu 2014年1月11日 上午10:02:18
 */
public class ProductIncHandler implements Runnable{
    
    private ProductLabelNotifyClient productLabelClient;

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }

    /**
     * @return the productLabelClient
     */
    public ProductLabelNotifyClient getProductLabelClient() {
        return productLabelClient;
    }

    /**
     * @param productLabelClient the productLabelClient to set
     */
    public void setProductLabelClient(ProductLabelNotifyClient productLabelClient) {
        this.productLabelClient = productLabelClient;
    }

}

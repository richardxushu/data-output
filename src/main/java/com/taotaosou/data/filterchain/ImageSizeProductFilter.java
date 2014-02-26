/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.filterchain;

import java.util.ArrayList;
import java.util.List;

import com.taotaosou.common.uri.image.ImageURIBroker;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.himport.client.hbase.domain.ProductPro;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;

/**
 * 类ImageSizeProductFilter.java的实现描述：TODO 类实现描述 
 * @author Richard.xu 2014年1月16日 下午4:59:38
 */
public class ImageSizeProductFilter implements ProductFilter{

    /* (non-Javadoc)
     * @see com.taotaosou.data.filterchain.ProductFilter#filterProductLabel(com.taotaosou.data.himport.client.hbase.domain.ProductPro)
     */
    @Override
    public void filterProductLabel(ProductPro product) {
        if(product==null)
            return;
        
        
        ImageURIBroker.getInstance().getURI(product.getImagePath());
        
        product.getImagePath();
        // TODO Auto-generated method stub
        
    }


}

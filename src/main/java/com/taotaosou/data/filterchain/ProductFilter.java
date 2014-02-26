/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.filterchain;

import java.util.List;

import com.taotaosou.data.himport.client.hbase.domain.ProductPro;

/**
 * 类Filter.java的实现描述：TODO 类实现描述 
 * @author Richard.xu 2014年1月16日 下午4:37:49
 */
public interface ProductFilter {
    
    
    public void filterProductLabel(ProductPro product);

}

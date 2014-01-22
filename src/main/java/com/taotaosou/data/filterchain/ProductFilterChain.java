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

/**
 * 类ProductFilterChain.java的实现描述：Product商品的过滤链
 * @author Richard.xu 2014年1月16日 下午4:31:12
 */
public class ProductFilterChain {
    
    List<ProductFilter> filterList = new ArrayList<ProductFilter>();
    
    
    public void addFilterLast(ProductFilter filter){
        filterList.add(filter);
    }

}

/**
 * Taotaosou.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.taotaosou.data.himport.client.hbase;

import java.util.List;

import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;

/**
 * 商品标签处理器
 * @author richard.xu
 */
public interface ProductLabelHandler {
    /**
     * 初始化
     */
    void start();

    /**
     * 并发处理列表
     * 
     * @param productLabels
     */
    void handle(List<ProductLabel> productLabels);

    /**
     * 数据处理完成
     */
    void finish();
}

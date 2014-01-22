/**
 * Copyright ©2011-2013, TAOTAOSOU. All right reserved.
 *
 * File: ProductLabelSample.java
 *
 * Package: com.taotaosou.tsearch.index.data.full.Handler
 *
 * Creator: <a href="mailto:lanxin.liao@gmail.com">lansine</a>
 *
 * Since: 2013-8-19 下午6:15:09
 */

package com.taotaosou.data.himport.client.hbase;

import java.util.List;

import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;

/**
 * 商品标签处理器
 * 
 * @author lanxin.liao
 * @version 2013-8-19 下午6:15:09
 */

public interface ProductLabelSample {
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

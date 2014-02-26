/**
 * Copyright ©2011-2013, TAOTAOSOU. All right reserved.
 *
 * File: FullProductLabelReaderTest.java
 *
 * Package: com.taotaosou.data.himport.client.hbase
 *
 * Creator: <a href="mailto:lanxin.liao@gmail.com">lansine</a>
 *
 * Since: 2013-8-28 下午4:53:07
 */

package com.taotaosou.data.himport.client.hbase;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.taotaosou.data.client.hbase.HBaseClient;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;

/**
 * FullProductLabelReader的测试用例
 * 
 * @author lanxin.liao
 * @version 2013-8-28 下午4:53:07
 */
@Ignore
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class FullProductLabelReaderTest extends
		AbstractJUnit4SpringContextTests {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FullProductLabelReaderTest.class);

	@Resource
	private FullProductLabelReaderSample fullProductReader;

	@Resource
	private HBaseClient hbaseClient;

	@Ignore
	@Test
	public void testRead() {

//		fullProductReader.setHandler(new ProductLabelXMLHandler());

		fullProductReader.fullRead();
	}

	@Ignore
	@Test
	public void testReadOne() {

		ProductLabel productLabel = hbaseClient.query("himport_product_label",
				"1726370839", "p", ProductLabel.class);

		logger.info("{}", ToStringBuilder.reflectionToString(productLabel));
		System.out.println("price:" + productLabel.getPrice());
		System.out.println("marketPrice:" + productLabel.getMarketPrice());
		System.out.println("ProPrice:" + productLabel.getPromoPrice());

	}
	
	public static void main(String []args){
		System.out.println(1111);
	}
}

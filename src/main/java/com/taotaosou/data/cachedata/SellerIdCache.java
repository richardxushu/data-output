/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.cachedata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * 类SellerIdCache.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年1月21日 下午5:19:34
 */
public class SellerIdCache {

    public static Set<String> idSet = new HashSet<String>();

    static {
        BufferedReader br = null;
        InputStream in = null;
        try {
            in = new FileInputStream("F:/data/seller.txt");
            br = new BufferedReader(new InputStreamReader(in));

            String sellerId = br.readLine();
            while (sellerId != null) {
                idSet.add(sellerId.trim());
                sellerId = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.util;

import java.text.DecimalFormat;

/**
 * 类PriceUtil.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年1月16日 下午2:13:20
 */
public class PriceUtil {

    public static String formatPrice(int price) {
        return price / 100 + "." + (price / 10) % 10;
    }

    public static double formatPriceDouble(int price) {

        price = price / 10;
        return new Double(price) / 10;
    }

    public static void main(String[] args) {
        System.out.println(formatPriceDouble(10500));
    }

}

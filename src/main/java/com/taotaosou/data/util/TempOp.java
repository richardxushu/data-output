/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.util;

/**
 * 类TempOp.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年2月19日 下午2:23:54
 */
public class TempOp {

    public static String[] opArr = new String[] { "add", "del", "upd" };

    public static String getRadomOp() {
        int i = (int) (Math.random() * 3);
        return opArr[i];
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++){
           System.out.println( getRadomOp());
        }
    }

}

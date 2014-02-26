/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the
 * confidential and proprietary information of Taotaosou.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Taotaosou.com.
 */
package com.taotaosou.data.output.strategy;

/**
 * 类FormatBehavior.java的实现描述：object对像转化成不同string接口 
 * @author Richard.xu 2014年2月26日 上午10:57:37
 */
public interface FormatBehavior {
    
    public String format(Object obj);
}

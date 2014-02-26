/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output.strategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 类JsonFormatBehavior.java的实现描述：传化成json
 * 
 * @author Richard.xu 2014年2月26日 上午10:59:55
 */
public class JsonFormatBehavior implements FormatBehavior {

    private final static Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    /*
     * (non-Javadoc)
     * @see com.taotaosou.data.output.strategy.FormatBehavior#format()
     */
    @Override
    public String format(Object obj) {
        return GSON.toJson(obj);
    }

}

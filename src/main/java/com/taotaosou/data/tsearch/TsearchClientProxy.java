/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.tsearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotaosou.data.cachedata.DataCache;
import com.taotaosou.data.output.JsonFileManager;
import com.taotaosou.search.client.service.SearchClient;

/**
 * 类TsearchClientProxy.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年1月15日 下午9:01:15
 */
@Service("tsearchClientProxy")
public class TsearchClientProxy {

    private static final Logger logger = Logger.getLogger(TsearchClientProxy.class);

    @Autowired
    private SearchClient        searchClient;

    @Autowired
    private DataCache           dataCache;

    /**
     * @return the searchClient
     */
    public SearchClient getSearchClient() {
        return searchClient;
    }

    /**
     * tsarch分词后再去标签缓存去匹配
     * 
     * @param words
     * @return
     */
    public List<String> getAnalyzedWords(String words) {
        if (words == null) return null;
        List<String> tmpTagList = null;
        try {
            tmpTagList = searchClient.getAnalyzedWords(words);
        } catch (Exception e) {
            logger.error("getAnalyzedWords words error:", e);

        }
        if (tmpTagList == null) return null;
        List<String> tagList = new ArrayList<String>();
        for (String tag : tmpTagList) {
            if (dataCache.getTagSet().contains(tag)) {
                tagList.add(tag);
            }
        }
        return tagList;
    }

    public String getAnalyzedWordsStr(String words) {
        try {
            List<String> tagList = this.getAnalyzedWords(words);
            String tagStr = "";
            if (tagList == null || tagList.isEmpty()) return "";
            for (String tag : tagList) {
                tagStr = "," + tag;
            }

            return tagStr.substring(1);
        } catch (Exception e) {
            logger.error("getAnalyzedWordsStr error: ", e);
            return "";
        }

    }

}

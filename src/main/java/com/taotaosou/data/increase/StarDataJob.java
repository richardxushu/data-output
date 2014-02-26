/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.increase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotaosou.common.lang.DateUtil;
import com.taotaosou.data.cachedata.DataCache;
import com.taotaosou.data.model.ProductBaidu;
import com.taotaosou.data.model.StarFind;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.output.JsonFileManager;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.data.util.PriceUtil;
import com.taotaosou.data.util.StringUtil;
import com.taotaosou.search.client.service.SearchClient;
import com.taotaosou.tts.zsm.model.FindProductVO;
import com.taotaosou.tts.zsm.model.QandADetailBean;
import com.taotaosou.tts.zsm.service.FindService;

/**
 * 类StarDataJob.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年1月13日 下午4:36:00
 */
@Service("starDataJob")
public class StarDataJob {

    private final static Gson  GSON = new GsonBuilder().disableHtmlEscaping().create();

    @Resource(name = "findServiceCom")
    private FindService        findServiceCom;

    @Resource
    private JsonFileManager    jsonFileManager;

    @Autowired
    private TsearchClientProxy tsearchClientProxy;

    @Autowired
    private DataCache          dataCache;
    

    public void outputData() {
//        jsonFileManager.reloadWriter();
//        
//        int page = 1;
//        Date startTime = DateUtil.getStartOfDate(new Date());
//        Date endTime = new Date();
//        
//        // 4 is movie star
//        List<QandADetailBean> resultList = findServiceCom.fingByCategory("4", page, startTime, endTime);
//
//        while(resultList!=null&&resultList.size()>0){
//            for (QandADetailBean bean : resultList) {
//                Map<String, Object> resMap = this.convert(bean);
//
//                if (resMap == null) continue;
//
//                String starData = GSON.toJson(resMap.get("starFind"));
//                String productData = GSON.toJson(resMap.get("productBaidu"));
//
////                jsonFileManager.writeStar(starData);
//                jsonFileManager.writeProduct(productData);
//            }
//            page++;
//            resultList = findServiceCom.fingByCategory("4", page, startTime, endTime);
//        }
//        
//        jsonFileManager.flush();
//       
    }

    // public ProductBaidu genProductData(StarFind starFind) {
    //
    // if (starFind == null) return null;
    //
    // long goodId = StringUtil.parseLong(starFind.getGoodsId(), 0l);
    // long[] ids = { goodId };
    // List<ProductDetail> pDetails = searchClient.getProductDetailBySourceIds(ids, WebSiteSelect.TAOBAO);
    // starFind.getGoodsId();
    //
    // pDetails
    //
    // }

    // convert the starFind and ProductBaidu
    public Map<String, Object> convert(QandADetailBean bean) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        StarFind starFind = new StarFind();
        ProductBaidu productBaidu = new ProductBaidu();

        starFind.setFrom_url("http://www.taotaosou.com/find/finddetail-" + bean.getId() + ".html");
        String objUrl = bean.getImageUrl();
        if (objUrl != null) {
            objUrl = objUrl.replace("_280x330.jpg", "");
        }
        
        List<String> objUrlList = new ArrayList<String>();
        objUrlList.add(objUrl);
        starFind.setObj_url(objUrlList);

        List<FindProductVO> proList = bean.getProductVOs();
        FindProductVO product = null;

        for (FindProductVO pro : proList) {
            if (pro.getTtsId() != 0) {
                product = pro;
                break;
            }

        }
        if (product == null) return null;

        starFind.setTitle(product.getTitle());
        starFind.setTag(tsearchClientProxy.getAnalyzedWords(product.getTitle()));
        List<String> goodIdList = new ArrayList<String>();
        goodIdList.add(product.getTbNumIid());
        starFind.setGoodIds(goodIdList);
        starFind.setTime(System.currentTimeMillis() + "");

        // set the productBaidu
        productBaidu.setGoodsId(product.getTbNumIid());

        productBaidu.setGoodsType(dataCache.getBaiduTagMap().get(product.getCategoryCode()));
        productBaidu.setTag(tsearchClientProxy.getAnalyzedWords(product.getTitle()));
        productBaidu.setTitle(product.getTitle());
        List<String> proObjUrlList = new ArrayList<String>();
        String proObjUrl = product.getImageUrl();
//        if (proObjUrl != null) {
//            proObjUrl = proObjUrl.replace("_450x450", "");
//        }
        
        proObjUrlList.add(proObjUrl);
        productBaidu.setObj_url(proObjUrlList);
        productBaidu.setFrom_url("http://www.taotaosou.com/product-" + product.getTtsId()
                                 + "--.html?utm_source=baidupic&utm_medium=pic&utm_campaign=baidu");
        productBaidu.setBuy_url(productBaidu.getFrom_url());
        productBaidu.setPrice(PriceUtil.formatPriceDouble(product.getPrice()));

        resMap.put("starFind", starFind);
        resMap.put("productBaidu", productBaidu);
        return resMap;
    }

}

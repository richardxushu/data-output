///**
// * Taotaosou.com Inc.
// * Copyright (c) 2010-2012 All Rights Reserved.
// */
//package com.taotaosou.data.util;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.protobuf.Descriptors.FieldDescriptor;
//import com.taotaosou.common.core.able.AbstractLogable;
//import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
//import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
//import com.taotaosou.tsearch.common.Constants;
//import com.taotaosou.tsearch.common.message.Module.IndexProductInfo;
//import com.taotaosou.tsearch.common.util.UrlUtil;
//
///**
// *
// * @author   ChaoKai Wen
// *           email:regis.wen@taotaosou.com
// * @version  
// * @since    Ver 1.1
// * @Date     Sep 12, 2012
// */
//public class ProductUtil {
//
//    private static final Logger LOGGER        = LoggerFactory.getLogger(ProductUtil.class);
//
//    public static String        filterUrlPath = "http://item.taobao.com/item.htm";
//
//    /**
//     * 全量
//     *
//     * @param productInfos
//     * @param tableName
//     * @return
//     */
//    public static List<IndexProductInfo> convertProductToPBProductInfo(List<ProductLabel> productInfos) {
//        List<IndexProductInfo> rets = new LinkedList<IndexProductInfo>();
//        for (ProductLabel productInfo : productInfos) {
//            try {
//                IndexProductInfo indexProductInfo = convertProductToIndexProduct(productInfo);
//                if (indexProductInfo == null)
//                    continue;
//                rets.add(indexProductInfo);
//            } catch (Exception er) {
//                LOGGER.error("在将productLabelInfo转化成pb对象时发生错误", er);
//            }
//        }
//        return rets;
//    }
//
//    /**
//    *
//    * @param productLabelPB
//    * @return
//    */
//    public static IndexProductInfo convertToIndexProductInfo(ProductLabelPBDataMessage productLabelPB) {
//        return convertProductToIndexProductBuilder(productLabelPB).build();
//    }
//
//    /**
//     * 全量
//     *
//     * @param productInfo
//     * @param tableName
//     * @return
//     */
//    public static IndexProductInfo convertProductToIndexProduct(ProductLabel productInfo) {
//        IndexProductInfo.Builder indexProductInfo = convertProductToIndexProductBuilder(productInfo);
//        return indexProductInfo.build();
//    }
//
//    /**
//     * 全量
//     *
//     * @param productInfo 
//     * @return
//     */
//    private static IndexProductInfo.Builder convertProductToIndexProductBuilder(ProductLabel productInfo) {
//        IndexProductInfo.Builder indexProductInfo = IndexProductInfo.newBuilder();
//
//        indexProductInfo.setId(productInfo.getId());
//
//        if (null != productInfo.getProductTitle())
//            indexProductInfo.setProductTitle(productInfo.getProductTitle());
//        if (null != productInfo.getCid())
//            indexProductInfo.setCid(productInfo.getCid().trim());
//
//        //        if (null != productInfo.getCommission())
//        indexProductInfo.setCommission(0);
//
//        if (null != productInfo.getLocation())
//            indexProductInfo.setLocation(productInfo.getLocation());
//
//        if (null != productInfo.getPrice())
//            indexProductInfo.setPrice(productInfo.getPrice());
//
//        if (null != productInfo.getProductCategoryCode())
//            indexProductInfo.setProductCategoryCode(productInfo.getProductCategoryCode().trim());
//
//        if (null != productInfo.getPromototedService())
//            indexProductInfo.setPromototedService(productInfo.getPromototedService());
//
//        if (null != productInfo.getPropsName())
//            indexProductInfo.setPropsName(productInfo.getPropsName());
//
//        if (null != productInfo.getSalesVolume())
//            indexProductInfo.setSalesVolume(productInfo.getSalesVolume());
//        else
//            indexProductInfo.setSalesVolume(0);
//
//        if (null != productInfo.getSellerId())
//            indexProductInfo.setSellerId(productInfo.getSellerId());
//
//        if (null != productInfo.getSex())
//            indexProductInfo.setSex(productInfo.getSex());
//
//        if (null != productInfo.getTagTime())
//            indexProductInfo.setTagTime(productInfo.getTagTime().getTime());
//
//        String newUrl = filterProductUrl(productInfo.getUrl());
//        productInfo.setUrl(newUrl);
//        if (null != productInfo.getUrl())
//            indexProductInfo.setUrl(productInfo.getUrl());
//
//        if (null != productInfo.getWebsite())
//            indexProductInfo.setWebsite(productInfo.getWebsite());
//
//        //商品原id
//        if (null != productInfo.getSourceProductId())
//            indexProductInfo.setSourceProductId(productInfo.getSourceProductId().trim());
//
//        if (null != productInfo.getTagTtsLevel())
//            indexProductInfo.setTagTtsLevel(productInfo.getTagTtsLevel());
//
//        if (null != productInfo.getTagTtsTime())
//            indexProductInfo.setTagTtsTime(productInfo.getTagTtsTime().getTime());
//
//        if (null != productInfo.getFeedbackCount())
//            indexProductInfo.setFeedbackCount(productInfo.getFeedbackCount());
//
//        if (productInfo.getTtsProductFlag() >= Constants.IS_TTS_STYLE_PRODUCT)
//            indexProductInfo.setTtsProductFlag(productInfo.getTtsProductFlag());
//        else
//            indexProductInfo.setTtsProductFlag(0);
//
//        if (productInfo.getTtsProductFeature() != null)
//            indexProductInfo.setTtsProductFeature(productInfo.getTtsProductFeature());
//
//        int conditionFlag = 0;
//        //        if (productInfo.isDiscount())
//        //            conditionFlag += Constants.DISCOUNT_FLAG_TYPE;
//        //        if (productInfo.isPrepay())
//        //            conditionFlag += Constants.PREPAY_FLAG_TYPE;
//        //        if (productInfo.isSellPromise())
//        //            conditionFlag += Constants.SELLPROMISE_FLAG_TYPE;
//        if (productInfo.getVlsplic() == 1)//拼接图
//            conditionFlag += Constants.SPLIC_FLAG_TYPE;
//        if (productInfo.getTagFlag() == Constants.IS_TAG_PRODUCT)
//            conditionFlag += Constants.TAG_FLAG_TYPE;
//        // isCPS已经停止维护
//        //        if (productInfo.getIsCPS() == Constants.IS_CPS_PRODUCT)
//        //            conditionFlag += Constants.CPS_FLAG_TYPE;
//        if (productInfo.getTagTtsFlag() == Constants.IS_TAG_PRODUCT)
//            conditionFlag += Constants.TAG_TTS_FLAG_TYPE;
//        if (productInfo.getNewProductFlag() == Constants.IS_NEW_PRODUCT)
//            conditionFlag += Constants.NEW_PRODUCT_FLAG_TYPE;
//        indexProductInfo.setConditionFlag(conditionFlag);
//
//        // 促销价格
//        if (null == productInfo.getActualPrice() || productInfo.getActualPrice() <= 0) {
//            // 不存在促销价的情况下
//            if (null != productInfo.getPrice() && productInfo.getPrice() >= 0)
//                indexProductInfo.setPromoPrice(productInfo.getPrice());
//            else
//                indexProductInfo.setPromoPrice(0);
//        } else
//            indexProductInfo.setPromoPrice(productInfo.getActualPrice());
//
//        //图像属性的处理
//        //主图id
//        if (null != productInfo.getMainImageId())
//            indexProductInfo.setMainImageId(productInfo.getMainImageId());
//        //同款组id（单值）
//        if (null != productInfo.getSimGID())
//            indexProductInfo.setGroupId(productInfo.getSimGID());
//        //颜色
//        if (null != productInfo.getVlDomColorList())
//            indexProductInfo.addAllColorList(productInfo.getVlDomColorList());
//        //牛皮癣
//        if (null != productInfo.getVlnpx())
//            indexProductInfo.setNpxFlag(productInfo.getVlnpx());
//        //宽高
//        if (null != productInfo.getVlh())
//            indexProductInfo.setMainImageViewHeight(productInfo.getVlh());
//        if (null != productInfo.getVlw())
//            indexProductInfo.setMainImageViewWeight(productInfo.getVlw());
//
//        //ttsProductFlag
//        //库-判断
//        int productLibray = 0; //全库
//        int tagTTSFlag = productInfo.getTagTtsFlag();
//        int tagFlag = productInfo.getTagFlag();
//        //ttsProductFlag只会返回-1、0、1
//        int ttsProductFlag = productInfo.getTtsProductFlag();//1a,2b,0all,-1
//        if (tagTTSFlag == 1 || ttsProductFlag == 1)
//            productLibray = 1; //A库
//        else if (tagFlag == 1 || ttsProductFlag == 2)
//            productLibray = 2; //B库
//
//        indexProductInfo.setTtsProductFlag(productLibray);
//
//        if (LOGGER.isDebugEnabled()) {
//            Map<FieldDescriptor, Object> fields = indexProductInfo.build().getAllFields();
//            for (Iterator<Entry<FieldDescriptor, Object>> iterator = fields.entrySet().iterator(); iterator
//                .hasNext();) {
//                Entry<FieldDescriptor, Object> field = iterator.next();
//                LOGGER.debug(field.getKey().getName() + ":" + field.getValue());
//            }
//        }
//
//        return indexProductInfo;
//    }
//
//    /**
//     * 增量
//     *
//     * @param productInfoMQ
//     * @return
//     */
//    private static IndexProductInfo.Builder convertProductToIndexProductBuilder(ProductLabelPBDataMessage productInfoMQ) {
//
//        IndexProductInfo.Builder indexProductInfo = IndexProductInfo.newBuilder();
//
//        //pb->ProcudeLabelInfo
//        //        ProductLabelInfo productLabelInfo = new ProductLabelInfo();
//        //        Mapper mapper = new DozerBeanMapper();
//        //        mapper.map(productInfoMQ, productLabelInfo);
//
//        //
//        String newUrl = filterProductUrl(productInfoMQ.getUrl());
//        if (null != newUrl)
//            indexProductInfo.setUrl(newUrl);
//
//        indexProductInfo.setId(productInfoMQ.getId());
//
//        if (null != productInfoMQ.getProductTitle())
//            indexProductInfo.setProductTitle(productInfoMQ.getProductTitle());
//        if (null != productInfoMQ.getCid())
//            indexProductInfo.setCid(productInfoMQ.getCid().trim());
//
//        indexProductInfo.setCommission(0);
//
//        indexProductInfo.setLocation(productInfoMQ.getLocation());
//
//        indexProductInfo.setPrice(productInfoMQ.getPrice());
//
//        if (null != productInfoMQ.getProductCategoryCode())
//            indexProductInfo.setProductCategoryCode(productInfoMQ.getProductCategoryCode().trim());
//
//        if (null != productInfoMQ.getPromototedService())
//            indexProductInfo.setPromototedService(productInfoMQ.getPromototedService());
//
//        if (null != productInfoMQ.getPropsName())
//            indexProductInfo.setPropsName(productInfoMQ.getPropsName());
//
//        if (0 < productInfoMQ.getSalesVolume())
//            indexProductInfo.setSalesVolume(productInfoMQ.getSalesVolume());
//        else
//            indexProductInfo.setSalesVolume(0);
//
//        indexProductInfo.setSellerId(productInfoMQ.getSellerId());
//
//        indexProductInfo.setSex(productInfoMQ.getSex());
//
//        indexProductInfo.setTagTime(productInfoMQ.getTagTime());
//
//        if (null != productInfoMQ.getUrl())
//            indexProductInfo.setUrl(productInfoMQ.getUrl());
//
//        if (null != productInfoMQ.getWebsite())
//            indexProductInfo.setWebsite(productInfoMQ.getWebsite());
//
//        //商品原id
//        if (null != productInfoMQ.getSourceProductId())
//            indexProductInfo.setSourceProductId(productInfoMQ.getSourceProductId());
//
//        indexProductInfo.setTagTtsLevel(productInfoMQ.getTagTtsLevel());
//
//        indexProductInfo.setTagTtsTime(productInfoMQ.getTagTtsTime());
//
//        indexProductInfo.setFeedbackCount(productInfoMQ.getFeedbackCount());
//
//        if (productInfoMQ.getTtsProductFlag() >= Constants.IS_TTS_STYLE_PRODUCT)
//            indexProductInfo.setTtsProductFlag(productInfoMQ.getTtsProductFlag());
//        else
//            indexProductInfo.setTtsProductFlag(0);
//
//        if (productInfoMQ.getTtsProductFeature() != null)
//            indexProductInfo.setTtsProductFeature(productInfoMQ.getTtsProductFeature());
//
//        int conditionFlag = 0;
//        //        if (productInfoMQ.getDiscountFlag() == 1)
//        //            conditionFlag += Constants.DISCOUNT_FLAG_TYPE;
//        //        if (productInfoMQ.getPrepayFlag() == 1)
//        //            conditionFlag += Constants.PREPAY_FLAG_TYPE;
//        //        if (productInfoMQ.getSellPromiseFlag() == 1)
//        //            conditionFlag += Constants.SELLPROMISE_FLAG_TYPE;
//        if (productInfoMQ.getVlsplic() == 1)//拼接图
//            conditionFlag += Constants.SPLIC_FLAG_TYPE;
//        if (productInfoMQ.getTagFlag() == Constants.IS_TAG_PRODUCT)
//            conditionFlag += Constants.TAG_FLAG_TYPE;
//        //        if (productInfoMQ.getIsCPS() == Constants.IS_CPS_PRODUCT)
//        //            conditionFlag += Constants.CPS_FLAG_TYPE;
//        if (productInfoMQ.getTagTtsFlag() == Constants.IS_TAG_PRODUCT)
//            conditionFlag += Constants.TAG_TTS_FLAG_TYPE;
//        //        if (productInfoMQ.getNewProductFlag() == Constants.IS_NEW_PRODUCT)
//        //            conditionFlag += Constants.NEW_PRODUCT_FLAG_TYPE;
//        indexProductInfo.setConditionFlag(conditionFlag);
//
//        // 促销价格(实价)
//        if (productInfoMQ.getActualPrice() <= 0) {
//            // 不存在促销价的情况下
//            if (productInfoMQ.getPromoPrice() >= 0)
//                indexProductInfo.setPromoPrice(productInfoMQ.getPromoPrice());
//            else if (productInfoMQ.getPrice() >= 0)
//                indexProductInfo.setPromoPrice(productInfoMQ.getPrice());
//            else
//                indexProductInfo.setPromoPrice(0);
//        } else
//            indexProductInfo.setPromoPrice(productInfoMQ.getActualPrice());
//
//        //图像属性的处理
//        //主图id
//        indexProductInfo.setMainImageId(productInfoMQ.getMainImageId());
//        //同款组id（单值）
//        indexProductInfo.setGroupId(productInfoMQ.getSimGID() == 0 ? productInfoMQ.getMainImageId()
//            : productInfoMQ.getSimGID());
//        //颜色
//        indexProductInfo.addAllColorList(getDomColorList(productInfoMQ.getVldomcolor()));
//        //牛皮癣
//        indexProductInfo.setNpxFlag(productInfoMQ.getVlnpx());
//        //宽高
//        indexProductInfo.setMainImageViewHeight(productInfoMQ.getVlh());
//        indexProductInfo.setMainImageViewWeight(productInfoMQ.getVlw());
//
//        //ttsProductFlag
//        //库-判断
//        //        int productLibray = 0; //全库
//        //        int tagTTSFlag = productInfoMQ.getTagTtsFlag();
//        //        int tagFlag = productInfoMQ.getTagFlag();
//        //        //ttsProductFlag只会返回-1、0、1
//        //        int ttsProductFlag = productInfoMQ.getTtsProductFlag();//1a,2b,0all,-1
//        //        if (tagTTSFlag == 1 || ttsProductFlag == 1)
//        //            productLibray = 1; //A库
//        //        if (tagFlag == 1 || ttsProductFlag == 2)
//        //            productLibray = 2; //B库
//
//        indexProductInfo.setTtsProductFlag(productInfoMQ.getTtsProductFlag());
//
//        return indexProductInfo;
//    }
//
//    //特殊处理---------------------------------------
//
//    /**
//     * 截取商品的URL信息
//     * @param product
//     */
//    private static String filterProductUrl(String url) {
//        String newUrl = UrlUtil.splitUrl(url, filterUrlPath);
//        return newUrl;
//    }
//
//    public static List<Integer> getDomColorList(String colorListStr) {
//        List<Integer> rets = new LinkedList<Integer>();
//        if (colorListStr != null) {
//            String[] parts = colorListStr.split(" ");
//            if (null != parts) {
//                for (String colorId : parts) {
//                    try {
//                        rets.add(Integer.parseInt(colorId.trim()));
//                    } catch (Exception err) {
//                    }
//                }
//            }
//        }
//        return rets;
//    }
//
//}

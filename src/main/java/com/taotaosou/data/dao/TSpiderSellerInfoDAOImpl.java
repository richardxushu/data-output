package com.taotaosou.data.dao;

import com.taotaosou.data.model.TSpiderSellerInfo;

import java.util.List;

public class TSpiderSellerInfoDAOImpl  extends BaseDao implements TSpiderSellerInfoDAO {
    private static final String space = "t_spider_seller_info.";


    
    public List<TSpiderSellerInfo> getSellerByPage(TSpiderSellerInfo seller, Pager pager) {
        return ((List<TSpiderSellerInfo>) super.page(space + "getSellerByPage", pager, seller));
    }

}
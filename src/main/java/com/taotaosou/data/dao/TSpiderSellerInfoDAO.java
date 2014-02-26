package com.taotaosou.data.dao;

import java.util.List;

import com.taotaosou.data.model.TSpiderSellerInfo;


public interface TSpiderSellerInfoDAO {
    public List<TSpiderSellerInfo> getSellerByPage(TSpiderSellerInfo seller, Pager pager);
}
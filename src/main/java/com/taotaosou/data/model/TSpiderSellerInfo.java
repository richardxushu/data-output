package com.taotaosou.data.model;

import java.util.Date;

public class TSpiderSellerInfo {

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * t_spider_seller_info.id
     * 
     * @abatorgenerated Thu Jan 23 10:43:33 CST 2014
     */
    private Long   id;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * t_spider_seller_info.seller_id
     * 
     * @abatorgenerated Thu Jan 23 10:43:33 CST 2014
     */
    private String sellerId;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to the database column
     * t_spider_seller_info.credit_grade
     * 
     * @abatorgenerated Thu Jan 23 10:43:33 CST 2014
     */
    private Byte   creditGrade;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the sellerId
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId the sellerId to set
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return the creditGrade
     */
    public Byte getCreditGrade() {
        return creditGrade;
    }

    /**
     * @param creditGrade the creditGrade to set
     */
    public void setCreditGrade(Byte creditGrade) {
        this.creditGrade = creditGrade;
    }

}

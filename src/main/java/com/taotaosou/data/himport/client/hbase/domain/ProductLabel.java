package com.taotaosou.data.himport.client.hbase.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品以及标签
 * 
 * 此Bean坑多，不要使用反射破锁访问
 * 
 * @author 不愿意署名
 * @Date 2013-3-14 上午10:27:06
 * @Version v1.0.0
 */
public class ProductLabel {

	/** Label groupId */
	private Long simGID;
	
	private String vldomcolor;

	/** 牛皮癣标记 */
	private Integer vlnpx;

	/** 拼接图标记 */
	private Integer vlsplic;

	/** 高度 */
	private Integer vlh;

	/** 图片宽度 */
	private Integer vlw;

	/** id. */
	private Long id;
	/** 商品类目编号. */
	private String productCategoryCode;
	/** 网站代号. */
	private String website;
	/** 商家ID. */
	private Long sellerId;
	/** 商家名称. */
	private String sellerName;
	/** 商品标题. */
	private String productTitle;

	/** 销量. */
	private Integer salesVolume;

	/** 所在地区. */
	private Integer location;

	/** 源商品编号. */
	private String sourceProductId;
	/** 源网站商品所属的叶子类目id. */
	private String cid;
	/** 商品属性名称. */
	private String propsName;

	/** 商品URL. */
	private String url;

	/** 性别. */
	private Integer sex;

	/** 消保服务 */
	private String promototedService;

	/** 商品图片ID串，逗号分隔 **/
	private String imageIds;

	/**
	 * 上架标志，1表示上架，0表示下架
	 */
	private Integer onsaleFlag;

	/**
	 * 删除标志，1标识已经删除，0表示未删除
	 */
	private Integer deletedFlag;

	/** 主图图片ID */
	private Long mainImageId;

	/** 新品标志 0：false,1:true */
	private Integer newProductFlag;

	/** 评论数 */
	private Integer feedbackCount;

	// 2012-07-18 add by darren spider2.6.4
	/** 人工选品标记. */
	private Integer tagFlag;

	/** 人工选品标记时间. */
	private Date tagTime;

	/** 主站人工选品标记. */
	private Integer tagTtsFlag;

	/** 主站人工选品星级. */
	private Integer tagTtsLevel;

	/** 主站人工选品标记时间. */
	private Date tagTtsTime;

	/*** 主站人群标签标记1: 是主站人群标记 0orNull 不是, -1为人工清除的商品 */
	private Integer ttsProductFlag;
	/** 商品标签 */
	private String ttsProductFeature;

	/** 主站人群标签标记时间 */
	private Date ttsProductTime;

	/** 主图路径 */
	private String mainImagePath;
	/**
	 * 主图URL
	 */
	private String mainImageUrl;

	/**
	 * 商品等级，a为A库，b为B库
	 */
	private String productLevel;

	/**
	 * 市场价
	 */
	private Integer marketPrice;

	/**
	 * 原价
	 */
	private Integer price;

	/**
	 * 促销价
	 */
	private Integer promoPrice;

	/**
	 * 实际价格
	 */
	private Integer actualPrice;

	/**
	 * 换为数据中心,新算出的simGId
	 * 
	 * @return
	 */
	public Long getSimGID() {
		if (null == simGID || simGID.equals(-1)) {
			simGID = mainImageId;
		}
		return simGID;
	}

	public void setSimGID(Long simGID) {
		this.simGID = simGID;
	}

	public List<Integer> getVlDomColorList() {
		List<Integer> rets = new LinkedList<Integer>();
		if (this.vldomcolor != null) {
			String[] parts = this.vldomcolor.split(" ");
			if (null != parts) {
				for (String colorId : parts) {
					try {
						rets.add(Integer.parseInt(colorId.trim()));
					} catch (Exception err) {
					}
				}
			}
		}
		return rets;
	}

	public void setVldomcolor(String vldomcolor) {
		this.vldomcolor = vldomcolor;
	}

	public Integer getVlnpx() {
		if (null == vlnpx)
			vlnpx = 0;
		return vlnpx;
	}

	public void setVlnpx(Integer vlnpx) {
		this.vlnpx = vlnpx;
	}

	public Integer getVlsplic() {
		if (null == vlsplic)
			vlsplic = 0;
		return vlsplic;
	}

	public void setVlsplic(Integer vlsplic) {
		this.vlsplic = vlsplic;
	}

	public Integer getVlh() {
		return vlh;
	}

	public void setVlh(Integer vlh) {
		this.vlh = vlh;
	}

	/**
	 * @return deletedFlag
	 */

	public Integer getDeletedFlag() {
		return deletedFlag;
	}

	/**
	 * @param deletedFlag
	 *            deletedFlag
	 */

	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getVlw() {
		return vlw;
	}

	public void setVlw(Integer vlw) {
		this.vlw = vlw;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCategoryCode() {
		return productCategoryCode;
	}

	public void setProductCategoryCode(String productCategoryCode) {
		this.productCategoryCode = productCategoryCode;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public Integer getSalesVolume() {
		if (salesVolume == null || salesVolume < 0)
			salesVolume = 0;
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public String getSourceProductId() {
		return sourceProductId;
	}

	public void setSourceProductId(String sourceProductId) {
		this.sourceProductId = sourceProductId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPropsName() {
		return propsName;
	}

	public void setPropsName(String propsName) {
		this.propsName = propsName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPromototedService() {
		return promototedService;
	}

	public void setPromototedService(String promototedService) {
		this.promototedService = promototedService;
	}

	public String getImageIds() {
		return imageIds;
	}

	public void setImageIds(String imageIds) {
		this.imageIds = imageIds;
	}

	public Long getMainImageId() {
		return mainImageId;
	}

	public void setMainImageId(Long mainImageId) {
		this.mainImageId = mainImageId;
	}

	public Integer getNewProductFlag() {
		if (null == newProductFlag)
			newProductFlag = 0;
		return newProductFlag;
	}

	public void setNewProductFlag(Integer newProductFlag) {
		this.newProductFlag = newProductFlag;
	}

	public Integer getFeedbackCount() {
		if (feedbackCount == null || feedbackCount < 0)
			feedbackCount = 0;
		return feedbackCount;
	}

	public void setFeedbackCount(Integer feedbackCount) {
		this.feedbackCount = feedbackCount;
	}

	public Integer getTagFlag() {
		if (null == tagFlag || tagFlag < 0 || null == tagTime)
			return 0;
		return tagFlag;
	}

	public void setTagFlag(Integer tagFlag) {
		this.tagFlag = tagFlag;
	}

	public Date getTagTime() {
		return tagTime;
	}

	public void setTagTime(Date tagTime) {
		this.tagTime = tagTime;
	}

	public Integer getTagTtsFlag() {
		if (null == tagTtsFlag || tagTtsFlag < 0 || null == tagTtsTime)
			return 0;
		return tagTtsFlag;
	}

	public void setTagTtsFlag(Integer tagTtsFlag) {
		this.tagTtsFlag = tagTtsFlag;
	}

	public Integer getTagTtsLevel() {
		if (tagTtsLevel == null || tagTtsLevel <= 0 || tagTtsLevel > 3)
			tagTtsLevel = 1;
		return tagTtsLevel;
	}

	public void setTagTtsLevel(Integer tagTtsLevel) {
		this.tagTtsLevel = tagTtsLevel;
	}

	public Date getTagTtsTime() {
		return tagTtsTime;
	}

	public void setTagTtsTime(Date tagTtsTime) {
		this.tagTtsTime = tagTtsTime;
	}

	public Integer getTtsProductFlag() {
		return ttsProductFlag == null ? 0 : ttsProductFlag;
	}

	public void setTtsProductFlag(Integer ttsProductFlag) {
		this.ttsProductFlag = ttsProductFlag;
	}

	public String getTtsProductFeature() {
		return ttsProductFeature;
	}

	public void setTtsProductFeature(String ttsProductFeature) {
		this.ttsProductFeature = ttsProductFeature;
	}

	public Date getTtsProductTime() {
		return ttsProductTime;
	}

	public void setTtsProductTime(Date ttsProductTime) {
		this.ttsProductTime = ttsProductTime;
	}

	public String getMainImageUrl() {
		return mainImageUrl;
	}

	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}

	public boolean isTtsProductSmart() {
		return null != ttsProductFlag;
	}

	/**
	 * @return onsaleFlag
	 */

	public Integer getOnsaleFlag() {
		return onsaleFlag;
	}

	/**
	 * @param onsaleFlag
	 *            onsaleFlag
	 */

	public void setOnsaleFlag(Integer onsaleFlag) {
		this.onsaleFlag = onsaleFlag;
	}

	/**
	 * @return vldomcolor
	 */

	public String getVldomcolor() {
		return vldomcolor;
	}

	/**
	 * @return mainImagePath
	 */

	public String getMainImagePath() {
		return mainImagePath;
	}

	/**
	 * @param mainImagePath
	 *            mainImagePath
	 */

	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}

	/**
	 * @return productLevel
	 */

	public String getProductLevel() {
		return productLevel;
	}

	/**
	 * @param productLevel
	 *            productLevel
	 */

	public void setProductLevel(String productLevel) {
		this.productLevel = productLevel;
	}

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(Integer promoPrice) {
		this.promoPrice = promoPrice;
	}

	public Integer getActualPrice() {
		if (actualPrice != null && actualPrice > 0) {
			return actualPrice;
		}
		if (promoPrice != null && promoPrice > 0) {
			return promoPrice;
		}
		return price;
	}

	public void setActualPrice(Integer actualPrice) {
		this.actualPrice = actualPrice;
	}
}

package org.epiclouds.newsSpider.bean;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


public class AmazonBean {
	
	
	private String itemId;
	private String itemViewUrl;
	private List<String> itemPicUrls;
	private List<String> itemPicUrlsCurrent;
	private String itemPrice;
	private String sellerName;
	private String itemName;
	private String categoryName;
	private List<ReviewBean> reviewNumbers=new LinkedList<ReviewBean>();
	private String shippingPrice;
	
	private boolean pHashValueProcessed;
	private Object lastModified;
	
	
	public boolean ispHashValueProcessed() {
		return pHashValueProcessed;
	}
	public void setpHashValueProcessed(boolean pHashValueProcessed) {
		this.pHashValueProcessed = pHashValueProcessed;
	}
	
	@JSONField(serialize = false, deserialize = false)
	private String table;
	
	public String getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(String shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<ReviewBean> getReviewNumbers() {
		return reviewNumbers;
	}
	public void setReviewNumbers(List<ReviewBean> reviewNumbers) {
		this.reviewNumbers = reviewNumbers;
	}
	public String getItemViewUrl() {
		return itemViewUrl;
	}
	public void setItemViewUrl(String itemViewUrl) {
		this.itemViewUrl = itemViewUrl;
	}
	public List<String> getItemPicUrls() {
		return itemPicUrls;
	}
	public void setItemPicUrls(List<String> itemPicUrls) {
		if(this.itemPicUrls==null||this.itemPicUrls.size()==0){
			this.itemPicUrls = itemPicUrls;
		}
		this.itemPicUrlsCurrent=itemPicUrls;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Object getLastModified() {
		return lastModified;
	}
	public void setLastModified(Object lastModified) {
		this.lastModified = lastModified;
	}
	public List<String> getItemPicUrlsCurrent() {
		return itemPicUrlsCurrent;
	}
	public void setItemPicUrlsCurrent(List<String> itemPicUrlsCurrent) {
		this.itemPicUrlsCurrent = itemPicUrlsCurrent;
	}
}

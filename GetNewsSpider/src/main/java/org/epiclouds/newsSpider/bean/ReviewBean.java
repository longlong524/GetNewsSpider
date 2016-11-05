package org.epiclouds.newsSpider.bean;

import org.joda.time.DateTime;

public class ReviewBean {
	private   String time;
	private   String reviewnumber;
	public  ReviewBean(){}
	
	public ReviewBean(String reviewnumber){
		this.setReviewnumber(reviewnumber);
		this.time=new DateTime().toString("yyyy-MM-dd HH:mm:ss");
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getReviewnumber() {
		return reviewnumber;
	}

	public void setReviewnumber(String reviewnumber) {
		this.reviewnumber = reviewnumber;
	}
	
	
}

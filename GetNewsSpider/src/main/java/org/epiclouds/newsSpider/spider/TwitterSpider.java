package org.epiclouds.newsSpider.spider;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.epiclouds.handlers.AbstractHandler;
import org.epiclouds.newsSpider.handler.TwitterWebDriverHandler;
import org.epiclouds.spiders.annotations.SpiderFeildConfig;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.alibaba.fastjson.annotation.JSONField;
/**
 
 * @author xianglong
 * @created 2016年6月29日 上午11:18:07
 * @version 1.0
 */
public class TwitterSpider  extends AbstractSpiderObject{
	private String web_name;
	private DateTime start_date;
	private DateTime end_date;
	private int sleep_time;



	public TwitterSpider(){
		
	}
	public TwitterSpider(AbstractSpiderObject parent,String web_name,DateTime start_date,DateTime end_date,int s_time){
		super(parent);
		this.web_name=web_name;
		this.start_date=start_date;
		this.end_date=end_date;
		this.sleep_time=s_time;
	}
	@Override
	public AbstractHandler createSpiderHandler() {
		TwitterWebDriverHandler th= new TwitterWebDriverHandler("/search?q=from%3A"+web_name+""
				+ "%20since%3A"+start_date.toString("yyyy-MM-dd")+"%20until%3A"+end_date.toString("yyyy-MM-dd")+"&src=typd", "twitter.com",
				this, sleep_time,web_name);
		th.setSchema("https");
		return th;
	}

	@Override
	public String getInfo() {
		return web_name;
	}
	@Override
	public void start() throws Exception {
		super.start();
	}
	

	
	@Override
	public void finish() {
		super.finish();
		
	}
	public String getWeb_name() {
		return web_name;
	}
	public void setWeb_name(String web_name) {
		this.web_name = web_name;
	}
	public DateTime getStart_date() {
		return start_date;
	}
	public void setStart_date(DateTime start_date) {
		this.start_date = start_date;
	}
	public DateTime getEnd_date() {
		return end_date;
	}
	public void setEnd_date(DateTime end_date) {
		this.end_date = end_date;
	}
	public int getSleep_time() {
		return sleep_time;
	}
	public void setSleep_time(int sleep_time) {
		this.sleep_time = sleep_time;
	}

	
	

}

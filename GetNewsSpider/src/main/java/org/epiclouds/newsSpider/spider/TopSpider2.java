package org.epiclouds.newsSpider.spider;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.epiclouds.handlers.AbstractHandler;
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
public class TopSpider2  extends AbstractSpiderObject{
	@SpiderFeildConfig(desc="website name")
	private String web_name;
	@SpiderFeildConfig(desc="start date 如2011-11-11")
	private String start_date;
	@SpiderFeildConfig(desc="end date 如2011-11-11")
	private String end_date;
	@SpiderFeildConfig(desc="days delta 如5")
	private String days_delta;
	@SpiderFeildConfig(desc="sleep_time 秒 如20")
	private Integer sleep_time;
	@SpiderFeildConfig(desc="覆盖天数 如2")
	private Integer cover_days;

	private AtomicInteger ai=new AtomicInteger(0);

	private AtomicInteger ai2=new AtomicInteger(0);



	public TopSpider2(){
		
	}
	@Override
	public AbstractHandler createSpiderHandler() {
		
		return null;
	}

	@Override
	public String getInfo() {
		return web_name+" get all url::"+ai+
				"::real URL"+ai2;
	}
	@Override
	public void start() throws Exception {
		super.start();
		int days=Integer.parseInt(days_delta);
		DateTime dt1=DateTime.parse(start_date);
		DateTime dt2=DateTime.parse(end_date);
		int del=Days.daysBetween(dt1, dt2).getDays();
		for(int i=0;i<(del-1)/days+1;i++){
			DateTime start_dt1=dt1.plusDays(days*i);
			DateTime end_dt=dt1.plusDays(days*i+days+cover_days);
			if(end_dt.isAfter(dt2)){
				end_dt=dt2;
			}
			TwitterSpider ts=new TwitterSpider(this, web_name, start_dt1, end_dt, sleep_time);
			this.addChild(ts);
		}
		if((del-1)/days+1<=0){
			this.finish();
		}
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
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getDays_delta() {
		return days_delta;
	}
	public void setDays_delta(String days_delta) {
		this.days_delta = days_delta;
	}

	public Integer getCover_days() {
		return cover_days;
	}
	public void setCover_days(Integer cover_days) {
		this.cover_days = cover_days;
	}
	public void setCover_days(String cover_days) {
		this.cover_days = Integer.parseInt(cover_days);
	}
	public Integer getSleep_time() {
		return sleep_time;
	}
	public void setSleep_time(Integer sleep_time) {
		this.sleep_time = sleep_time;
	}
	public void setSleep_time(String sleep_time) {
		this.sleep_time = Integer.parseInt(sleep_time);
	}
	public AtomicInteger getAi() {
		return ai;
	}
	public void setAi(AtomicInteger ai) {
		this.ai = ai;
	}
	public AtomicInteger getAi2() {
		return ai2;
	}
	public void setAi2(AtomicInteger ai2) {
		this.ai2 = ai2;
	}

}

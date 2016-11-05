package org.epiclouds.spider_main;

import org.epiclouds.newsSpider.spider.TopSpider2;
import org.epiclouds.spiders.bootstrap.imp.Bootstrap;

public class StartSpider {
	private static Bootstrap boot;

	public static void main(String[] args) throws Exception {
		boot=new Bootstrap();
		boot.setBootSpiderClass(TopSpider2.class);
		boot.start();
	}
}

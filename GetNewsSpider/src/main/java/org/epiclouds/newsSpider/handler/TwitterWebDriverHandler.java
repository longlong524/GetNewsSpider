package org.epiclouds.newsSpider.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.epiclouds.handlers.AbstractWebDriverCrawlerHandler;
import org.epiclouds.newsSpider.spider.HuffingSpider;
import org.epiclouds.newsSpider.spider.TopSpider2;
import org.epiclouds.newsSpider.spider.URLSpider;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

public class TwitterWebDriverHandler extends AbstractWebDriverCrawlerHandler{
	public static HashMap<String,String> hs;

	private int ok=0;
	private int limit=3;
	private String pre_content;
	private boolean first=true;
	private int sleep_time;
	private String web_name;
	
	
	
	@Override
	protected void onError(Object e) {
		if (e instanceof SessionNotFoundException|| e instanceof org.openqa.selenium.NotFoundException) {
			first=true;
		}else{
			super.onError(e);
		}
	}


	public TwitterWebDriverHandler(String url, String host,
			AbstractSpiderObject spider,int sleep_time,String web_name) {
		super(host,url,spider);
		this.sleep_time=sleep_time;
		this.web_name=web_name;
	}

	
	@Override
	protected void requestSelf() {
		if(first){
			super.requestSelf();
			first=false;
		}
	}


	@Override
	protected void handlePage(ChromeDriver wd) throws Exception {
		wd.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		Thread.sleep(sleep_time*1000);
	}


	@Override
	public void handle(String content) throws Exception {
		if(content!=null){
			if(content.equals(pre_content)){
				ok++;
				if(ok>limit){
					stop();
					return;
				}
			}
			pre_content=content;
		}
		requestSelf();
		return;
	}

	@Override
	protected void onBefore() {
		
	}

	@Override
	protected void onNormalFinished() {
		List<String> urls=new LinkedList<>();
		Document doc=Jsoup.parse(pre_content);
		/*Elements eles=doc.select(".js-display-url");
		for(int i=0;i<eles.size();i++){
			urls.add(eles.get(i).text());
		}*/
		
		Elements eles=doc.select(".js-tweet-text-container");
		for(int i=0;i<eles.size();i++){
			Element e=eles.get(i).select("a[title]").first();
			if(e!=null)
				urls.add(e.attr("title"));
		}
		TopSpider2 ts=(TopSpider2) this.getSpider().getParent();
		ts.getAi().addAndGet(urls.size());
		boolean xxx=false;
		for(String url:urls){
			if(!url.contains("slack-redir.net")){
				URLSpider us=new URLSpider(this.getSpider(), url,web_name);
				this.getSpider().addChild(us);
				xxx=true;
			}
			
		}
		if(urls.size()==0&&!xxx){
			this.getSpider().finish();
		}
	}

	@Override
	protected void onDataFinished() {
		
	}


	@Override
	protected void onReadTimeOut() {
		
	}


}

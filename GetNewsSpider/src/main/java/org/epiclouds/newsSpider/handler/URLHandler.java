package org.epiclouds.newsSpider.handler;

import io.netty.handler.codec.http.FullHttpResponse;

import java.util.HashMap;

import org.epiclouds.handlers.AbstractNettyCrawlerHandler;
import org.epiclouds.handlers.CrawlerClient;
import org.epiclouds.newsSpider.spider.HuffingSpider;
import org.epiclouds.newsSpider.spider.RainBowSpider;
import org.epiclouds.newsSpider.spider.URLSpider;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;

public class URLHandler extends AbstractNettyCrawlerHandler{
	public static HashMap<String,String> hs;
	
	private int error=0;
	private String web_name;
	private String[] HUFFING_SITES=new String[]{"huffingtonpost","HuffPostPol","huffpostblog","hpyoungvoices",
			"HuffPostUK","huffpostukpol","HuffPostTech","hplifestyle","huffpolifestyle","HuffPostScience","HuffPostBiz",
			"HealthyLiving","HuffPostEnt"};
	
	static{
		hs=new HashMap<String,String>();
		//headers
	    hs.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	    hs.put("Accept-Encoding", "gzip, deflate");
	}
	public URLHandler(String url, String host,
			AbstractSpiderObject spider,String web_name) {
		super(host,url,spider);
		this.getHeaders().putAll(hs);
		this.web_name=web_name;
	}

	
	
	@Override
	protected void onReadTimeOut() {
		error++;
		if(error>=10){
			stop();
			return;
		}else{
			super.onReadTimeOut();
		}
	}



	@Override
	protected void onError(Object response) {
		FullHttpResponse cres=(FullHttpResponse)response;
		int code=cres.status().code()/100;
		if(code==3){
			CrawlerClient.mainlogger.error("error:"+cres.status()+":"+this.getUrl());
			String sss=cres.headers().get("Location")+"";
			if(sss.startsWith("//")){
				sss=this.schema+":"+sss;
			}
			String BIG=sss.toLowerCase();
			if(BIG.startsWith("http")||BIG.startsWith("https")){
				URLSpider us=new URLSpider(this.getSpider().getParent(), sss,web_name);
				this.getSpider().getParent().addChild(us);
				stop();
				return;
			}else{
				this.setUrl(sss);
			}
			requestSelf();
		}else{
			if(code==4){
				error++;
				if(error>=10){
					stop();
					return;
				}
				requestSelf();
			}else{
				if(code==5){
					error++;
					if(error>=10){
						stop();
						return;
					}
					requestSelf();
				}else{
					super.onError(response);
				}
			}
			
		}
	}



	@Override
	public void handle(String content) throws Exception {
		
		for(String str:HUFFING_SITES){
			if(str.equals(web_name)){
				String tmp_url=this.getSchema()+"://"+this.host
						+this.url;
				if(!tmp_url.contains("slack-redir.net")){
					HuffingSpider spider=new HuffingSpider(this.getSpider().getParent(), tmp_url
						, web_name);
					this.getSpider().getParent().addChild(spider);
					break;
				}
			}
		}
		/*RainBowSpider spider=new RainBowSpider(this.getSpider().getParent(), this.getSchema()+"://"+this.host
				+this.url
				, web_name);
		this.getSpider().getParent().addChild(spider);*/
		stop();
		return;
	}

	@Override
	protected void onBefore() {
		
	}

	@Override
	protected void onNormalFinished() {
		this.getSpider().finish();
	}

	@Override
	protected void onDataFinished() {
		
	}

	public static HashMap<String, String> getHs() {
		return hs;
	}

	public static void setHs(HashMap<String, String> hs) {
		URLHandler.hs = hs;
	}







}

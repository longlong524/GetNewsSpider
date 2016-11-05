package org.epiclouds.newsSpider.spider;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.epiclouds.handlers.AbstractHandler;
import org.epiclouds.newsSpider.handler.CommongJsonHandler;
import org.epiclouds.newsSpider.handler.URLHandler;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;



public class RainBowSpider  extends AbstractSpiderObject{
	private String url;
	private String web_name;
	public RainBowSpider(){
		
	}

	public RainBowSpider(AbstractSpiderObject parent,String url,
			String web_name){
		super(parent);
		this.url=url;
		this.web_name=web_name;
	}
	



	@Override
	public AbstractHandler createSpiderHandler() {
			String tmp="/rainbow-data-service/rainbow/content-by-url.json?"
					+ "followLinks=false&platform=iphoneclassic"
					+ "&url="+url;
			CommongJsonHandler handler=null;
			try {
				handler = new CommongJsonHandler(tmp ,"rainbowapi-a.wpdigital.net",this,URLEncoder.encode(url, "utf-8"),
						web_name);
				handler.setSchema("https");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return handler;
	}
	
	

	@Override
	public String getInfo() {
		return url;
	}


	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}




	
}

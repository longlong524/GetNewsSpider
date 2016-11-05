package org.epiclouds.newsSpider.spider;


import java.net.MalformedURLException;
import java.net.URL;

import org.epiclouds.handlers.AbstractHandler;
import org.epiclouds.newsSpider.handler.URLHandler;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;



public class URLSpider  extends AbstractSpiderObject{
	private String url;
	private String web_name;
	public URLSpider(){
		
	}

	public URLSpider(AbstractSpiderObject parent,String url,String web_name){
		super(parent);
		this.url=url;
		this.web_name=web_name;
	}
	



	@Override
	public AbstractHandler createSpiderHandler() {
		try {
			if(url.contains("slack-redir.net")){
				this.finish();
				return null;
			}
			URL u=new URL(url);
			URLHandler handler=new URLHandler(u.getPath(), u.getHost(), this,web_name);
			handler.setSchema(u.getProtocol());
			return handler;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
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

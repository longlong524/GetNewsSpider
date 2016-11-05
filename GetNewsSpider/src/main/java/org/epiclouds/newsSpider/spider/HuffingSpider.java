package org.epiclouds.newsSpider.spider;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.epiclouds.handlers.AbstractHandler;
import org.epiclouds.newsSpider.handler.CommongJsonHandler;
import org.epiclouds.newsSpider.handler.URLHandler;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;



public class HuffingSpider  extends AbstractSpiderObject{
	private String url;
	private String web_name;
	public HuffingSpider(){
		
	}

	public HuffingSpider(AbstractSpiderObject parent,String url,
			String web_name){
		super(parent);
		this.url=url;
		this.web_name=web_name;
	}
	



	@Override
	public AbstractHandler createSpiderHandler() {
		try{
			try {
				url=URLDecoder.decode(url, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int wen_index=url.indexOf("?");
			int last=0;
			String sub=null;
			if(wen_index==-1){
				last=url.lastIndexOf("/");
				sub=url.substring(last+1);
			}else{
				String tmp=url.substring(0,wen_index);
				last=tmp.lastIndexOf("/");
				sub=tmp.substring(last+1);
			}
			
			if(sub.indexOf(".")>=0){
				sub=sub.substring(0,sub.indexOf("."));
			}
			String[] coms=sub.trim().split("_");
			String some_id=coms[coms.length-2]+"_"+coms[coms.length-1];
	
			String tmp="/mapi/v2/us/entry/"+some_id
					+ "?device=iOS,small";
			CommongJsonHandler handler=null;
			try {
				TopSpider2 ts=(TopSpider2) this.getParent().getParent();
				ts.getAi2().addAndGet(1);
				handler = new CommongJsonHandler(tmp ,"mapi.huffpost.com",this,URLEncoder.encode(url, "utf-8"),
						web_name);
				return handler;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(url);
		}
		this.finish();
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

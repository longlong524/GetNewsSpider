package org.epiclouds.newsSpider.handler;

import io.netty.handler.codec.http.FullHttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.epiclouds.handlers.AbstractNettyCrawlerHandler;
import org.epiclouds.handlers.CrawlerClient;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;

import com.alibaba.fastjson.JSONObject;

public class CommongJsonHandler extends AbstractNettyCrawlerHandler{
	public static HashMap<String,String> hs;
	
	private String name;
	private int error=0;
	private String web_name;
	
	static{
		hs=new HashMap<String,String>();
		//headers
	    hs.put("Accept", "application/json, text/javascript, */*; q=0.01");
	    hs.put("Accept-Encoding", "gzip, deflate");
	}
	public CommongJsonHandler(String url, String host,
			AbstractSpiderObject spider,String name,String web_name) {
		super(host,url,spider);
		this.getHeaders().putAll(hs);
		this.name=name;
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
			this.setUrl(sss);
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


	private static synchronized void writeFile(String web_name,String name,String content){
		System.err.println(web_name+" ::: "+name);
		File f=new File(web_name);
		if(!f.exists()){
			f.mkdir();
		}
		try {
			FileOutputStream fout=new FileOutputStream(web_name+"/"+name);
			fout.write(content.getBytes());
			fout.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handle(String content) throws Exception {
		JSONObject jo=JSONObject.parseObject(content);
		writeFile(web_name,name,content);
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
		CommongJsonHandler.hs = hs;
	}







}

package org.epiclouds.newsSpider.spider;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.epiclouds.handlers.AbstractHandler;
import org.epiclouds.spiders.annotations.SpiderFeildConfig;
import org.epiclouds.spiders.spiderobject.abstracts.AbstractSpiderObject;

import com.alibaba.fastjson.annotation.JSONField;
/**
 
 * @author xianglong
 * @created 2016年6月29日 上午11:18:07
 * @version 1.0
 */
public class TopSpider_tmp  extends AbstractSpiderObject{
	@SpiderFeildConfig(desc="url的文件")
	private String url_filename;
	@SpiderFeildConfig(desc="唯一名称")
	private String u_name;
	
	@JSONField(serialize = false, deserialize = false)
	private FileWriter[] fouts=new FileWriter[4];


	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public TopSpider_tmp(){
		
	}
	@Override
	public AbstractHandler createSpiderHandler() {
		return null;
	}

	@Override
	public String getInfo() {
		return url_filename;
	}
	@Override
	public void start() throws Exception {
		super.start();
		List<String> urls=new LinkedList<String>();
		BufferedReader reader=new BufferedReader(new FileReader(url_filename));
		String tmp=null;
		while((tmp=reader.readLine())!=null){
			if(!tmp.trim().isEmpty()){
				urls.add(tmp.trim());
			}
		}
		reader.close();
		for(String u:urls){
			if(!u.toLowerCase().startsWith("http")&&!u.toLowerCase().startsWith("//")){
				u="http://"+u;
			}
			if(!u.toLowerCase().startsWith("http")&&u.toLowerCase().startsWith("//")){
				u="http:"+u;
			}
			//URLSpider us=new URLSpider(this, u,u_name,this);
			//this.addChild(us);
		}
		if(urls.size()==0){
			this.finish();
		}
		for(int i=2;i<6;i++){
			fouts[i-2]=new FileWriter(u_name+i);
		}
	}
	
	public synchronized void writeFile(String text){
		int fi=text.indexOf(":");
		String wws=text.substring(0, fi);
		Integer ii=Integer.parseInt(wws);
		int code=ii/100;
		try {
			fouts[code-2].write(text.substring(fi+1)+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void finish() {
		super.finish();
		for(int i=0;i<4;i++){
			try {
				fouts[i].close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getUrl_filename() {
		return url_filename;
	}
	public void setUrl_filename(String url_filename) {
		this.url_filename = url_filename;
	}
	
	

}

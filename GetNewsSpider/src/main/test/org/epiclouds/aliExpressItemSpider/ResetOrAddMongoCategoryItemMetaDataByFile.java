package org.epiclouds.aliExpressItemSpider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class ResetOrAddMongoCategoryItemMetaDataByFile {
	private static   MongoClient client;
	public static void main(String[] args) throws IOException {
		
		{
			String itemUrl="http://www.aliexpress.com/4343/item/Women-White-Long-Sleeve-Bodycon-Dress-Sexy-Club-Dress-2015-Black-Bandage-Winter-Dress-Warm-Vestidos/32456986172.html";
			String str=itemUrl;;
			if(itemUrl.indexOf("?")!=-1){
				str=itemUrl.substring(0,itemUrl.indexOf("?"));
			}
			System.err.println(str.substring(str.lastIndexOf("/")+1,
					str.indexOf(".html")==-1?str.length():str.indexOf(".html")));
			if(1==1){
				return;
			}
		}
		if(client==null){
			MongoCredential credential = MongoCredential.createCredential("root", "admin","123Yuanshuju456".toCharArray());
			client = new MongoClient(new ServerAddress("106.3.38.50",27017), Arrays.asList(credential));
		}
		DB db=client.getDB("aliexpress");

		DBCollection col=db.getCollection("category_item_meta");
		BasicDBObject con=new BasicDBObject();
		BufferedReader br=new BufferedReader(new FileReader(new File("src/main/test/AliexpressText.txt")));
		String line=null;
		int i=0;
		while((line=br.readLine())!=null){
			if(line.split("/").length==3){
				String categoryId = line.split("/")[0];
				String categoryName = line.split("/")[1];
				String order = line.split("/")[2];
				con.put("categoryId", categoryId);
				System.err.println(line);
				DBObject oo=col.findOne(con);
				if(oo==null) {
					oo = new BasicDBObject();
					System.err.println("cateoryId: "+categoryId);
					oo.put("_id", categoryId);
					oo.put("finishTime", 0);
					oo.put("delta", 0);
					oo.put("startTime", System.currentTimeMillis());
					oo.put("id", categoryId);
					oo.put("categoryName", categoryName);
					oo.put("categoryId", categoryId);
					oo.put("orderMin", order);
					oo.put("info", categoryId);
					
				}else{
					oo.put("finishTime", 0);
				}
				col.update(con, oo, true, false);
				i++;
				System.err.println(i);
			}else{
				
			}
		}
	}
}

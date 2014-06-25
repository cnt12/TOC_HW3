//-------------------------------------
//NAME:Ming - Jie Deng
//ID:H54001347
//Date:2013/06/21 from Tainan,Taiwan
//Description:程式先把從網路上讀到的程式用InputStream打開，用BufferedReader讀取
//用readAll()函式把讀到的轉成String格式
//用JSONToken 讀入 再轉給 JSONArray 
//最後 對Array取出每個的元素 對此元素再丟到JSONObject 後處理並用分析比對
//整體主要是對JSON 格式的分析 與取值 做處理


import java.net.*;
import java.nio.charset.Charset;
import java.io.*;

import org.json.*;

import java.util.*;
import java.util.regex.*;


public class TocHw3 {
	
	 int aa =0;
	 

	public static void main(String[] args)throws Exception ,JSONException {
		
		 int n = args.length;
		 String DataUrl;
		 String Condition1,Condition2,Condition3;
		 String Downtown,RoadName,Year,Price;
		 
		 DataUrl = (n>=1)?args[0].toString():"http://www.datagarage.io/api/5365dee31bc6e9d9463a0057";
		 
		 Condition1 = (n>=2)?args[1].toString():"文山區";
		 
		 Condition2 = (n>=3)?args[2].toString():"辛亥路";
		 
		 Condition3 = (n>=4)?args[3].toString():"103";
		 
		 Downtown = "鄉鎮市區";
		 
		 RoadName = "土地區段位置或建物區門牌";
		 
		 Year = "交易年月";
		 
		 Price ="總價元";
		 
			
		 
		try{
			
			
			
			
			InputStream is = new URL(DataUrl).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF8")));
			//取得URL資訊
		
			
			String jtext = readAll(br);
			
			JSONTokener jk = new JSONTokener(jtext);
			
			JSONArray ay = new JSONArray(jk);
			
			//System.out.println("Done");
			
			int count =0;
			int total_price=0;
			
			for(int i=0;i<ay.length();i++){
				JSONTokener jt = new JSONTokener(ay.get(i).toString());	
				JSONObject jb = new JSONObject(jt);
				String cd1_match = jb.get(Downtown).toString();
				String cd2_match = jb.get(RoadName).toString();
				String cd3_match = jb.get(Year).toString();
				if(cd1_match.compareTo(Condition1)==0){
					Pattern cd2_pattern = Pattern.compile(Condition2);
					Matcher cd2_matcher = cd2_pattern.matcher(cd2_match);
					Pattern cd3_pattern = Pattern.compile(Condition3);
					Matcher cd3_matcher = cd3_pattern.matcher(cd3_match);
					
					if(cd2_matcher.find()&& cd3_matcher.find()){
					++count;
					total_price+=(int)jb.get(Price);
					//System.out.println(jb.get(Downtown)+" "+jb.get(RoadName)+" "+jb.get(Year)+" "+jb.get(Price));
					}	
				
				}
				
			}
			
			if(count!=0){
			//System.out.println("Output:");
			System.out.println(total_price/count);
			
			}
			
			
			
			
			
			
			
			 	
			
			
			
			
		}catch(FileNotFoundException e){}
		catch(JSONException e){e.printStackTrace();}
	
		

	}
	public  static String readAll(Reader rd) throws IOException{
		
		//System.out.println("Starting reading the file");
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while((cp=rd.read())!=-1){
			sb.append((char)cp);
			
		}
		
		//System.out.println("Done reading the files");
		
		return sb.toString();
	}
	
	
	

}


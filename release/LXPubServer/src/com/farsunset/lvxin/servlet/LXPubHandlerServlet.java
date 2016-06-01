package com.farsunset.lvxin.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.farsunset.lvxin.model.PubMenuEvent;
import com.farsunset.lvxin.model.XFResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.TextUnderstander;
import com.iflytek.cloud.speech.TextUnderstanderListener;
import com.iflytek.cloud.speech.UnderstanderResult;

public class LXPubHandlerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static{
		SpeechUtility.createUtility( SpeechConstant.APPID +"=55976c4e");
	}
	 
	public void doGet(HttpServletRequest request,HttpServletResponse respnose) throws IOException
	{
		doPost(request,respnose);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse respnose) throws IOException
	{
		respnose.setContentType("text/json;charset=UTF-8");
		String json = IOUtils.toString(request.getInputStream());
		PubMenuEvent event   =	JSON.parseObject(json, PubMenuEvent.class);
	    if(event.eventType.equals(PubMenuEvent.EVENT_TYPE_SUBSCRIBE))
	    {
	    	doOnSubscribeEvent(event);
	    }
	    
	    if(event.eventType.equals(PubMenuEvent.EVENT_TYPE_UNSUBSCRIBE))
	    {
	    	doOnUnSubscribeEvent(event);
	    }
	    
	    if(event.eventType.equals(PubMenuEvent.EVENT_TYPE_TEXT))
	    {
	    	  doOnRecivedTextEvent(respnose,event);
	    }
	    
	    if(event.eventType.equals(PubMenuEvent.EVENT_TYPE_MENU))
	    {
	        String responseJson = doOnMenuEvent(event);
	    	respnose.getWriter().print(responseJson);
	    }
	}
	
	
	
	private void doOnRecivedTextEvent(final HttpServletResponse respnose,PubMenuEvent event) {
		    final XFResult data = new XFResult();
			final TextUnderstander mTextUnderstander =TextUnderstander.createTextUnderstander();
			 //初始化监听器
			 TextUnderstanderListener searchListener = new TextUnderstanderListener(){
					 //语义结果回调
					 public void onResult(UnderstanderResult result){
						
						 XFResult temp = JSON.parseObject(result.getResultString(), XFResult.class);
						 data.answer = temp.answer;
						 data.operation = temp.operation;
						 data.service = temp.service;
						 data.rc = temp.rc;
						 data.returned = true;
						 mTextUnderstander.cancel();
						 mTextUnderstander.destroy();
						 System.out.println("result:"+temp.toJSONString());
					 }
					 //语义错误回调
					 public void onError(SpeechError error) {
						 data.returned = true;
						 mTextUnderstander.cancel();
						 mTextUnderstander.destroy();
						 error.printStackTrace();
					 }
			 };
			 System.out.println("wenzi:"+event.text);
			//开始语义理解
			 mTextUnderstander.understandText(event.text, searchListener);
			 
			 while(!data.returned){System.out.println("waiting.....");}
			 
			 
			 if(data.getResult()!=null){
				 try {respnose.getWriter().print(data.toJSONString());} catch (IOException e) {e.printStackTrace();}
			 }else{
				 try {respnose.getOutputStream().write(IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("com/farsunset/lvxin/res/text.json"), "UTF-8").getBytes());} catch (IOException e) {e.printStackTrace();}
			 } 
	}
	private String  doOnMenuEvent(PubMenuEvent event) throws IOException {
		String responseJson ;
		 if(event.menuCode.equals("KEY_SUB_NEWS"))
		 {
			  responseJson = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("com/farsunset/lvxin/res/news.json"), "UTF-8");
			  return responseJson;
		 }
		 if(event.menuCode.equals("KEY_SUB_WENZHANG"))
		 {
			 responseJson = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("com/farsunset/lvxin/res/atricle.json"), "UTF-8");
			 return responseJson;
		 }
		
		 return null;
	}
	
	private void doOnSubscribeEvent(PubMenuEvent event) {
		// TODO Auto-generated method stub
		
	}
	private void doOnUnSubscribeEvent(PubMenuEvent event) {
		// TODO Auto-generated method stub
		
	}
}

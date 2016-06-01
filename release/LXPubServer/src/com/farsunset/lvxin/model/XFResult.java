 
package com.farsunset.lvxin.model;

import java.io.Serializable;
 
/**
 *公众账号操作事件
 */
public class XFResult implements Serializable{
 
	
	
	    /**
	    * @Fields serialVersionUID : TODO(说明)
	    */
	    
	private static final long serialVersionUID = 1L;
	public int rc; 
	public String operation; 
	
	public String service; 
	
	public Answer answer; 
	public boolean returned; 
	public class Answer {
		public String text; 
	} 
	
	public String getResult(){
		return answer==null || answer.text == null?null:answer.text;
	}
	public String toJSONString(){
		
		return "{\"contentType\":\"0\",\"content\":\""+answer.text+"\"}";
	}
}

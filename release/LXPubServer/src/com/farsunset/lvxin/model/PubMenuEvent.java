 
package com.farsunset.lvxin.model;

import java.io.Serializable;
 
/**
 *公众账号操作事件
 */
public class PubMenuEvent implements Serializable{
 
	public static final long serialVersionUID = 1L;
	
	public	final static String EVENT_TYPE_TEXT = "text";//发送文字事件
	public	final static String EVENT_TYPE_MENU = "menu";//点击菜单事件
	public	final static String EVENT_TYPE_SUBSCRIBE = "subscribe";//订阅事件
	public	final static String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";//取消订阅事件
	
	public String eventType;//事件类型
	
	public String text;//用户发送的文本内容
	
	public String account;//用户账户
	
	public String menuCode;//自定义菜单的key
	 
}

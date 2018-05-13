package cn.garymb.ygomobile.ui.online.mcchat;

import org.jivesoftware.smack.packet.Message;
import cn.garymb.ygomobile.ui.online.mcchat.management.*;

public class ChatMessage
{
	private String name;
	//private String time;
	private String message;
	private String avatar;

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getAvatar()
	{
		return avatar;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setName(String name)
	{
		this.name = name;
		this.avatar=getAvatarUrl(name);
	}

	public String getName()
	{
		return name;
	}

/*
	public void setTime(String time)
	{
		this.time = time;
	}

	public String getTime()
	{
		return time;
	}
	*/
	public static ChatMessage toChatMessage(Message message){
		
		if(message.getBody()!=null)
		{
			String xs=message.toXML().toString();

			int cc=xs.indexOf("stamp='");
			if(cc!=-1){
			String ss=xs.substring(cc+7,xs.indexOf("'",cc+7));
			}else{
				
			}
			String names=message.getFrom().toString();
			String name=names.substring(names.indexOf(ServiceManagement.GROUP_ADDRESS)+ServiceManagement.GROUP_ADDRESS.length()+1,names.length());
			ChatMessage cm=new ChatMessage();
			cm.setName(name);
			//cm.setTime(ss);
			cm.setMessage(message.getBody());
			return cm;

		}
		return null;
	}
	
	
	public String getAvatarUrl(String userName){
		return "https://api.moecube.com/accounts/users/"+userName+".png";
	}
	
	}

package org.duongthuy.tichhop.api.message;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DossierMessage implements Serializable {
	protected String messagefunction;
	@XmlElement(name="messagefunction")
	public String getMessagefunction() {
		return messagefunction;
	}
	public void setMessagefunction(String messagefunction) {
		this.messagefunction = messagefunction;
	}
	@XmlElement(name="messageid")
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public String getMessagecontent() {
		return messagecontent;
	}
	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent;
	}
	protected String messageid;
	protected String messagecontent;
}

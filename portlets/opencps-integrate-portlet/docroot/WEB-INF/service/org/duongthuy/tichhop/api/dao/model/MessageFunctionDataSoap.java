/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.duongthuy.tichhop.api.dao.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author binhth
 * @generated
 */
public class MessageFunctionDataSoap implements Serializable {
	public static MessageFunctionDataSoap toSoapModel(MessageFunctionData model) {
		MessageFunctionDataSoap soapModel = new MessageFunctionDataSoap();

		soapModel.setMessagePackagesId(model.getMessagePackagesId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setMessageFunction(model.getMessageFunction());
		soapModel.setMessageId(model.getMessageId());
		soapModel.setMessageFileIdData(model.getMessageFileIdData());
		soapModel.setSendDate(model.getSendDate());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static MessageFunctionDataSoap[] toSoapModels(
		MessageFunctionData[] models) {
		MessageFunctionDataSoap[] soapModels = new MessageFunctionDataSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MessageFunctionDataSoap[][] toSoapModels(
		MessageFunctionData[][] models) {
		MessageFunctionDataSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MessageFunctionDataSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MessageFunctionDataSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MessageFunctionDataSoap[] toSoapModels(
		List<MessageFunctionData> models) {
		List<MessageFunctionDataSoap> soapModels = new ArrayList<MessageFunctionDataSoap>(models.size());

		for (MessageFunctionData model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MessageFunctionDataSoap[soapModels.size()]);
	}

	public MessageFunctionDataSoap() {
	}

	public long getPrimaryKey() {
		return _messagePackagesId;
	}

	public void setPrimaryKey(long pk) {
		setMessagePackagesId(pk);
	}

	public long getMessagePackagesId() {
		return _messagePackagesId;
	}

	public void setMessagePackagesId(long messagePackagesId) {
		_messagePackagesId = messagePackagesId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getMessageFunction() {
		return _messageFunction;
	}

	public void setMessageFunction(String messageFunction) {
		_messageFunction = messageFunction;
	}

	public String getMessageId() {
		return _messageId;
	}

	public void setMessageId(String messageId) {
		_messageId = messageId;
	}

	public String getMessageFileIdData() {
		return _messageFileIdData;
	}

	public void setMessageFileIdData(String messageFileIdData) {
		_messageFileIdData = messageFileIdData;
	}

	public Date getSendDate() {
		return _sendDate;
	}

	public void setSendDate(Date sendDate) {
		_sendDate = sendDate;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private long _messagePackagesId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _messageFunction;
	private String _messageId;
	private String _messageFileIdData;
	private Date _sendDate;
	private String _version;
}
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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MessageFunctionData}.
 * </p>
 *
 * @author binhth
 * @see MessageFunctionData
 * @generated
 */
public class MessageFunctionDataWrapper implements MessageFunctionData,
	ModelWrapper<MessageFunctionData> {
	public MessageFunctionDataWrapper(MessageFunctionData messageFunctionData) {
		_messageFunctionData = messageFunctionData;
	}

	@Override
	public Class<?> getModelClass() {
		return MessageFunctionData.class;
	}

	@Override
	public String getModelClassName() {
		return MessageFunctionData.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("messagePackagesId", getMessagePackagesId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("messageFunction", getMessageFunction());
		attributes.put("messageId", getMessageId());
		attributes.put("messageFileIdData", getMessageFileIdData());
		attributes.put("sendDate", getSendDate());
		attributes.put("version", getVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long messagePackagesId = (Long)attributes.get("messagePackagesId");

		if (messagePackagesId != null) {
			setMessagePackagesId(messagePackagesId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String messageFunction = (String)attributes.get("messageFunction");

		if (messageFunction != null) {
			setMessageFunction(messageFunction);
		}

		String messageId = (String)attributes.get("messageId");

		if (messageId != null) {
			setMessageId(messageId);
		}

		String messageFileIdData = (String)attributes.get("messageFileIdData");

		if (messageFileIdData != null) {
			setMessageFileIdData(messageFileIdData);
		}

		Date sendDate = (Date)attributes.get("sendDate");

		if (sendDate != null) {
			setSendDate(sendDate);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	/**
	* Returns the primary key of this message function data.
	*
	* @return the primary key of this message function data
	*/
	@Override
	public long getPrimaryKey() {
		return _messageFunctionData.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message function data.
	*
	* @param primaryKey the primary key of this message function data
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_messageFunctionData.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the message packages ID of this message function data.
	*
	* @return the message packages ID of this message function data
	*/
	@Override
	public long getMessagePackagesId() {
		return _messageFunctionData.getMessagePackagesId();
	}

	/**
	* Sets the message packages ID of this message function data.
	*
	* @param messagePackagesId the message packages ID of this message function data
	*/
	@Override
	public void setMessagePackagesId(long messagePackagesId) {
		_messageFunctionData.setMessagePackagesId(messagePackagesId);
	}

	/**
	* Returns the user ID of this message function data.
	*
	* @return the user ID of this message function data
	*/
	@Override
	public long getUserId() {
		return _messageFunctionData.getUserId();
	}

	/**
	* Sets the user ID of this message function data.
	*
	* @param userId the user ID of this message function data
	*/
	@Override
	public void setUserId(long userId) {
		_messageFunctionData.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message function data.
	*
	* @return the user uuid of this message function data
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _messageFunctionData.getUserUuid();
	}

	/**
	* Sets the user uuid of this message function data.
	*
	* @param userUuid the user uuid of this message function data
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_messageFunctionData.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message function data.
	*
	* @return the user name of this message function data
	*/
	@Override
	public java.lang.String getUserName() {
		return _messageFunctionData.getUserName();
	}

	/**
	* Sets the user name of this message function data.
	*
	* @param userName the user name of this message function data
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_messageFunctionData.setUserName(userName);
	}

	/**
	* Returns the create date of this message function data.
	*
	* @return the create date of this message function data
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _messageFunctionData.getCreateDate();
	}

	/**
	* Sets the create date of this message function data.
	*
	* @param createDate the create date of this message function data
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_messageFunctionData.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message function data.
	*
	* @return the modified date of this message function data
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _messageFunctionData.getModifiedDate();
	}

	/**
	* Sets the modified date of this message function data.
	*
	* @param modifiedDate the modified date of this message function data
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_messageFunctionData.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the message function of this message function data.
	*
	* @return the message function of this message function data
	*/
	@Override
	public java.lang.String getMessageFunction() {
		return _messageFunctionData.getMessageFunction();
	}

	/**
	* Sets the message function of this message function data.
	*
	* @param messageFunction the message function of this message function data
	*/
	@Override
	public void setMessageFunction(java.lang.String messageFunction) {
		_messageFunctionData.setMessageFunction(messageFunction);
	}

	/**
	* Returns the message ID of this message function data.
	*
	* @return the message ID of this message function data
	*/
	@Override
	public java.lang.String getMessageId() {
		return _messageFunctionData.getMessageId();
	}

	/**
	* Sets the message ID of this message function data.
	*
	* @param messageId the message ID of this message function data
	*/
	@Override
	public void setMessageId(java.lang.String messageId) {
		_messageFunctionData.setMessageId(messageId);
	}

	/**
	* Returns the message file ID data of this message function data.
	*
	* @return the message file ID data of this message function data
	*/
	@Override
	public java.lang.String getMessageFileIdData() {
		return _messageFunctionData.getMessageFileIdData();
	}

	/**
	* Sets the message file ID data of this message function data.
	*
	* @param messageFileIdData the message file ID data of this message function data
	*/
	@Override
	public void setMessageFileIdData(java.lang.String messageFileIdData) {
		_messageFunctionData.setMessageFileIdData(messageFileIdData);
	}

	/**
	* Returns the send date of this message function data.
	*
	* @return the send date of this message function data
	*/
	@Override
	public java.util.Date getSendDate() {
		return _messageFunctionData.getSendDate();
	}

	/**
	* Sets the send date of this message function data.
	*
	* @param sendDate the send date of this message function data
	*/
	@Override
	public void setSendDate(java.util.Date sendDate) {
		_messageFunctionData.setSendDate(sendDate);
	}

	/**
	* Returns the version of this message function data.
	*
	* @return the version of this message function data
	*/
	@Override
	public java.lang.String getVersion() {
		return _messageFunctionData.getVersion();
	}

	/**
	* Sets the version of this message function data.
	*
	* @param version the version of this message function data
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_messageFunctionData.setVersion(version);
	}

	@Override
	public boolean isNew() {
		return _messageFunctionData.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_messageFunctionData.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _messageFunctionData.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_messageFunctionData.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _messageFunctionData.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _messageFunctionData.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_messageFunctionData.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _messageFunctionData.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_messageFunctionData.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_messageFunctionData.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_messageFunctionData.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MessageFunctionDataWrapper((MessageFunctionData)_messageFunctionData.clone());
	}

	@Override
	public int compareTo(
		org.duongthuy.tichhop.api.dao.model.MessageFunctionData messageFunctionData) {
		return _messageFunctionData.compareTo(messageFunctionData);
	}

	@Override
	public int hashCode() {
		return _messageFunctionData.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<org.duongthuy.tichhop.api.dao.model.MessageFunctionData> toCacheModel() {
		return _messageFunctionData.toCacheModel();
	}

	@Override
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData toEscapedModel() {
		return new MessageFunctionDataWrapper(_messageFunctionData.toEscapedModel());
	}

	@Override
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData toUnescapedModel() {
		return new MessageFunctionDataWrapper(_messageFunctionData.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _messageFunctionData.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _messageFunctionData.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_messageFunctionData.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MessageFunctionDataWrapper)) {
			return false;
		}

		MessageFunctionDataWrapper messageFunctionDataWrapper = (MessageFunctionDataWrapper)obj;

		if (Validator.equals(_messageFunctionData,
					messageFunctionDataWrapper._messageFunctionData)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MessageFunctionData getWrappedMessageFunctionData() {
		return _messageFunctionData;
	}

	@Override
	public MessageFunctionData getWrappedModel() {
		return _messageFunctionData;
	}

	@Override
	public void resetOriginalValues() {
		_messageFunctionData.resetOriginalValues();
	}

	private MessageFunctionData _messageFunctionData;
}
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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import org.duongthuy.tichhop.api.dao.service.ClpSerializer;
import org.duongthuy.tichhop.api.dao.service.MessageFunctionDataLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binhth
 */
public class MessageFunctionDataClp extends BaseModelImpl<MessageFunctionData>
	implements MessageFunctionData {
	public MessageFunctionDataClp() {
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
	public long getPrimaryKey() {
		return _messagePackagesId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setMessagePackagesId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _messagePackagesId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
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

	@Override
	public long getMessagePackagesId() {
		return _messagePackagesId;
	}

	@Override
	public void setMessagePackagesId(long messagePackagesId) {
		_messagePackagesId = messagePackagesId;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setMessagePackagesId",
						long.class);

				method.invoke(_messageFunctionDataRemoteModel, messagePackagesId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_messageFunctionDataRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setUserName", String.class);

				method.invoke(_messageFunctionDataRemoteModel, userName);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setCreateDate", Date.class);

				method.invoke(_messageFunctionDataRemoteModel, createDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setModifiedDate", Date.class);

				method.invoke(_messageFunctionDataRemoteModel, modifiedDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getMessageFunction() {
		return _messageFunction;
	}

	@Override
	public void setMessageFunction(String messageFunction) {
		_messageFunction = messageFunction;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setMessageFunction",
						String.class);

				method.invoke(_messageFunctionDataRemoteModel, messageFunction);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getMessageId() {
		return _messageId;
	}

	@Override
	public void setMessageId(String messageId) {
		_messageId = messageId;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setMessageId", String.class);

				method.invoke(_messageFunctionDataRemoteModel, messageId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getMessageFileIdData() {
		return _messageFileIdData;
	}

	@Override
	public void setMessageFileIdData(String messageFileIdData) {
		_messageFileIdData = messageFileIdData;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setMessageFileIdData",
						String.class);

				method.invoke(_messageFunctionDataRemoteModel, messageFileIdData);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getSendDate() {
		return _sendDate;
	}

	@Override
	public void setSendDate(Date sendDate) {
		_sendDate = sendDate;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setSendDate", Date.class);

				method.invoke(_messageFunctionDataRemoteModel, sendDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getVersion() {
		return _version;
	}

	@Override
	public void setVersion(String version) {
		_version = version;

		if (_messageFunctionDataRemoteModel != null) {
			try {
				Class<?> clazz = _messageFunctionDataRemoteModel.getClass();

				Method method = clazz.getMethod("setVersion", String.class);

				method.invoke(_messageFunctionDataRemoteModel, version);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getMessageFunctionDataRemoteModel() {
		return _messageFunctionDataRemoteModel;
	}

	public void setMessageFunctionDataRemoteModel(
		BaseModel<?> messageFunctionDataRemoteModel) {
		_messageFunctionDataRemoteModel = messageFunctionDataRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _messageFunctionDataRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_messageFunctionDataRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			MessageFunctionDataLocalServiceUtil.addMessageFunctionData(this);
		}
		else {
			MessageFunctionDataLocalServiceUtil.updateMessageFunctionData(this);
		}
	}

	@Override
	public MessageFunctionData toEscapedModel() {
		return (MessageFunctionData)ProxyUtil.newProxyInstance(MessageFunctionData.class.getClassLoader(),
			new Class[] { MessageFunctionData.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		MessageFunctionDataClp clone = new MessageFunctionDataClp();

		clone.setMessagePackagesId(getMessagePackagesId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setMessageFunction(getMessageFunction());
		clone.setMessageId(getMessageId());
		clone.setMessageFileIdData(getMessageFileIdData());
		clone.setSendDate(getSendDate());
		clone.setVersion(getVersion());

		return clone;
	}

	@Override
	public int compareTo(MessageFunctionData messageFunctionData) {
		int value = 0;

		value = getMessageFunction()
					.compareTo(messageFunctionData.getMessageFunction());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MessageFunctionDataClp)) {
			return false;
		}

		MessageFunctionDataClp messageFunctionData = (MessageFunctionDataClp)obj;

		long primaryKey = messageFunctionData.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{messagePackagesId=");
		sb.append(getMessagePackagesId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", messageFunction=");
		sb.append(getMessageFunction());
		sb.append(", messageId=");
		sb.append(getMessageId());
		sb.append(", messageFileIdData=");
		sb.append(getMessageFileIdData());
		sb.append(", sendDate=");
		sb.append(getSendDate());
		sb.append(", version=");
		sb.append(getVersion());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("org.duongthuy.tichhop.api.dao.model.MessageFunctionData");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>messagePackagesId</column-name><column-value><![CDATA[");
		sb.append(getMessagePackagesId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageFunction</column-name><column-value><![CDATA[");
		sb.append(getMessageFunction());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageId</column-name><column-value><![CDATA[");
		sb.append(getMessageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageFileIdData</column-name><column-value><![CDATA[");
		sb.append(getMessageFileIdData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sendDate</column-name><column-value><![CDATA[");
		sb.append(getSendDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>version</column-name><column-value><![CDATA[");
		sb.append(getVersion());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _messagePackagesId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _messageFunction;
	private String _messageId;
	private String _messageFileIdData;
	private Date _sendDate;
	private String _version;
	private BaseModel<?> _messageFunctionDataRemoteModel;
	private Class<?> _clpSerializerClass = org.duongthuy.tichhop.api.dao.service.ClpSerializer.class;
}
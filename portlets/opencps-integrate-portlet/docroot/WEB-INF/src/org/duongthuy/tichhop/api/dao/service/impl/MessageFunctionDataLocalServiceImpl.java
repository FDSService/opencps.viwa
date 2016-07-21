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

package org.duongthuy.tichhop.api.dao.service.impl;

import java.util.Date;

import org.duongthuy.tichhop.api.dao.model.MessageFunctionData;
import org.duongthuy.tichhop.api.dao.service.base.MessageFunctionDataLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the message function data local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link org.duongthuy.tichhop.api.dao.service.MessageFunctionDataLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author binhth
 * @see org.duongthuy.tichhop.api.dao.service.base.MessageFunctionDataLocalServiceBaseImpl
 * @see org.duongthuy.tichhop.api.dao.service.MessageFunctionDataLocalServiceUtil
 */
public class MessageFunctionDataLocalServiceImpl
	extends MessageFunctionDataLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link org.duongthuy.tichhop.api.dao.service.MessageFunctionDataLocalServiceUtil} to access the message function data local service.
	 */
	
	public MessageFunctionData getByF_O(String messageFunction, String messageId) throws SystemException {
		return messageFunctionDataPersistence.fetchByF_O(messageFunction, messageId);
	}
	
	public MessageFunctionData addMessageFunctionData(String userId, String userName, 
			String messageFunction, String messageId,
			String messageFileIdData, String version, Date sendDate) throws SystemException {
		
		MessageFunctionData messageFunctionData = null;
		
		long id = counterLocalService.increment(MessageFunctionData.class.getName());
		messageFunctionData = messageFunctionDataPersistence.create(id);
		
		messageFunctionData.setUserId(Long.valueOf(userId));
		
		messageFunctionData.setUserName(userName);
		
		messageFunctionData.setCreateDate(new Date());
		
		messageFunctionData.setModifiedDate(new Date());
		
		messageFunctionData.setMessageFunction(messageFunction);
		
		messageFunctionData.setMessageId(messageId);
		
		messageFunctionData.setMessageFileIdData(messageFileIdData);
		
		messageFunctionData.setVersion(version);
		
		messageFunctionData.setSendDate(sendDate);
		
		messageFunctionDataPersistence.update(messageFunctionData);
		return messageFunctionData;
	}
	
	public MessageFunctionData getByMI(String messageId) throws SystemException {
		return messageFunctionDataPersistence.fetchByMI(messageId);
	}
	
}
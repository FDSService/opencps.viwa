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

package org.duongthuy.tichhop.api.dao.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import org.duongthuy.tichhop.api.dao.model.MessageFunctionData;

/**
 * The persistence interface for the message function data service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author binhth
 * @see MessageFunctionDataPersistenceImpl
 * @see MessageFunctionDataUtil
 * @generated
 */
public interface MessageFunctionDataPersistence extends BasePersistence<MessageFunctionData> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MessageFunctionDataUtil} to access the message function data persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the message function data where messageFunction = &#63; and messageId = &#63; or throws a {@link org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException} if it could not be found.
	*
	* @param messageFunction the message function
	* @param messageId the message ID
	* @return the matching message function data
	* @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a matching message function data could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData findByF_O(
		java.lang.String messageFunction, java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;

	/**
	* Returns the message function data where messageFunction = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param messageFunction the message function
	* @param messageId the message ID
	* @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData fetchByF_O(
		java.lang.String messageFunction, java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message function data where messageFunction = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param messageFunction the message function
	* @param messageId the message ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData fetchByF_O(
		java.lang.String messageFunction, java.lang.String messageId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message function data where messageFunction = &#63; and messageId = &#63; from the database.
	*
	* @param messageFunction the message function
	* @param messageId the message ID
	* @return the message function data that was removed
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData removeByF_O(
		java.lang.String messageFunction, java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;

	/**
	* Returns the number of message function datas where messageFunction = &#63; and messageId = &#63;.
	*
	* @param messageFunction the message function
	* @param messageId the message ID
	* @return the number of matching message function datas
	* @throws SystemException if a system exception occurred
	*/
	public int countByF_O(java.lang.String messageFunction,
		java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message function data where messageId = &#63; or throws a {@link org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException} if it could not be found.
	*
	* @param messageId the message ID
	* @return the matching message function data
	* @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a matching message function data could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData findByMI(
		java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;

	/**
	* Returns the message function data where messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param messageId the message ID
	* @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData fetchByMI(
		java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message function data where messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param messageId the message ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData fetchByMI(
		java.lang.String messageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message function data where messageId = &#63; from the database.
	*
	* @param messageId the message ID
	* @return the message function data that was removed
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData removeByMI(
		java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;

	/**
	* Returns the number of message function datas where messageId = &#63;.
	*
	* @param messageId the message ID
	* @return the number of matching message function datas
	* @throws SystemException if a system exception occurred
	*/
	public int countByMI(java.lang.String messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the message function data in the entity cache if it is enabled.
	*
	* @param messageFunctionData the message function data
	*/
	public void cacheResult(
		org.duongthuy.tichhop.api.dao.model.MessageFunctionData messageFunctionData);

	/**
	* Caches the message function datas in the entity cache if it is enabled.
	*
	* @param messageFunctionDatas the message function datas
	*/
	public void cacheResult(
		java.util.List<org.duongthuy.tichhop.api.dao.model.MessageFunctionData> messageFunctionDatas);

	/**
	* Creates a new message function data with the primary key. Does not add the message function data to the database.
	*
	* @param messagePackagesId the primary key for the new message function data
	* @return the new message function data
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData create(
		long messagePackagesId);

	/**
	* Removes the message function data with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messagePackagesId the primary key of the message function data
	* @return the message function data that was removed
	* @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a message function data with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData remove(
		long messagePackagesId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;

	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData updateImpl(
		org.duongthuy.tichhop.api.dao.model.MessageFunctionData messageFunctionData)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message function data with the primary key or throws a {@link org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException} if it could not be found.
	*
	* @param messagePackagesId the primary key of the message function data
	* @return the message function data
	* @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a message function data with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData findByPrimaryKey(
		long messagePackagesId)
		throws com.liferay.portal.kernel.exception.SystemException,
			org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;

	/**
	* Returns the message function data with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param messagePackagesId the primary key of the message function data
	* @return the message function data, or <code>null</code> if a message function data with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public org.duongthuy.tichhop.api.dao.model.MessageFunctionData fetchByPrimaryKey(
		long messagePackagesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message function datas.
	*
	* @return the message function datas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<org.duongthuy.tichhop.api.dao.model.MessageFunctionData> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message function datas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.duongthuy.tichhop.api.dao.model.impl.MessageFunctionDataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message function datas
	* @param end the upper bound of the range of message function datas (not inclusive)
	* @return the range of message function datas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<org.duongthuy.tichhop.api.dao.model.MessageFunctionData> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message function datas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.duongthuy.tichhop.api.dao.model.impl.MessageFunctionDataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message function datas
	* @param end the upper bound of the range of message function datas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message function datas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<org.duongthuy.tichhop.api.dao.model.MessageFunctionData> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message function datas from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message function datas.
	*
	* @return the number of message function datas
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}
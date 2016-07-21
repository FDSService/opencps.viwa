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

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException;
import org.duongthuy.tichhop.api.dao.model.MessageFunctionData;
import org.duongthuy.tichhop.api.dao.model.impl.MessageFunctionDataImpl;
import org.duongthuy.tichhop.api.dao.model.impl.MessageFunctionDataModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message function data service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author binhth
 * @see MessageFunctionDataPersistence
 * @see MessageFunctionDataUtil
 * @generated
 */
public class MessageFunctionDataPersistenceImpl extends BasePersistenceImpl<MessageFunctionData>
	implements MessageFunctionDataPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MessageFunctionDataUtil} to access the message function data persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MessageFunctionDataImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED,
			MessageFunctionDataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED,
			MessageFunctionDataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_F_O = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED,
			MessageFunctionDataImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_O",
			new String[] { String.class.getName(), String.class.getName() },
			MessageFunctionDataModelImpl.MESSAGEFUNCTION_COLUMN_BITMASK |
			MessageFunctionDataModelImpl.MESSAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_O = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_O",
			new String[] { String.class.getName(), String.class.getName() });

	/**
	 * Returns the message function data where messageFunction = &#63; and messageId = &#63; or throws a {@link org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException} if it could not be found.
	 *
	 * @param messageFunction the message function
	 * @param messageId the message ID
	 * @return the matching message function data
	 * @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a matching message function data could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData findByF_O(String messageFunction,
		String messageId)
		throws NoSuchMessageFunctionDataException, SystemException {
		MessageFunctionData messageFunctionData = fetchByF_O(messageFunction,
				messageId);

		if (messageFunctionData == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageFunction=");
			msg.append(messageFunction);

			msg.append(", messageId=");
			msg.append(messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageFunctionDataException(msg.toString());
		}

		return messageFunctionData;
	}

	/**
	 * Returns the message function data where messageFunction = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param messageFunction the message function
	 * @param messageId the message ID
	 * @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData fetchByF_O(String messageFunction,
		String messageId) throws SystemException {
		return fetchByF_O(messageFunction, messageId, true);
	}

	/**
	 * Returns the message function data where messageFunction = &#63; and messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param messageFunction the message function
	 * @param messageId the message ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData fetchByF_O(String messageFunction,
		String messageId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { messageFunction, messageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_F_O,
					finderArgs, this);
		}

		if (result instanceof MessageFunctionData) {
			MessageFunctionData messageFunctionData = (MessageFunctionData)result;

			if (!Validator.equals(messageFunction,
						messageFunctionData.getMessageFunction()) ||
					!Validator.equals(messageId,
						messageFunctionData.getMessageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MESSAGEFUNCTIONDATA_WHERE);

			boolean bindMessageFunction = false;

			if (messageFunction == null) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEFUNCTION_1);
			}
			else if (messageFunction.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEFUNCTION_3);
			}
			else {
				bindMessageFunction = true;

				query.append(_FINDER_COLUMN_F_O_MESSAGEFUNCTION_2);
			}

			boolean bindMessageId = false;

			if (messageId == null) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEID_1);
			}
			else if (messageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEID_3);
			}
			else {
				bindMessageId = true;

				query.append(_FINDER_COLUMN_F_O_MESSAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMessageFunction) {
					qPos.add(messageFunction);
				}

				if (bindMessageId) {
					qPos.add(messageId);
				}

				List<MessageFunctionData> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_O,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"MessageFunctionDataPersistenceImpl.fetchByF_O(String, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					MessageFunctionData messageFunctionData = list.get(0);

					result = messageFunctionData;

					cacheResult(messageFunctionData);

					if ((messageFunctionData.getMessageFunction() == null) ||
							!messageFunctionData.getMessageFunction()
													.equals(messageFunction) ||
							(messageFunctionData.getMessageId() == null) ||
							!messageFunctionData.getMessageId().equals(messageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_O,
							finderArgs, messageFunctionData);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_O,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (MessageFunctionData)result;
		}
	}

	/**
	 * Removes the message function data where messageFunction = &#63; and messageId = &#63; from the database.
	 *
	 * @param messageFunction the message function
	 * @param messageId the message ID
	 * @return the message function data that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData removeByF_O(String messageFunction,
		String messageId)
		throws NoSuchMessageFunctionDataException, SystemException {
		MessageFunctionData messageFunctionData = findByF_O(messageFunction,
				messageId);

		return remove(messageFunctionData);
	}

	/**
	 * Returns the number of message function datas where messageFunction = &#63; and messageId = &#63;.
	 *
	 * @param messageFunction the message function
	 * @param messageId the message ID
	 * @return the number of matching message function datas
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByF_O(String messageFunction, String messageId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_O;

		Object[] finderArgs = new Object[] { messageFunction, messageId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MESSAGEFUNCTIONDATA_WHERE);

			boolean bindMessageFunction = false;

			if (messageFunction == null) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEFUNCTION_1);
			}
			else if (messageFunction.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEFUNCTION_3);
			}
			else {
				bindMessageFunction = true;

				query.append(_FINDER_COLUMN_F_O_MESSAGEFUNCTION_2);
			}

			boolean bindMessageId = false;

			if (messageId == null) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEID_1);
			}
			else if (messageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_O_MESSAGEID_3);
			}
			else {
				bindMessageId = true;

				query.append(_FINDER_COLUMN_F_O_MESSAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMessageFunction) {
					qPos.add(messageFunction);
				}

				if (bindMessageId) {
					qPos.add(messageId);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_F_O_MESSAGEFUNCTION_1 = "messageFunctionData.messageFunction IS NULL AND ";
	private static final String _FINDER_COLUMN_F_O_MESSAGEFUNCTION_2 = "messageFunctionData.messageFunction = ? AND ";
	private static final String _FINDER_COLUMN_F_O_MESSAGEFUNCTION_3 = "(messageFunctionData.messageFunction IS NULL OR messageFunctionData.messageFunction = '') AND ";
	private static final String _FINDER_COLUMN_F_O_MESSAGEID_1 = "messageFunctionData.messageId IS NULL";
	private static final String _FINDER_COLUMN_F_O_MESSAGEID_2 = "messageFunctionData.messageId = ?";
	private static final String _FINDER_COLUMN_F_O_MESSAGEID_3 = "(messageFunctionData.messageId IS NULL OR messageFunctionData.messageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_MI = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED,
			MessageFunctionDataImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByMI", new String[] { String.class.getName() },
			MessageFunctionDataModelImpl.MESSAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MI = new FinderPath(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMI",
			new String[] { String.class.getName() });

	/**
	 * Returns the message function data where messageId = &#63; or throws a {@link org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException} if it could not be found.
	 *
	 * @param messageId the message ID
	 * @return the matching message function data
	 * @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a matching message function data could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData findByMI(String messageId)
		throws NoSuchMessageFunctionDataException, SystemException {
		MessageFunctionData messageFunctionData = fetchByMI(messageId);

		if (messageFunctionData == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("messageId=");
			msg.append(messageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageFunctionDataException(msg.toString());
		}

		return messageFunctionData;
	}

	/**
	 * Returns the message function data where messageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param messageId the message ID
	 * @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData fetchByMI(String messageId)
		throws SystemException {
		return fetchByMI(messageId, true);
	}

	/**
	 * Returns the message function data where messageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param messageId the message ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message function data, or <code>null</code> if a matching message function data could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData fetchByMI(String messageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { messageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_MI,
					finderArgs, this);
		}

		if (result instanceof MessageFunctionData) {
			MessageFunctionData messageFunctionData = (MessageFunctionData)result;

			if (!Validator.equals(messageId, messageFunctionData.getMessageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MESSAGEFUNCTIONDATA_WHERE);

			boolean bindMessageId = false;

			if (messageId == null) {
				query.append(_FINDER_COLUMN_MI_MESSAGEID_1);
			}
			else if (messageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MI_MESSAGEID_3);
			}
			else {
				bindMessageId = true;

				query.append(_FINDER_COLUMN_MI_MESSAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMessageId) {
					qPos.add(messageId);
				}

				List<MessageFunctionData> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MI,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"MessageFunctionDataPersistenceImpl.fetchByMI(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					MessageFunctionData messageFunctionData = list.get(0);

					result = messageFunctionData;

					cacheResult(messageFunctionData);

					if ((messageFunctionData.getMessageId() == null) ||
							!messageFunctionData.getMessageId().equals(messageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MI,
							finderArgs, messageFunctionData);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MI, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (MessageFunctionData)result;
		}
	}

	/**
	 * Removes the message function data where messageId = &#63; from the database.
	 *
	 * @param messageId the message ID
	 * @return the message function data that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData removeByMI(String messageId)
		throws NoSuchMessageFunctionDataException, SystemException {
		MessageFunctionData messageFunctionData = findByMI(messageId);

		return remove(messageFunctionData);
	}

	/**
	 * Returns the number of message function datas where messageId = &#63;.
	 *
	 * @param messageId the message ID
	 * @return the number of matching message function datas
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByMI(String messageId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MI;

		Object[] finderArgs = new Object[] { messageId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MESSAGEFUNCTIONDATA_WHERE);

			boolean bindMessageId = false;

			if (messageId == null) {
				query.append(_FINDER_COLUMN_MI_MESSAGEID_1);
			}
			else if (messageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MI_MESSAGEID_3);
			}
			else {
				bindMessageId = true;

				query.append(_FINDER_COLUMN_MI_MESSAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMessageId) {
					qPos.add(messageId);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_MI_MESSAGEID_1 = "messageFunctionData.messageId IS NULL";
	private static final String _FINDER_COLUMN_MI_MESSAGEID_2 = "messageFunctionData.messageId = ?";
	private static final String _FINDER_COLUMN_MI_MESSAGEID_3 = "(messageFunctionData.messageId IS NULL OR messageFunctionData.messageId = '')";

	public MessageFunctionDataPersistenceImpl() {
		setModelClass(MessageFunctionData.class);
	}

	/**
	 * Caches the message function data in the entity cache if it is enabled.
	 *
	 * @param messageFunctionData the message function data
	 */
	@Override
	public void cacheResult(MessageFunctionData messageFunctionData) {
		EntityCacheUtil.putResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataImpl.class, messageFunctionData.getPrimaryKey(),
			messageFunctionData);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_O,
			new Object[] {
				messageFunctionData.getMessageFunction(),
				messageFunctionData.getMessageId()
			}, messageFunctionData);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MI,
			new Object[] { messageFunctionData.getMessageId() },
			messageFunctionData);

		messageFunctionData.resetOriginalValues();
	}

	/**
	 * Caches the message function datas in the entity cache if it is enabled.
	 *
	 * @param messageFunctionDatas the message function datas
	 */
	@Override
	public void cacheResult(List<MessageFunctionData> messageFunctionDatas) {
		for (MessageFunctionData messageFunctionData : messageFunctionDatas) {
			if (EntityCacheUtil.getResult(
						MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
						MessageFunctionDataImpl.class,
						messageFunctionData.getPrimaryKey()) == null) {
				cacheResult(messageFunctionData);
			}
			else {
				messageFunctionData.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message function datas.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MessageFunctionDataImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MessageFunctionDataImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message function data.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MessageFunctionData messageFunctionData) {
		EntityCacheUtil.removeResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataImpl.class, messageFunctionData.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(messageFunctionData);
	}

	@Override
	public void clearCache(List<MessageFunctionData> messageFunctionDatas) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MessageFunctionData messageFunctionData : messageFunctionDatas) {
			EntityCacheUtil.removeResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
				MessageFunctionDataImpl.class,
				messageFunctionData.getPrimaryKey());

			clearUniqueFindersCache(messageFunctionData);
		}
	}

	protected void cacheUniqueFindersCache(
		MessageFunctionData messageFunctionData) {
		if (messageFunctionData.isNew()) {
			Object[] args = new Object[] {
					messageFunctionData.getMessageFunction(),
					messageFunctionData.getMessageId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_O, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_O, args,
				messageFunctionData);

			args = new Object[] { messageFunctionData.getMessageId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MI, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MI, args,
				messageFunctionData);
		}
		else {
			MessageFunctionDataModelImpl messageFunctionDataModelImpl = (MessageFunctionDataModelImpl)messageFunctionData;

			if ((messageFunctionDataModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_F_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						messageFunctionData.getMessageFunction(),
						messageFunctionData.getMessageId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_O, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_O, args,
					messageFunctionData);
			}

			if ((messageFunctionDataModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_MI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { messageFunctionData.getMessageId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MI, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_MI, args,
					messageFunctionData);
			}
		}
	}

	protected void clearUniqueFindersCache(
		MessageFunctionData messageFunctionData) {
		MessageFunctionDataModelImpl messageFunctionDataModelImpl = (MessageFunctionDataModelImpl)messageFunctionData;

		Object[] args = new Object[] {
				messageFunctionData.getMessageFunction(),
				messageFunctionData.getMessageId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_O, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_O, args);

		if ((messageFunctionDataModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_F_O.getColumnBitmask()) != 0) {
			args = new Object[] {
					messageFunctionDataModelImpl.getOriginalMessageFunction(),
					messageFunctionDataModelImpl.getOriginalMessageId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_O, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_O, args);
		}

		args = new Object[] { messageFunctionData.getMessageId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MI, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MI, args);

		if ((messageFunctionDataModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_MI.getColumnBitmask()) != 0) {
			args = new Object[] {
					messageFunctionDataModelImpl.getOriginalMessageId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_MI, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_MI, args);
		}
	}

	/**
	 * Creates a new message function data with the primary key. Does not add the message function data to the database.
	 *
	 * @param messagePackagesId the primary key for the new message function data
	 * @return the new message function data
	 */
	@Override
	public MessageFunctionData create(long messagePackagesId) {
		MessageFunctionData messageFunctionData = new MessageFunctionDataImpl();

		messageFunctionData.setNew(true);
		messageFunctionData.setPrimaryKey(messagePackagesId);

		return messageFunctionData;
	}

	/**
	 * Removes the message function data with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messagePackagesId the primary key of the message function data
	 * @return the message function data that was removed
	 * @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a message function data with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData remove(long messagePackagesId)
		throws NoSuchMessageFunctionDataException, SystemException {
		return remove((Serializable)messagePackagesId);
	}

	/**
	 * Removes the message function data with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message function data
	 * @return the message function data that was removed
	 * @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a message function data with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData remove(Serializable primaryKey)
		throws NoSuchMessageFunctionDataException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MessageFunctionData messageFunctionData = (MessageFunctionData)session.get(MessageFunctionDataImpl.class,
					primaryKey);

			if (messageFunctionData == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageFunctionDataException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(messageFunctionData);
		}
		catch (NoSuchMessageFunctionDataException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected MessageFunctionData removeImpl(
		MessageFunctionData messageFunctionData) throws SystemException {
		messageFunctionData = toUnwrappedModel(messageFunctionData);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(messageFunctionData)) {
				messageFunctionData = (MessageFunctionData)session.get(MessageFunctionDataImpl.class,
						messageFunctionData.getPrimaryKeyObj());
			}

			if (messageFunctionData != null) {
				session.delete(messageFunctionData);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (messageFunctionData != null) {
			clearCache(messageFunctionData);
		}

		return messageFunctionData;
	}

	@Override
	public MessageFunctionData updateImpl(
		org.duongthuy.tichhop.api.dao.model.MessageFunctionData messageFunctionData)
		throws SystemException {
		messageFunctionData = toUnwrappedModel(messageFunctionData);

		boolean isNew = messageFunctionData.isNew();

		Session session = null;

		try {
			session = openSession();

			if (messageFunctionData.isNew()) {
				session.save(messageFunctionData);

				messageFunctionData.setNew(false);
			}
			else {
				session.merge(messageFunctionData);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MessageFunctionDataModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
			MessageFunctionDataImpl.class, messageFunctionData.getPrimaryKey(),
			messageFunctionData);

		clearUniqueFindersCache(messageFunctionData);
		cacheUniqueFindersCache(messageFunctionData);

		return messageFunctionData;
	}

	protected MessageFunctionData toUnwrappedModel(
		MessageFunctionData messageFunctionData) {
		if (messageFunctionData instanceof MessageFunctionDataImpl) {
			return messageFunctionData;
		}

		MessageFunctionDataImpl messageFunctionDataImpl = new MessageFunctionDataImpl();

		messageFunctionDataImpl.setNew(messageFunctionData.isNew());
		messageFunctionDataImpl.setPrimaryKey(messageFunctionData.getPrimaryKey());

		messageFunctionDataImpl.setMessagePackagesId(messageFunctionData.getMessagePackagesId());
		messageFunctionDataImpl.setUserId(messageFunctionData.getUserId());
		messageFunctionDataImpl.setUserName(messageFunctionData.getUserName());
		messageFunctionDataImpl.setCreateDate(messageFunctionData.getCreateDate());
		messageFunctionDataImpl.setModifiedDate(messageFunctionData.getModifiedDate());
		messageFunctionDataImpl.setMessageFunction(messageFunctionData.getMessageFunction());
		messageFunctionDataImpl.setMessageId(messageFunctionData.getMessageId());
		messageFunctionDataImpl.setMessageFileIdData(messageFunctionData.getMessageFileIdData());
		messageFunctionDataImpl.setSendDate(messageFunctionData.getSendDate());
		messageFunctionDataImpl.setVersion(messageFunctionData.getVersion());

		return messageFunctionDataImpl;
	}

	/**
	 * Returns the message function data with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message function data
	 * @return the message function data
	 * @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a message function data with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMessageFunctionDataException, SystemException {
		MessageFunctionData messageFunctionData = fetchByPrimaryKey(primaryKey);

		if (messageFunctionData == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMessageFunctionDataException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return messageFunctionData;
	}

	/**
	 * Returns the message function data with the primary key or throws a {@link org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException} if it could not be found.
	 *
	 * @param messagePackagesId the primary key of the message function data
	 * @return the message function data
	 * @throws org.duongthuy.tichhop.api.dao.NoSuchMessageFunctionDataException if a message function data with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData findByPrimaryKey(long messagePackagesId)
		throws NoSuchMessageFunctionDataException, SystemException {
		return findByPrimaryKey((Serializable)messagePackagesId);
	}

	/**
	 * Returns the message function data with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message function data
	 * @return the message function data, or <code>null</code> if a message function data with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		MessageFunctionData messageFunctionData = (MessageFunctionData)EntityCacheUtil.getResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
				MessageFunctionDataImpl.class, primaryKey);

		if (messageFunctionData == _nullMessageFunctionData) {
			return null;
		}

		if (messageFunctionData == null) {
			Session session = null;

			try {
				session = openSession();

				messageFunctionData = (MessageFunctionData)session.get(MessageFunctionDataImpl.class,
						primaryKey);

				if (messageFunctionData != null) {
					cacheResult(messageFunctionData);
				}
				else {
					EntityCacheUtil.putResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
						MessageFunctionDataImpl.class, primaryKey,
						_nullMessageFunctionData);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(MessageFunctionDataModelImpl.ENTITY_CACHE_ENABLED,
					MessageFunctionDataImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return messageFunctionData;
	}

	/**
	 * Returns the message function data with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param messagePackagesId the primary key of the message function data
	 * @return the message function data, or <code>null</code> if a message function data with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MessageFunctionData fetchByPrimaryKey(long messagePackagesId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)messagePackagesId);
	}

	/**
	 * Returns all the message function datas.
	 *
	 * @return the message function datas
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<MessageFunctionData> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<MessageFunctionData> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

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
	@Override
	public List<MessageFunctionData> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<MessageFunctionData> list = (List<MessageFunctionData>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MESSAGEFUNCTIONDATA);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MESSAGEFUNCTIONDATA;

				if (pagination) {
					sql = sql.concat(MessageFunctionDataModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MessageFunctionData>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<MessageFunctionData>(list);
				}
				else {
					list = (List<MessageFunctionData>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the message function datas from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (MessageFunctionData messageFunctionData : findAll()) {
			remove(messageFunctionData);
		}
	}

	/**
	 * Returns the number of message function datas.
	 *
	 * @return the number of message function datas
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MESSAGEFUNCTIONDATA);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the message function data persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.org.duongthuy.tichhop.api.dao.model.MessageFunctionData")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MessageFunctionData>> listenersList = new ArrayList<ModelListener<MessageFunctionData>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MessageFunctionData>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(MessageFunctionDataImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_MESSAGEFUNCTIONDATA = "SELECT messageFunctionData FROM MessageFunctionData messageFunctionData";
	private static final String _SQL_SELECT_MESSAGEFUNCTIONDATA_WHERE = "SELECT messageFunctionData FROM MessageFunctionData messageFunctionData WHERE ";
	private static final String _SQL_COUNT_MESSAGEFUNCTIONDATA = "SELECT COUNT(messageFunctionData) FROM MessageFunctionData messageFunctionData";
	private static final String _SQL_COUNT_MESSAGEFUNCTIONDATA_WHERE = "SELECT COUNT(messageFunctionData) FROM MessageFunctionData messageFunctionData WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "messageFunctionData.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MessageFunctionData exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MessageFunctionData exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(MessageFunctionDataPersistenceImpl.class);
	private static MessageFunctionData _nullMessageFunctionData = new MessageFunctionDataImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MessageFunctionData> toCacheModel() {
				return _nullMessageFunctionDataCacheModel;
			}
		};

	private static CacheModel<MessageFunctionData> _nullMessageFunctionDataCacheModel =
		new CacheModel<MessageFunctionData>() {
			@Override
			public MessageFunctionData toEntityModel() {
				return _nullMessageFunctionData;
			}
		};
}
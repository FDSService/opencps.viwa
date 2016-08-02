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

package org.duongthuy.tichhop.business.service.impl;

import org.duongthuy.tichhop.business.service.base.IntegrateAppServiceBaseImpl;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

/**
 * The implementation of the integrate app remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link org.duongthuy.tichhop.business.service.IntegrateAppService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author trungdk
 * @see org.duongthuy.tichhop.business.service.base.IntegrateAppServiceBaseImpl
 * @see org.duongthuy.tichhop.business.service.IntegrateAppServiceUtil
 */
public class IntegrateAppServiceImpl extends IntegrateAppServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link org.duongthuy.tichhop.business.service.IntegrateAppServiceUtil} to access the integrate app remote service.
	 */
	@JSONWebService(value="/integrate-app/dossiers")
	public String searchDossier(String dossiertype, String organizationcode, String status, String documentyear, String fromdate, String todate, String customername) {
		return "dossiertype: " + dossiertype;
	}

	@JSONWebService(value="/integrate-app/dossiers/instance")
	public String dossiersInstance(String messagefunction, String messageid, String messagecontent) {
		int function = Integer.parseInt(messagefunction);
		switch (function) {
		case 20:
			break;
		case 21:
			break;
		case 22:
			break;
		case 23:
			break;
		case 24:
			break;
		case 11:
			break;
		default:
			break;
		};
		return "";
	}
	
}
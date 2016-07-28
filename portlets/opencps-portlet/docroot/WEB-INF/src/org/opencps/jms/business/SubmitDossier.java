/**
 * OpenCPS is the open source Core Public Services software
 * Copyright (C) 2016-present OpenCPS community

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.opencps.jms.business;

import java.util.LinkedHashMap;
import java.util.List;

import org.opencps.backend.message.SendToEngineMsg;
import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.model.DossierFile;
import org.opencps.dossiermgt.model.DossierPart;
import org.opencps.dossiermgt.model.DossierTemplate;
import org.opencps.dossiermgt.model.FileGroup;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.jms.message.body.DossierFileMsgBody;
import org.opencps.jms.message.body.DossierMsgBody;
import org.opencps.jms.util.JMSMessageBodyUtil.AnalyzeDossierFile;
import org.opencps.util.PortletConstants;
import org.opencps.util.WebKeys;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

/**
 * @author trungnt
 */
public class SubmitDossier {

	/**
	 * @param submitDossierMessage
	 * @return
	 * @throws SystemException
	 * @throws PortalException
	 */
	public Dossier syncDossier(DossierMsgBody dossierMsgBody)
		throws PortalException, SystemException {

		Dossier dossier = null;

		Dossier syncDossier = dossierMsgBody.getDossier();

		ServiceContext serviceContext = dossierMsgBody.getServiceContext();

		LinkedHashMap<DossierFile, DossierPart> syncDossierFiles = null;

		LinkedHashMap<String, FileGroup> syncFileGroups = null;

		LinkedHashMap<Long, DossierPart> syncFileGroupDossierParts = null;

		LinkedHashMap<String, DLFileEntry> syncDLFileEntries = null;

		LinkedHashMap<String, byte[]> data = null;

		DossierTemplate syncDossierTemplate =
			dossierMsgBody.getDossierTemplate();

		List<DossierFileMsgBody> dossierFileMsgBodies =
			dossierMsgBody.getLstDossierFileMsgBody();

		if (dossierFileMsgBodies != null) {
			AnalyzeDossierFile analyzeDossierFile =
				new AnalyzeDossierFile(dossierFileMsgBodies);

			syncDossierFiles = analyzeDossierFile.getSyncDossierFiles();
			syncFileGroups = analyzeDossierFile.getSyncFileGroups();
			syncFileGroupDossierParts =
				analyzeDossierFile.getSyncFileGroupDossierParts();
			syncDLFileEntries = analyzeDossierFile.getSyncDLFileEntries();
			data = analyzeDossierFile.getData();
		}

		if (syncDossier != null && syncDossierFiles != null &&
			syncDLFileEntries != null && data != null &&
			syncDossierTemplate != null && serviceContext != null) {
			dossier =
				DossierLocalServiceUtil.syncDossier(
					syncDossier, syncDossierFiles, syncFileGroups,
					syncFileGroupDossierParts, syncDLFileEntries, data,
					syncDossierTemplate, serviceContext);

			sendToBackend(
				dossier.getDossierId(), dossier.getDossierStatus(),
				serviceContext);
		}

		return dossier;
	}

	protected void sendToBackend(
		long dossierId, String dossierStatus, ServiceContext serviceContext) {

		Message message = new Message();

		SendToEngineMsg engineMsg = new SendToEngineMsg();

		switch (dossierStatus) {

		case PortletConstants.DOSSIER_STATUS_NEW:

			engineMsg.setAction(WebKeys.ACTION_SUBMIT_VALUE);

			engineMsg.setDossierId(dossierId);

			engineMsg.setFileGroupId(0);

			engineMsg.setCompanyId(serviceContext.getCompanyId());

			engineMsg.setEvent(WebKeys.ACTION_SUBMIT_VALUE);

			engineMsg.setUserId(serviceContext.getUserId());

			engineMsg.setGroupId(serviceContext.getScopeGroupId());

			engineMsg.setUserId(serviceContext.getUserId());

			break;

		default:
			break;
		}

		message.put("msgToEngine", engineMsg);

		MessageBusUtil.sendMessage(
			"opencps/backoffice/engine/destination", message);

	}
}

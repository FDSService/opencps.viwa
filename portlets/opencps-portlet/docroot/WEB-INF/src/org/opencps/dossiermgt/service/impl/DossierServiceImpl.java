
/*******************************************************************************
 * OpenCPS is the open source Core Public Services software
 * Copyright (C) 2016-present OpenCPS community
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/


package org.opencps.dossiermgt.service.impl;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.POST;

import org.opencps.accountmgt.NoSuchAccountException;
import org.opencps.accountmgt.NoSuchAccountFolderException;
import org.opencps.accountmgt.NoSuchAccountOwnOrgIdException;
import org.opencps.accountmgt.NoSuchAccountOwnUserIdException;
import org.opencps.accountmgt.NoSuchAccountTypeException;
import org.opencps.accountmgt.model.Business;
import org.opencps.accountmgt.model.Citizen;
import org.opencps.accountmgt.service.BusinessLocalServiceUtil;
import org.opencps.accountmgt.service.CitizenLocalServiceUtil;
import org.opencps.backend.message.SendToEngineMsg;
import org.opencps.backend.message.UserActionMsg;
import org.opencps.backend.util.AutoFillFormData;
import org.opencps.datamgt.model.DictItem;
import org.opencps.dossiermgt.NoSuchDossierException;
import org.opencps.dossiermgt.NoSuchDossierFileException;
import org.opencps.dossiermgt.NoSuchDossierPartException;
import org.opencps.dossiermgt.PermissionDossierException;
import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.model.DossierFile;
import org.opencps.dossiermgt.model.DossierPart;
import org.opencps.dossiermgt.model.DossierTemplate;
import org.opencps.dossiermgt.model.ServiceConfig;
import org.opencps.dossiermgt.search.DossierDisplayTerms;
import org.opencps.dossiermgt.search.DossierFileDisplayTerms;
import org.opencps.dossiermgt.service.DossierFileLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierPartLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierTemplateLocalServiceUtil;
import org.opencps.dossiermgt.service.ServiceConfigLocalServiceUtil;
import org.opencps.dossiermgt.service.base.DossierServiceBaseImpl;
import org.opencps.processmgt.NoSuchProcessOrderException;
import org.opencps.processmgt.model.ProcessOrder;
import org.opencps.processmgt.model.ProcessWorkflow;
import org.opencps.processmgt.search.ProcessOrderDisplayTerms;
import org.opencps.processmgt.service.ProcessOrderLocalServiceUtil;
import org.opencps.processmgt.service.ProcessWorkflowLocalServiceUtil;
import org.opencps.servicemgt.NoSuchServiceInfoException;
import org.opencps.servicemgt.model.ServiceInfo;
import org.opencps.servicemgt.service.ServiceInfoLocalServiceUtil;
import org.opencps.util.DLFolderUtil;
import org.opencps.util.DateTimeUtil;
import org.opencps.util.PortletConstants;
import org.opencps.util.WebKeys;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * The implementation of the dossier remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link org.opencps.dossiermgt.service.DossierService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author trungnt
 * @see org.opencps.dossiermgt.service.base.DossierServiceBaseImpl
 * @see org.opencps.dossiermgt.service.DossierServiceUtil
 */

@JSONWebService
public class DossierServiceImpl extends DossierServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link org.opencps.dossiermgt.service.DossierServiceUtil} to access the dossier remote service.
	 */
	
	public JSONObject getByoid(
	    String oid)
	    throws SystemException {
		System.out.println("DossierServiceImpl.oid()"+oid);
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		Dossier dossier =
				dossierLocalService.getByoid(oid);
		jsonObject.put("oid", dossier.getOid());
		jsonObject.put("externalRefNo", dossier.getExternalRefNo());
		jsonObject.put("dossierStatus", dossier.getDossierStatus());
		
		try {
			DossierTemplate dossierTemplate = DossierTemplateLocalServiceUtil.getDossierTemplate(dossier.getDossierTemplateId());
			JSONObject jsonDossierTemplate = JSONFactoryUtil.createJSONObject();
			jsonDossierTemplate.put("dossierTemplateId", dossierTemplate.getDossierTemplateId());
			jsonDossierTemplate.put("templateNo", dossierTemplate.getTemplateNo());
			jsonDossierTemplate.put("templateName", dossierTemplate.getTemplateName());
			jsonDossierTemplate.put("description", dossierTemplate.getDescription());
			jsonObject.put("DossierTemplate", jsonDossierTemplate);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ServiceConfig serviceConfig = ServiceConfigLocalServiceUtil.getServiceConfig(dossier.getServiceConfigId());
			JSONObject jsonServiceConfig = JSONFactoryUtil.createJSONObject();
			jsonServiceConfig.put("serviceConfigId", serviceConfig.getServiceConfigId());
			jsonServiceConfig.put("serviceInfoId", serviceConfig.getServiceInfoId());
			jsonServiceConfig.put("serviceDomainIndex", serviceConfig.getServiceDomainIndex());
			jsonServiceConfig.put("serviceAdministrationIndex", serviceConfig.getServiceAdministrationIndex());
			jsonServiceConfig.put("dossierTemplateId", serviceConfig.getDossierTemplateId());
			jsonServiceConfig.put("govAgencyCode", serviceConfig.getGovAgencyCode());
			jsonServiceConfig.put("govAgencyName", serviceConfig.getGovAgencyName());
			jsonServiceConfig.put("serviceInstruction", serviceConfig.getServiceInstruction());
			jsonServiceConfig.put("serviceLevel", serviceConfig.getServiceLevel());
			jsonServiceConfig.put("servicePortal", serviceConfig.getServicePortal());
			jsonServiceConfig.put("serviceOnegate", serviceConfig.getServiceOnegate());

			jsonObject.put("ServiceConfig", jsonServiceConfig);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<DossierFile> dossierFiles = DossierFileLocalServiceUtil.getDossierFileByDossierId(dossier.getDossierId());
		JSONArray dfArr= JSONFactoryUtil.createJSONArray();
		for (DossierFile df : dossierFiles) {
			JSONObject jsonDossierFile = JSONFactoryUtil.createJSONObject();
			jsonDossierFile.put("dossierFileId", df.getDossierFileId());
			jsonDossierFile.put("templateFileNo", df.getTemplateFileNo());
			jsonDossierFile.put("displayName", df.getDisplayName());
			jsonDossierFile.put("formData", df.getFormData());
			dfArr.put(jsonDossierFile);
		}
		
		jsonObject.put("DossierFiles", dfArr);
		return jsonObject;
	}
	
	@JSONWebService(value = "search-dossier", method = "POST")
	public JSONObject searchDossier(
			String dossiertype, 
			String organizationcode,
			String processStepId,
			String status, 
			String fromdate, 
			String todate, 
			String documentyear, 
			String customername) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		JSONArray dossierArr = JSONFactoryUtil.createJSONArray();
		
		int documentyearInt = -1;
		if (!Validator.isNull(documentyear) && !"".equals(documentyear.trim())) {
			documentyearInt = Integer.parseInt(documentyear);
		}
		DateFormat sdf = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		int end = DossierLocalServiceUtil.countDossierForRemoteService(dossiertype, organizationcode, processStepId, status, fromdate, todate, documentyearInt, customername);
		System.out.println("---------NUMBER OF RESULT---------" + end);
		List<Dossier> lsDossiers = DossierLocalServiceUtil.searchDossierForRemoteService(dossiertype, organizationcode, processStepId, status, fromdate, todate, documentyearInt, customername, 0, end);
		
		for (Dossier d : lsDossiers) {
			JSONObject obj = JSONFactoryUtil.createJSONObject();
			obj.put("oid", d.getOid());
			obj.put("govAgencyName", d.getGovAgencyName());
			obj.put("subjectName", d.getSubjectName());
			if (Validator.isNotNull(d.getSubmitDatetime())) {
				//obj.put("submitDatetime", sdf.format(d.getSubmitDatetime()));				
			}
			else {
				//obj.put("submitDatetime", "");
			}
			if (Validator.isNotNull(d.getReceiveDatetime())) {
				//obj.put("receiveDatetime", sdf.format(d.getReceiveDatetime()));				
			}
			else {
				//obj.put("receiveDatetime", "");				
			}
			//obj.put("receptionNo", d.getReceptionNo());
			if (Validator.isNotNull(d.getEstimateDatetime())) {
				//obj.put("estimateDatetime", sdf.format(d.getEstimateDatetime()));				
			}
			else {
				//obj.put("estimateDatetime", "");				
			}
			obj.put("dossierStatus", d.getDossierStatus());
			//obj.put("delayStatus", d.getDelayStatus());
			
			dossierArr.put(obj);
		}
		resultObj.put("DossierList", dossierArr);
		return resultObj;
	}
	
	@JSONWebService(value = "request-changes", method = "POST")
	public JSONObject requestChanges(String messagecontent) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		
		return resultObj;
	}

	@JSONWebService(value = "cancel-dossier", method = "POST")
	public JSONObject cancelDossier(String oid) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		try {
			Dossier dossier =
					dossierLocalService.getByoid(oid);
			Message message = new Message();
			UserActionMsg actionMsg = new UserActionMsg();
			
			actionMsg
				.setAction(WebKeys.ACTION_CANCEL_VALUE);

			actionMsg
				.setDossierId(dossier.getDossierId());
		
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setScopeGroupId(dossier.getGroupId());

			actionMsg
				.setLocale(serviceContext
					.getLocale());

				actionMsg
					.setUserId(serviceContext
						.getUserId());

				ProcessOrder processOrder = ProcessOrderLocalServiceUtil
					.getProcessOrder(dossier.getDossierId(), -1);

				actionMsg
					.setProcessOrderId(processOrder
						.getProcessOrderId());

				message
					.put("action", "resubmit");
				message
					.put("dossierId", dossier.getDossierId());
				message
					.put("fileGroupId", -1);
				message
					.put("level", PortletConstants.DOSSIER_LOG_NORMAL);
				message
					.put("locale", serviceContext
						.getLocale());

				message
					.put("groupId", serviceContext
						.getScopeGroupId());

				message
					.put("govAgencyOrganizationId", dossier.getGovAgencyOrganizationId());

				message
					.put("userId", serviceContext
						.getUserId());

				message
					.put("msgToEngine", actionMsg);

			MessageBusUtil
				.sendMessage("opencps/frontoffice/out/destination", message);
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProcessOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultObj;
	}

	@JSONWebService(value = "update-dossier", method = "POST")
	public JSONObject updateDossier(String oid, String email, int processId, int processWorkflowId) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		try {
			Dossier dossier =
					dossierLocalService.getByoid(oid);
			Message message = new Message();
			UserActionMsg actionMsg = new UserActionMsg();
			
			actionMsg
				.setDossierId(dossier.getDossierId());
		
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setScopeGroupId(dossier.getGroupId());

			actionMsg
				.setLocale(serviceContext
					.getLocale());

				actionMsg
					.setUserId(serviceContext
						.getUserId());

				ProcessOrder processOrder = ProcessOrderLocalServiceUtil
					.getProcessOrder(dossier.getDossierId(), -1);

				actionMsg
					.setProcessOrderId(processOrder
						.getProcessOrderId());

				message
					.put("action", "resubmit");
				message
					.put("dossierId", dossier.getDossierId());
				message
					.put("fileGroupId", -1);
				message
					.put("level", PortletConstants.DOSSIER_LOG_NORMAL);
				message
					.put("locale", serviceContext
						.getLocale());

				message
					.put("groupId", serviceContext
						.getScopeGroupId());

				message
					.put("govAgencyOrganizationId", dossier.getGovAgencyOrganizationId());

				message
					.put("userId", serviceContext
						.getUserId());

				message
					.put("msgToEngine", actionMsg);

			MessageBusUtil
				.sendMessage("opencps/frontoffice/out/destination", message);
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProcessOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultObj;
	}

	@JSONWebService(value = "nextstep", method = "POST")
	public JSONObject nextStep(String oid, String userName, int processWorkflowId, String actionNote) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		resultObj.put("oid", oid);
		try {
			Dossier dossier = DossierLocalServiceUtil.getByoid(oid);
			System.out.println("DOSSIER============" + dossier.getDossierId());
			ProcessOrder processOrder = ProcessOrderLocalServiceUtil.getProcessOrder(dossier.getDossierId(), 0);
			User user = UserLocalServiceUtil.getUserByScreenName(dossier.getCompanyId(), userName);
			ProcessWorkflow processWorkflow = ProcessWorkflowLocalServiceUtil.getProcessWorkflow(processWorkflowId);
			Message message = new Message();
			message
				.put(ProcessOrderDisplayTerms.EVENT, null);
			message
				.put(ProcessOrderDisplayTerms.ACTION_NOTE, actionNote);
			message
				.put(ProcessOrderDisplayTerms.PROCESS_STEP_ID, processOrder.getProcessStepId());
			message
				.put(ProcessOrderDisplayTerms.ASSIGN_TO_USER_ID, 0);
			message
				.put(ProcessOrderDisplayTerms.SERVICE_PROCESS_ID, processOrder.getServiceProcessId());
			message
				.put(ProcessOrderDisplayTerms.PAYMENTVALUE, 0);
	
			message
				.put(ProcessOrderDisplayTerms.PROCESS_WORKFLOW_ID,
					processWorkflowId);
	
			message
				.put(ProcessOrderDisplayTerms.ACTION_USER_ID, user.getUserId());
	
			message
				.put(ProcessOrderDisplayTerms.PROCESS_ORDER_ID, processOrder.getProcessOrderId());
			message
				.put(ProcessOrderDisplayTerms.FILE_GROUP_ID, 0);
			message
				.put(ProcessOrderDisplayTerms.DOSSIER_ID, dossier.getDossierId());
		
			message
				.put(ProcessOrderDisplayTerms.GROUP_ID, dossier.getGroupId());
	
			message
				.put(ProcessOrderDisplayTerms.COMPANY_ID, dossier.getCompanyId());
	
			SendToEngineMsg sendToEngineMsg = new SendToEngineMsg();
	
			// sendToEngineMsg.setAction(WebKeys.ACTION);
			sendToEngineMsg
				.setActionNote(actionNote);
			sendToEngineMsg
				.setAssignToUserId(0);
			sendToEngineMsg
				.setActionUserId(user.getUserId());
			sendToEngineMsg
				.setDossierId(dossier.getDossierId());
			sendToEngineMsg
				.setFileGroupId(0);
			sendToEngineMsg
				.setPaymentValue(GetterUtil
					.getDouble(0));
			sendToEngineMsg
				.setProcessOrderId(processOrder.getProcessOrderId());
			sendToEngineMsg
				.setProcessWorkflowId(processWorkflowId);
			sendToEngineMsg
				.setReceptionNo(Validator
					.isNotNull(dossier
						.getReceptionNo()) ? dossier
							.getReceptionNo() : StringPool.BLANK);
			sendToEngineMsg
				.setSignature(0);
			message
				.put("msgToEngine", sendToEngineMsg);
			System.out.println("BEFORE SEND============" + message);
			MessageBusUtil
				.sendMessage("opencps/backoffice/engine/destination", message);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProcessOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultObj;
	}	
	
	@JSONWebService(value = "add-dossier-file", method = "POST")
	public JSONObject addDossierFile(String oid, int dossierPartId, String formData, String url) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();

		Dossier dossier = null;
		DossierFile dossierFile = null;
		DossierPart dossierPart = null;
		try {
			dossier = DossierLocalServiceUtil.getByoid(oid);
			long dossierId = dossier.getDossierId();

			ServiceContext serviceContext = new ServiceContext();
			try {
				dossier = DossierLocalServiceUtil
					.getDossier(dossierId);

				dossierPart = DossierPartLocalServiceUtil
					.getDossierPart(dossierPartId);
				serviceContext.setUserId(dossier.getUserId());
				
				DossierFileLocalServiceUtil
					.addDossierFile(dossier.getUserId(), dossierId, dossierPartId, dossierPart
							.getTemplateFileNo(),
						StringPool.BLANK, 0, 0, dossier.getUserId(),
						dossier.getOwnerOrganizationId(),
						"Form mẫu alpaca", formData,
						0,
						PortletConstants.DOSSIER_FILE_MARK_UNKNOW, 1,
						"FILE0001", new Date(), 1,
						PortletConstants.DOSSIER_FILE_SYNC_STATUS_NOSYNC, serviceContext
						);


			}
			catch (Exception e) {

			}
			
			resultObj.put("success", true);
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return resultObj;
	}
	@JSONWebService(value="dossiers", method="GET")
	public JSONObject searchDossierByUserAssignProcessOrder(
		    String username)
		    throws SystemException {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
			int count = dossierLocalService.countDossierByUserAssignProcessOrder(username);
			List<Dossier> dossiers = dossierLocalService.searchDossierByUserAssignProcessOrder(username, 0, count);
			JSONArray resultArr = JSONFactoryUtil.createJSONArray();
			for (Dossier d : dossiers) {
				JSONObject dossierObj = JSONFactoryUtil.createJSONObject();
				dossierObj.put("oid", d.getOid());
				ServiceInfo serviceInfo = null;
				try {
					serviceInfo = ServiceInfoLocalServiceUtil.getServiceInfo(d.getServiceInfoId());
					dossierObj.put("serviceNo", serviceInfo.getServiceNo());
					dossierObj.put("serviceName", serviceInfo.getServiceName());
				}
				catch (NoSuchServiceInfoException e) {
					dossierObj.put("serviceNo", "");	
					dossierObj.put("serviceName", "");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					dossierObj.put("serviceNo", "");
					dossierObj.put("serviceName", "");
				}
				dossierObj.put("address", d.getAddress());
				if (d.getSubmitDatetime() != null) {
					dossierObj.put("submitDatetime", sdf.format(d.getSubmitDatetime()));					
				}
				if (d.getReceiveDatetime() != null) {
					dossierObj.put("receiveDatetime", sdf.format(d.getReceiveDatetime()));
				}
				dossierObj.put("receptionNo", d.getReceptionNo());
				if (d.getEstimateDatetime() != null) {
					dossierObj.put("estimateDatetime", d.getEstimateDatetime());
				}
				dossierObj.put("dossierStatus", d.getDossierStatus());
				dossierObj.put("delayStatus", d.getDelayStatus());
				resultArr.put(dossierObj);
			}
			
			resultObj.put("statusCode", "Success");
			resultObj.put("data", resultArr);
		}
		catch (NoSuchUserException e) {
			resultObj.put("statusCode", "UserNotFound");
		}
		
		return resultObj;
	}
}
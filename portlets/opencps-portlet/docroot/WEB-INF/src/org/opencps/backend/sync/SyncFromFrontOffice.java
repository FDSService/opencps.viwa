/**
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.opencps.backend.sync;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.opencps.accountmgt.model.Business;
import org.opencps.accountmgt.model.Citizen;
import org.opencps.accountmgt.service.BusinessLocalServiceUtil;
import org.opencps.accountmgt.service.CitizenLocalServiceUtil;
import org.opencps.backend.message.SendToEngineMsg;
import org.opencps.backend.message.UserActionMsg;
import org.opencps.backend.util.AutoFillFormData;
import org.opencps.backend.util.BackendUtils;
import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.model.DossierFile;
import org.opencps.dossiermgt.model.DossierPart;
import org.opencps.dossiermgt.model.DossierStatus;
import org.opencps.dossiermgt.service.DossierFileLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierPartLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierStatusLocalServiceUtil;
import org.opencps.servicemgt.model.ServiceInfo;
import org.opencps.servicemgt.service.ServiceInfoLocalServiceUtil;
import org.opencps.util.PortletConstants;
import org.opencps.util.RESTfulUtils;
import org.opencps.util.WebKeys;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;


/**
 * @author khoavd
 *
 */
public class SyncFromFrontOffice implements MessageListener{

	/* (non-Javadoc)
     * @see com.liferay.portal.kernel.messaging.MessageListener#receive(com.liferay.portal.kernel.messaging.Message)
     */
    @Override
    public void receive(Message message)
        throws MessageListenerException {

        try {
        	_doReceiveDossier(message);
        }
        catch (Exception e) {
            _log.error("Unable to process message " + message, e);
        }	    
    }
    
	/**
	 * @param message
	 */
	private void _doReceiveDossier(Message message) {

		UserActionMsg userActionMgs =
		    (UserActionMsg) message.get("msgToEngine");

		String action = userActionMgs.getAction();

		long dosserId = userActionMgs.getDossierId();

		boolean trustServiceMode = true;//_checkServiceMode(dosserId);

		if (trustServiceMode) {
			try {
				if (Validator.equals(WebKeys.ACTION_SUBMIT_VALUE, action) &&
				    _checkStatus(
				        userActionMgs.getDossierId(),
				        userActionMgs.getFileGroupId())) {

					if (Validator.equals(
					    PortletConstants.DOSSIER_STATUS_NEW,
					    BackendUtils.getDossierStatus(
					        userActionMgs.getDossierId(),
					        userActionMgs.getFileGroupId()))) {

						int logLevel = 0;

						long govAgencyOrgId =
						    BackendUtils.getGovAgencyOrgId(userActionMgs.getDossierId());

						// Change dossier status to SYSTEM
						// Update govAgencyOrgId of dossier and dossierFile
					 	Dossier dossier = DossierLocalServiceUtil.updateDossierStatus(
						    userActionMgs.getUserId(),
						    userActionMgs.getDossierId(),
						    govAgencyOrgId,
						    PortletConstants.DOSSIER_STATUS_SYSTEM,
						    PortletConstants.DOSSIER_FILE_SYNC_STATUS_SYNCSUCCESS,
						    userActionMgs.getFileGroupId(), logLevel,
						    userActionMgs.getLocale());

						// Create message
						Message msgToEngine = new Message();

						SendToEngineMsg engineMsg = new SendToEngineMsg();

						engineMsg.setDossierId(userActionMgs.getDossierId());
						engineMsg.setFileGroupId(userActionMgs.getFileGroupId());
						engineMsg.setEvent(WebKeys.ACTION_SUBMIT_VALUE);

						msgToEngine.put("msgToEngine", engineMsg);

						//add data packages to tich hop duongthuy
						DateFormat dateFormat = DateFormatFactoryUtil
								.getSimpleDateFormat("YYYY-MM-dd HH:mm:ss");
						
						Date date = new Date();
						
						String currentDate = dateFormat.format(date);
						
						String integrateURL = PortletProps.get("INTEGRATE_URL");
						
						JSONObject objLv1 = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv2 = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv3Header = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv3Body = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv3SystemSignature = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv4HeaderReference = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv4HeaderFrom = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv4HeaderTo = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv4HeaderSubject = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv4BodyContent = JSONFactoryUtil.createJSONObject();
						
						JSONObject objLv4BodySignature = JSONFactoryUtil.createJSONObject();
						
						objLv4HeaderReference.put("version", "1.0");
						
						objLv4HeaderReference.put("messageId", dossier.getOid()+"_"+System.nanoTime());
						
						objLv4HeaderFrom.put("name", dossier.getContactName());
						
						objLv4HeaderFrom.put("identity", "CDTNDVN");
						
						objLv4HeaderFrom.put("countryCode", "VN");
						
						objLv4HeaderFrom.put("ministryCode", "BGTVT");
						
						objLv4HeaderFrom.put("organizationCode", "CDTNDVN");
						
						objLv4HeaderFrom.put("unitCode", "");
						
						objLv4HeaderTo.put("name", dossier.getGovAgencyName());
						
						objLv4HeaderTo.put("identity", "BGTVT");
						
						objLv4HeaderTo.put("countryCode", "VN");
						
						objLv4HeaderTo.put("ministryCode", "BGTVT");
						
						objLv4HeaderTo.put("organizationCode", "CDTNDVN");
						
						objLv4HeaderTo.put("unitCode", "");
						
						ServiceInfo serviceInfo = ServiceInfoLocalServiceUtil.fetchServiceInfo(dossier.getServiceInfoId());
						
						objLv4HeaderSubject.put("documentType", serviceInfo.getServiceNo());
						
						objLv4HeaderSubject.put("function", "01");
						
						objLv4HeaderSubject.put("reference", dossier.getOid());
						
						objLv4HeaderSubject.put("preReference", dossier.getOid());
						
						Calendar calendar = Calendar.getInstance();
						
						calendar.setTime(date);
						
						objLv4HeaderSubject.put("documentYear", calendar.get(Calendar.YEAR));
						
						objLv4HeaderSubject.put("sendDate", dateFormat.format(date));
						
						//
						objLv3Header.put("Reference", objLv4HeaderReference);
						
						objLv3Header.put("From", objLv4HeaderFrom);
						
						objLv3Header.put("To", objLv4HeaderTo);
						
						objLv3Header.put("Subject", objLv4HeaderSubject);
						
						objLv2.put("Header", objLv3Header);
						//
						JSONObject contentLv1 = JSONFactoryUtil.createJSONObject();
						
						JSONArray contentAttachedFiles = JSONFactoryUtil.createJSONArray();
						
						JSONObject contentAttachedFile = JSONFactoryUtil.createJSONObject();
						//
						List<DossierFile> listDossierFiles = DossierFileLocalServiceUtil.getDossierFileByDossierId(dossier.getDossierId());
						int i = 0;
						for (DossierFile dossierFile : listDossierFiles) {
							i++;
							FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(dossierFile.getFileEntryId());
							
							DossierPart dossierPart = DossierPartLocalServiceUtil.fetchDossierPart(dossierFile.getDossierPartId());
							
							contentAttachedFile.put("AttachedTypeCode", Validator.isNotNull(dossierPart)?dossierFile.getTemplateFileNo():StringPool.BLANK);
							
							contentAttachedFile.put("AttachedTypeName", dossierFile.getDossierFileType());
							
							contentAttachedFile.put("AttachedDocName", dossierFile.getDisplayName());
							
							contentAttachedFile.put("AttachedNote", StringPool.BLANK);
							
							contentAttachedFile.put("AttachedSequenceNo", i);
							
							contentAttachedFile.put("FullFileName", fileEntry.getTitle());
							
							String base64File = StringPool.BLANK;
							
							
							String url =  PortletProps.get("VIRTUAL_HOST") + ":" + PortletProps.get("VIRTUAL_PORT") +  "/documents/"
							        + fileEntry.getGroupId()
							        + StringPool.SLASH
							        + fileEntry.getFolderId()
							        + StringPool.SLASH
							        + fileEntry.getTitle()
							        + "?version="+fileEntry.getVersion();
							
//							base64File = new String(Base64.encode(FileUtil.getBytes(fileEntry.getContentStream())));
							
							contentAttachedFile.put("Base64FileContent", url);
							
							contentAttachedFiles.put(contentAttachedFile);
						}
						
						contentLv1.put("AttachedFile", contentAttachedFiles);
						//
						DossierPart dossierPartOnline = DossierPartLocalServiceUtil.getByF_FORM_ONLINE(dossier.getDossierTemplateId(), 0, dossier.getGroupId(), 1);
//						_log.info("dossierPartOnline" + dossierPartOnline);
						DossierFile dossierFileOnline = null;
						
						if(Validator.isNotNull(dossierPartOnline)){
							try {
								dossierFileOnline = DossierFileLocalServiceUtil.getDossierFileInUse(dossier.getDossierId(), dossierPartOnline.getDossierpartId());
							} catch (Exception e) {
								// TODO: handle exception
							}
						
						}
						
						String sampleData="{}";
						
						if(Validator.isNotNull(dossierFileOnline)){
							
						sampleData = "{\"FromOrganization\":\"#tencoquantrinh@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"Division\":\"#Division@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"ToOrganization\":\"#kinhgui@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"DocNumber\":\"#socongvan@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"BriefContent\":\"#trichyeunoidung@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"DocContent\":\"#noidungvanban@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"SignName\":\"#tennguoiky@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"SignTitle\":\"#chucdanhky@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"SignPlace\":\"#diadiem@"+dossierFileOnline.getTemplateFileNo()+"\","
	                                + "\"SignDate\":\"#ngaythangnam@"+dossierFileOnline.getTemplateFileNo()+"\"}";
						}
						Citizen ownerCitizen = null;
						
						Business ownerBusiness = null;
						
						if(dossier.getOwnerOrganizationId() <= 0){
						
							ownerCitizen = CitizenLocalServiceUtil.getByMappingUserId(dossier.getUserId());
						
						} else {
						
							ownerBusiness = BusinessLocalServiceUtil.getBymappingOrganizationId(dossier.getOwnerOrganizationId());
						
						}
						
						String resultBilding = AutoFillFormData.dataBinding(sampleData, ownerCitizen, ownerBusiness, dossier.getDossierId());
						
						JSONObject resultBildingJson = JSONFactoryUtil.createJSONObject(resultBilding);
						
						contentLv1.put("FromOrganization", resultBildingJson.getString("FromOrganization"));
						
						contentLv1.put("Division", resultBildingJson.getString("Division"));
						
						contentLv1.put("ToOrganization", resultBildingJson.getString("ToOrganization"));
						
						contentLv1.put("DocNumber", resultBildingJson.getString("DocNumber"));
						
						contentLv1.put("BriefContent", resultBildingJson.getString("BriefContent"));
						
						contentLv1.put("DocContent", resultBildingJson.getString("DocContent"));
						
						contentLv1.put("SignName", resultBildingJson.getString("SignName"));
						
						contentLv1.put("SignTitle", resultBildingJson.getString("SignTitle"));
						
						contentLv1.put("SignPlace", resultBildingJson.getString("SignPlace"));
						
						String signDate = resultBildingJson.getString("SignDate");
						String fSignDate = StringPool.BLANK;
						if(Validator.isNotNull(signDate) || signDate.length() > 4){
							try {
								Date fDate = dateFormat.parse(signDate);
								fSignDate = dateFormat.format(fDate);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						contentLv1.put("SignDate", fSignDate);
						//
						
						objLv4BodyContent.put("Declaration", contentLv1);
						
						objLv3Body.put("Content", objLv4BodyContent);
						
						objLv3Body.put("Signature", objLv4BodySignature);
						//
						objLv2.put("Body", objLv3Body);
						
						objLv2.put("SystemSignature", objLv3SystemSignature);
						
						objLv1.put("Envelope", objLv2);
						
						String input = objLv1.toString();
						//add param
						
						JSONObject param = JSONFactoryUtil.createJSONObject();
						
						param.put("userId", dossier.getUserId());
						
						param.put("userName", dossier.getContactName());
						
						param.put("messageFunction", "01");
						
						param.put("messageId", dossier.getOid()+"_"+System.nanoTime());
						
						param.put("messageFileIdData", objLv1);
						
						param.put("version", "1.0");
						
						param.put("sendDate", currentDate);
						
						String inputPOST = param.toString();
						
						RESTfulUtils.responsePOSTAPI(integrateURL+"dossier/addMessageFunctionData", inputPOST);
//						RESTfulUtils.responseGETAPI(integrateURL+"dossier/new/messagefunction/01/messageid/04ac0077-ac36-47ed-9b71-fcf09d6e8706_46044931800432");
						// Send message to ...engine/destination
						MessageBusUtil.sendMessage(
						    "opencps/backoffice/engine/destination",
						    msgToEngine);
						
					}

				}
				else if (Validator.equals(WebKeys.ACTION_RESUBMIT_VALUE, action) &&
				    _checkStatus(
				        userActionMgs.getDossierId(),
				        userActionMgs.getFileGroupId())) {
					
					Message msgToEngine = new Message();

					int logLevel = 0;

					long govAgencyOrgId =
					    BackendUtils.getGovAgencyOrgId(userActionMgs.getDossierId());

					SendToEngineMsg engineMsg = new SendToEngineMsg();

					DossierLocalServiceUtil.updateDossierStatus(
					    userActionMgs.getUserId(),
					    userActionMgs.getDossierId(), govAgencyOrgId,
					    PortletConstants.DOSSIER_STATUS_SYSTEM,
					    PortletConstants.DOSSIER_FILE_SYNC_STATUS_SYNCSUCCESS,
					    userActionMgs.getFileGroupId(), logLevel,
					    userActionMgs.getLocale());

					engineMsg.setDossierId(userActionMgs.getDossierId());
					engineMsg.setFileGroupId(userActionMgs.getFileGroupId());
					engineMsg.setEvent(WebKeys.ACTION_CHANGE_VALUE);
					engineMsg.setActionDatetime(new Date());
					engineMsg.setProcessOrderId(userActionMgs.getProcessOrderId());

					msgToEngine.put("msgToEngine", engineMsg);

					// Send message to ...engine/destination
					MessageBusUtil.sendMessage(
					    "opencps/backoffice/engine/destination", msgToEngine);

				}
				else if (Validator.equals(WebKeys.ACTION_REPAIR_VALUE, action)) {
					// Update requestCommand = repair
					
					Dossier dossier =
					    DossierLocalServiceUtil.fetchDossier(userActionMgs.getDossierId());

					DossierLocalServiceUtil.updateDossierStatus(
					    userActionMgs.getDossierId(),
					    userActionMgs.getFileGroupId(),
					    dossier.getDossierStatus(), dossier.getReceptionNo(),
					    dossier.getEstimateDatetime(),
					    dossier.getReceiveDatetime(),
					    dossier.getFinishDatetime(),
					    WebKeys.ACTOR_ACTION_CITIZEN,
					    WebKeys.ACTION_REPAIR_VALUE,
					    WebKeys.ACTION_REPAIR_VALUE,
					    WebKeys.ACTION_REPAIR_VALUE);
				}
				else if (Validator.equals(WebKeys.ACTION_CLOSE_VALUE, action)) {
					Dossier dossier =
					    DossierLocalServiceUtil.fetchDossier(userActionMgs.getDossierId());

					DossierLocalServiceUtil.updateDossierStatus(
					    userActionMgs.getDossierId(),
					    userActionMgs.getFileGroupId(),
					    dossier.getDossierStatus(), dossier.getReceptionNo(),
					    dossier.getEstimateDatetime(),
					    dossier.getReceiveDatetime(),
					    dossier.getFinishDatetime(),
					    WebKeys.ACTOR_ACTION_CITIZEN,
					    WebKeys.ACTION_CLOSE_VALUE,
					    WebKeys.ACTION_CLOSE_VALUE,
					    WebKeys.ACTION_CLOSE_VALUE);
				}

			}
			catch (Exception e) {
				_log.error(e);
			}

		}

	}

    /**
     * @param dossierId
     * @return
     */
    private boolean _checkServiceMode(long dossierId) {
    	boolean trustServiceMode = false;
    	
    	int serviceMode = 0;
    	
    	try {
	        Dossier dossier = DossierLocalServiceUtil.fetchDossier(dossierId);
	        
	        if (Validator.isNotNull(dossier)) {
	        	serviceMode = dossier.getServiceMode();
	        }
        }
        catch (Exception e) {
        }
    	
    	if (serviceMode == 3) {
    		trustServiceMode = true;
    	}
    	
    	return trustServiceMode;
    }
    
    private boolean _checkStatus(long dossierId, long fileGroupId) {
    	
    	boolean isValidatorStatus = false;
    	
    	DossierStatus status = null;
    	
    	try {
	        status = DossierStatusLocalServiceUtil.getStatus(dossierId, fileGroupId);
        }
        catch (Exception e) {
        	_log.error(e);
        	
        }
    	
    	if (Validator.isNotNull(status)) {
			if (status.getDossierStatus() == PortletConstants.DOSSIER_STATUS_NEW ||
			    status.getDossierStatus() == PortletConstants.DOSSIER_STATUS_WAITING) {
				isValidatorStatus = true;
			}
    	}
    	
    	return isValidatorStatus;
    }
    
    private Log _log = LogFactoryUtil.getLog(SyncFromFrontOffice.class);

}

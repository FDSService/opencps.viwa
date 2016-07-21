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

package org.opencps.api.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.PathParam;

import org.opencps.api.service.ApiServiceLocalServiceUtil;
import org.opencps.api.service.base.ApiServiceServiceBaseImpl;
import org.opencps.backend.message.SendToEngineMsg;
import org.opencps.dossiermgt.NoSuchDossierPartException;
import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.model.DossierFile;
import org.opencps.dossiermgt.model.DossierPart;
import org.opencps.dossiermgt.model.DossierTemplate;
import org.opencps.dossiermgt.model.ServiceConfig;
import org.opencps.dossiermgt.service.DossierFileLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierPartLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierTemplateLocalServiceUtil;
import org.opencps.dossiermgt.service.ServiceConfigLocalServiceUtil;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * The implementation of the api service remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link org.opencps.api.service.ApiServiceService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials because this service
 * can be accessed remotely.
 * </p>
 *
 * @author trungdk
 * @see org.opencps.api.service.base.ApiServiceServiceBaseImpl
 * @see org.opencps.api.service.ApiServiceServiceUtil
 */
public class ApiServiceServiceImpl extends ApiServiceServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * org.opencps.api.service.ApiServiceServiceUtil} to access the api service
	 * remote service.
	 */

	@JSONWebService(value = "dossiers", method = "GET")
	public JSONObject searchDossierByUserAssignProcessOrder(String username)
			throws SystemException {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					DateTimeUtil._VN_DATE_TIME_FORMAT);
			int count = dossierLocalService
					.countDossierByUserAssignProcessOrder(username);
			List<Dossier> dossiers = dossierLocalService
					.searchDossierByUserAssignProcessOrder(username, 0, count);
			JSONArray resultArr = JSONFactoryUtil.createJSONArray();
			long userId = 0;
			for (Dossier d : dossiers) {
				userId = d.getUserId();
				JSONObject dossierObj = JSONFactoryUtil.createJSONObject();
				dossierObj.put("oid", d.getOid());
				ServiceInfo serviceInfo = null;
				try {
					serviceInfo = ServiceInfoLocalServiceUtil.getServiceInfo(d
							.getServiceInfoId());
					dossierObj.put("serviceNo", serviceInfo.getServiceNo());
					dossierObj.put("serviceName", serviceInfo.getServiceName());
				} catch (NoSuchServiceInfoException e) {
					dossierObj.put("serviceNo", "");
					dossierObj.put("serviceName", "");
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					dossierObj.put("serviceNo", "");
					dossierObj.put("serviceName", "");
				}
				dossierObj.put("subjectName", d.getSubjectName());
				dossierObj.put("address", d.getAddress());
				if (d.getSubmitDatetime() != null) {
					dossierObj.put("submitDatetime",
							sdf.format(d.getSubmitDatetime()));
				}
				if (d.getReceiveDatetime() != null) {
					dossierObj.put("receiveDatetime",
							sdf.format(d.getReceiveDatetime()));
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
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setUserId(userId);
			ApiServiceLocalServiceUtil.addApiService(userId, "02", "127.0.0.1",
					"", username, "success", serviceContext);
		} catch (NoSuchUserException e) {
			resultObj.put("statusCode", "UserNotFound");
		}
		return resultObj;
	}

	@JSONWebService(value = "dossiers", method = "GET")
	public JSONObject searchDossierByProcessStepAndUser(String processno,
			String stepno, String username) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat(
				DateTimeUtil._VN_DATE_TIME_FORMAT);
		int count = dossierLocalService.countDossierByP_S_U(processno, stepno,
				username);
		List<Dossier> dossiers = dossierLocalService.searchDossierByP_S_U(
				processno, stepno, username, 0, count);
		JSONArray resultArr = JSONFactoryUtil.createJSONArray();
		for (Dossier d : dossiers) {
			JSONObject dossierObj = JSONFactoryUtil.createJSONObject();
			dossierObj.put("oid", d.getOid());
			ServiceInfo serviceInfo = null;
			try {
				serviceInfo = ServiceInfoLocalServiceUtil.getServiceInfo(d
						.getServiceInfoId());
				dossierObj.put("serviceNo", serviceInfo.getServiceNo());
				dossierObj.put("serviceName", serviceInfo.getServiceName());
			} catch (NoSuchServiceInfoException e) {
				dossierObj.put("serviceNo", "");
				dossierObj.put("serviceName", "");
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				dossierObj.put("serviceNo", "");
				dossierObj.put("serviceName", "");
			} catch (SystemException e) {
				// TODO Auto-generated catch block

			}
			dossierObj.put("subjectName", d.getSubjectName());
			dossierObj.put("address", d.getAddress());
			if (d.getSubmitDatetime() != null) {
				dossierObj.put("submitDatetime",
						sdf.format(d.getSubmitDatetime()));
			}
			if (d.getReceiveDatetime() != null) {
				dossierObj.put("receiveDatetime",
						sdf.format(d.getReceiveDatetime()));
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

		return resultObj;
	}

	@JSONWebService(value = "dossiers", method = "GET")
	public JSONObject getByoid(String oid) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		Dossier dossier;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					DateTimeUtil._VN_DATE_TIME_FORMAT);
			dossier = dossierLocalService.getByoid(oid);
			jsonObject.put("oid", dossier.getOid());
			ServiceInfo serviceInfo = null;
			try {
				serviceInfo = ServiceInfoLocalServiceUtil
						.getServiceInfo(dossier.getServiceInfoId());
				jsonObject.put("serviceNo", serviceInfo.getServiceNo());
				jsonObject.put("serviceName", serviceInfo.getServiceName());
			} catch (NoSuchServiceInfoException e) {
				jsonObject.put("serviceNo", "");
				jsonObject.put("serviceName", "");
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				jsonObject.put("serviceNo", "");
				jsonObject.put("serviceName", "");
			}
			jsonObject.put("govAgencyCode", dossier.getGovAgencyCode());
			jsonObject.put("govAgencyName", dossier.getGovAgencyName());
			jsonObject.put("subjectName", dossier.getSubjectName());
			jsonObject.put("address", dossier.getAddress());
			jsonObject.put("cityCode", dossier.getCityCode());
			jsonObject.put("cityName", dossier.getCityName());
			jsonObject.put("districtCode", dossier.getDistrictCode());
			jsonObject.put("districtName", dossier.getDistrictName());
			jsonObject.put("wardCode", dossier.getWardCode());
			jsonObject.put("wardName", dossier.getWardName());
			jsonObject.put("contactName", dossier.getContactName());
			jsonObject.put("contactTelNo", dossier.getContactTelNo());
			jsonObject.put("contactEmail", dossier.getContactEmail());
			jsonObject.put("note", dossier.getNote());
			if (dossier.getSubmitDatetime() != null) {
				jsonObject.put("submitDatetime",
						sdf.format(dossier.getSubmitDatetime()));
			}
			if (dossier.getReceiveDatetime() != null) {
				jsonObject.put("receiveDatetime",
						sdf.format(dossier.getReceiveDatetime()));
			}
			jsonObject.put("receptionNo", dossier.getReceptionNo());
			if (dossier.getEstimateDatetime() != null) {
				jsonObject.put("estimateDatetime",
						sdf.format(dossier.getEstimateDatetime()));
			}
			if (dossier.getFinishDatetime() != null) {
				jsonObject.put("finishDatetime",
						sdf.format(dossier.getFinishDatetime()));
			}
			jsonObject.put("dossierStatus", dossier.getDossierStatus());
			jsonObject.put("delayStatus", dossier.getDelayStatus());

			List<DossierFile> dossierFiles = DossierFileLocalServiceUtil
					.getDossierFileByDossierId(dossier.getDossierId());
			JSONArray dfArr = JSONFactoryUtil.createJSONArray();

			for (DossierFile df : dossierFiles) {
				JSONObject jsonDossierFile = JSONFactoryUtil.createJSONObject();
				jsonDossierFile.put("dossierFileOid", df.getOid());
				try {
					DossierPart dpart = DossierPartLocalServiceUtil
							.getDossierPart(df.getDossierPartId());
					jsonDossierFile.put("dossierPartNo", dpart.getPartNo());
				} catch (NoSuchDossierPartException e) {

				} catch (PortalException e) {
					// TODO Auto-generated catch block

				}
				jsonDossierFile.put("dossierFileName", df.getDisplayName());
				jsonDossierFile.put("templateFileNo", df.getTemplateFileNo());
				jsonDossierFile.put("dossierFileNo", df.getDossierFileNo());

				if (df.getFileEntryId() > 0) {
					FileEntry fileEntry;
					try {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(df
								.getFileEntryId());
						jsonDossierFile.put(
								"dossierFullFileName",
								df.getDisplayName() + "."
										+ fileEntry.getExtension());

						String url = PortletProps.get("VIRTUAL_HOST") + ":"
								+ PortletProps.get("VIRTUAL_PORT")
								+ "/documents/" + fileEntry.getGroupId()
								+ StringPool.SLASH + fileEntry.getFolderId()
								+ StringPool.SLASH + fileEntry.getTitle()
								+ "?version=" + fileEntry.getVersion();

						jsonDossierFile.put("dossierFileURL", url);

					} catch (PortalException e) {
						// TODO Auto-generated catch block
					}
				} else {
					jsonDossierFile.put("dossierFileContent", df.getFormData());
					jsonDossierFile.put("dossierFileURL", "");
				}

				if (df.getDossierFileDate() != null) {
					jsonDossierFile.put("dossierFileDate",
							sdf.format(df.getDossierFileDate()));
				}
				dfArr.put(jsonDossierFile);
			}

			jsonObject.put("dossierFiles", dfArr);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			jsonObject.put("statusCode", "DossierNotFound");
		}

		return jsonObject;
	}

	@JSONWebService(value = "dossierfile", method = "POST")
	public JSONObject addDossierFile(String oid, String dossierfile) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		System.out.println("DOSSIER FILE CONTENT==============" + dossierfile);
		try {
			JSONObject dossierfileObj = JSONFactoryUtil
					.createJSONObject(dossierfile);
			String dossierFileOid = dossierfileObj.getString("dossierFileOid");
			String dossierPartNo = dossierfileObj.getString("dossierPartNo");
			String dossierFileContent = dossierfileObj
					.getString("dossierFileContent");
			String dossierFileURL = dossierfileObj.getString("dossierFileURL");
			String templateFileNo = dossierfileObj.getString("templateFileNo");
			String dossierFileName = dossierfileObj
					.getString("dossierFileName");
			String dossierFileNo = dossierfileObj.getString("dossierFileNo");
			String dossierFileDate = dossierfileObj
					.getString("dossierFileDate");
			System.out.println("DOSSIER FILE OID==============="
					+ dossierFileOid);
			System.out.println("DOSSIER FILE URL==============="
					+ dossierFileURL);
			if (dossierFileOid.equals("")) {
				// Add new dossier file
				if (dossierFileURL.equals("")) {
					System.out.println("ADD NEW DOSSIER FILE=============");
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
									.getDossierPartByPartNo(dossierPartNo);

							serviceContext.setUserId(dossier.getUserId());

							DossierFileLocalServiceUtil
									.addDossierFile(
											dossier.getUserId(),
											dossierId,
											dossierPart.getDossierpartId(),
											dossierPart.getTemplateFileNo(),
											StringPool.BLANK,
											0,
											0,
											dossier.getUserId(),
											dossier.getOwnerOrganizationId(),
											dossierFileName,
											dossierFileContent,
											0,
											PortletConstants.DOSSIER_FILE_MARK_UNKNOW,
											1,
											dossierFileNo,
											new Date(),
											1,
											PortletConstants.DOSSIER_FILE_SYNC_STATUS_NOSYNC,
											serviceContext);

						} catch (Exception e) {

						}
					} catch (SystemException ee) {
						// TODO Auto-generated catch block

					}
				} else if (dossierFileContent.equals("")) {
					try {
						URL fileURL = new URL(dossierFileURL);
						InputStream is = fileURL.openStream();
						long size = is.available();
						ServiceContext serviceContext = ServiceContextThreadLocal
								.getServiceContext();
						Dossier dossier = null;
						DossierFile dossierFile = null;
						DossierPart dossierPart = null;
						try {
							System.out
									.println("GET DOSSIER FOLDER=============");
							dossier = DossierLocalServiceUtil.getByoid(oid);
							long dossierId = dossier.getDossierId();
							DLFolder dossierFolder = DLFolderUtil
									.getDossierFolder(
											serviceContext.getScopeGroupId(),
											dossier.getUserId(),
											dossier.getCounter(),
											serviceContext);
							dossierPart = DossierPartLocalServiceUtil
									.getDossierPartByPartNo(dossierPartNo);
							System.out
									.println("ADD DOSSIER FILE FROM URL=============");
							DossierFileLocalServiceUtil
									.addDossierFile(
											serviceContext.getUserId(),
											dossierId,
											dossierPart.getDossierpartId(),
											dossierPart.getTemplateFileNo(),
											StringPool.BLANK,
											0L,
											0L,
											dossier.getUserId(),
											dossier.getOwnerOrganizationId(),
											dossierFileName,
											StringPool.BLANK,
											dossierFile != null ? dossierFile
													.getFileEntryId() : 0,
											PortletConstants.DOSSIER_FILE_MARK_UNKNOW,
											1,
											dossierFileNo,
											new Date(),
											1,
											PortletConstants.DOSSIER_FILE_SYNC_STATUS_NOSYNC,
											dossierFolder.getFolderId(),
											dossierFileNo, "", dossierFileName,
											StringPool.BLANK, StringPool.BLANK,
											is, size, serviceContext);
						} catch (SystemException e) {

						} catch (PortalException e) {
							// TODO Auto-generated catch block

						}

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block

					} catch (IOException e) {
						// TODO Auto-generated catch block

					}

				}
			} else {
				if (dossierFileURL.equals("")) {
					DossierFile dossierFile = null;
					try {
						dossierFile = DossierFileLocalServiceUtil
								.getByOid(dossierFileOid);
						DossierPart dossierPart = null;
						Dossier dossier = null;
						try {
							dossier = DossierLocalServiceUtil.getByoid(oid);
							long dossierId = dossier.getDossierId();

							ServiceContext serviceContext = new ServiceContext();
							try {
								dossier = DossierLocalServiceUtil
										.getDossier(dossierId);

								dossierPart = DossierPartLocalServiceUtil
										.getDossierPartByPartNo(dossierPartNo);

								serviceContext.setUserId(dossier.getUserId());

								DossierFileLocalServiceUtil
										.addDossierFile(
												dossier.getUserId(),
												dossierId,
												dossierPart.getDossierpartId(),
												dossierPart.getTemplateFileNo(),
												StringPool.BLANK,
												0,
												0,
												dossier.getUserId(),
												dossier.getOwnerOrganizationId(),
												dossierFileName,
												dossierFileContent,
												dossierFile != null ? dossierFile
														.getFileEntryId() : 0,
												PortletConstants.DOSSIER_FILE_MARK_UNKNOW,
												1,
												dossierFileNo,
												new Date(),
												1,
												PortletConstants.DOSSIER_FILE_SYNC_STATUS_NOSYNC,
												serviceContext);

							} catch (Exception e) {

							}
						} catch (SystemException ee) {
							// TODO Auto-generated catch block

						}

					} catch (SystemException e) {
						// TODO Auto-generated catch block

					}

				} else {
					try {
						DossierFile dossierFile = null;
						dossierFile = DossierFileLocalServiceUtil
								.getByOid(dossierFileOid);
						URL fileURL = new URL(dossierFileURL);
						InputStream is = fileURL.openStream();
						long size = is.available();
						ServiceContext serviceContext = ServiceContextThreadLocal
								.getServiceContext();
						Dossier dossier = null;
						DossierPart dossierPart = null;

						System.out.println("GET DOSSIER FOLDER=============");
						dossier = DossierLocalServiceUtil.getByoid(oid);
						long dossierId = dossier.getDossierId();
						DLFolder dossierFolder = DLFolderUtil.getDossierFolder(
								serviceContext.getScopeGroupId(),
								dossier.getUserId(), dossier.getCounter(),
								serviceContext);
						dossierPart = DossierPartLocalServiceUtil
								.getDossierPartByPartNo(dossierPartNo);
						System.out
								.println("ADD DOSSIER FILE FROM URL=============");
						DossierFileLocalServiceUtil
								.addDossierFile(
										serviceContext.getUserId(),
										dossierId,
										dossierPart.getDossierpartId(),
										dossierPart.getTemplateFileNo(),
										StringPool.BLANK,
										0L,
										0L,
										dossier.getUserId(),
										dossier.getOwnerOrganizationId(),
										dossierFileName,
										StringPool.BLANK,
										dossierFile != null ? dossierFile
												.getFileEntryId() : 0,
										PortletConstants.DOSSIER_FILE_MARK_UNKNOW,
										1,
										dossierFileNo,
										new Date(),
										1,
										PortletConstants.DOSSIER_FILE_SYNC_STATUS_NOSYNC,
										dossierFolder.getFolderId(),
										dossierFileNo, "", dossierFileName,
										StringPool.BLANK, StringPool.BLANK, is,
										size, serviceContext);

					} catch (SystemException e) {
						// TODO Auto-generated catch block

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block

					} catch (IOException e) {
						// TODO Auto-generated catch block

					} catch (NoSuchDossierPartException e) {
						// TODO Auto-generated catch block

					} catch (PortalException e) {
						// TODO Auto-generated catch block

					}

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Dossier dossier = null; DossierFile dossierFile = null; DossierPart
		 * dossierPart = null; try { dossier =
		 * DossierLocalServiceUtil.getByoid(oid); long dossierId =
		 * dossier.getDossierId();
		 * 
		 * ServiceContext serviceContext = new ServiceContext(); try { dossier =
		 * DossierLocalServiceUtil .getDossier(dossierId);
		 * 
		 * dossierPart = DossierPartLocalServiceUtil
		 * .getDossierPart(dossierPartId);
		 * serviceContext.setUserId(dossier.getUserId());
		 * 
		 * DossierFileLocalServiceUtil .addDossierFile(dossier.getUserId(),
		 * dossierId, dossierPartId, dossierPart .getTemplateFileNo(),
		 * StringPool.BLANK, 0, 0, dossier.getUserId(),
		 * dossier.getOwnerOrganizationId(), "Form mẫu alpaca", formData, 0,
		 * PortletConstants.DOSSIER_FILE_MARK_UNKNOW, 1, "FILE0001", new Date(),
		 * 1, PortletConstants.DOSSIER_FILE_SYNC_STATUS_NOSYNC, serviceContext
		 * );
		 * 
		 * 
		 * } catch (Exception e) {
		 * 
		 * }
		 * 
		 * resultObj.put("success", true); } catch (SystemException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); }
		 */
		resultObj.put("statusCode", "Success");
		return resultObj;
	}

	@JSONWebService(value = "processorder", method = "POST")
	public JSONObject nextStep(String oid, String actioncode, String username) {
		JSONObject resultObj = JSONFactoryUtil.createJSONObject();
		Dossier dossier = null;
		try {
			dossier = DossierLocalServiceUtil.getByoid(oid);
		} catch (SystemException e) {
			resultObj.put("statusCode", "DossierNotFound");
			return resultObj;
		}
		try {
			System.out.println("PROCESS ORDER============"
					+ dossier.getDossierId());
			ProcessOrder processOrder = ProcessOrderLocalServiceUtil
					.getProcessOrder(dossier.getDossierId(), 0);
			User user = UserLocalServiceUtil.getUserByScreenName(
					dossier.getCompanyId(), username);
			int processWorkflowId = Integer.parseInt(actioncode);
			ProcessWorkflow processWorkflow = ProcessWorkflowLocalServiceUtil
					.getProcessWorkflow(processWorkflowId);
			Message message = new Message();
			message.put(ProcessOrderDisplayTerms.EVENT, null);
			message.put(ProcessOrderDisplayTerms.ACTION_NOTE,
					"Chuyển trạng thái");
			message.put(ProcessOrderDisplayTerms.PROCESS_STEP_ID,
					processOrder.getProcessStepId());
			message.put(ProcessOrderDisplayTerms.ASSIGN_TO_USER_ID, 0);
			message.put(ProcessOrderDisplayTerms.SERVICE_PROCESS_ID,
					processOrder.getServiceProcessId());
			message.put(ProcessOrderDisplayTerms.PAYMENTVALUE, 0);

			message.put(ProcessOrderDisplayTerms.PROCESS_WORKFLOW_ID,
					processWorkflowId);

			message.put(ProcessOrderDisplayTerms.ACTION_USER_ID,
					user.getUserId());

			message.put(ProcessOrderDisplayTerms.PROCESS_ORDER_ID,
					processOrder.getProcessOrderId());
			message.put(ProcessOrderDisplayTerms.FILE_GROUP_ID, 0);
			message.put(ProcessOrderDisplayTerms.DOSSIER_ID,
					dossier.getDossierId());

			message.put(ProcessOrderDisplayTerms.GROUP_ID, dossier.getGroupId());

			message.put(ProcessOrderDisplayTerms.COMPANY_ID,
					dossier.getCompanyId());

			SendToEngineMsg sendToEngineMsg = new SendToEngineMsg();

			// sendToEngineMsg.setAction(WebKeys.ACTION);
			sendToEngineMsg.setActionNote("Chuyển trạng thái");
			sendToEngineMsg.setAssignToUserId(0);
			sendToEngineMsg.setActionUserId(user.getUserId());
			sendToEngineMsg.setDossierId(dossier.getDossierId());
			sendToEngineMsg.setFileGroupId(0);
			sendToEngineMsg.setPaymentValue(GetterUtil.getDouble(0));
			sendToEngineMsg.setProcessOrderId(processOrder.getProcessOrderId());
			sendToEngineMsg.setProcessWorkflowId(processWorkflowId);
			sendToEngineMsg.setReceptionNo(Validator.isNotNull(dossier
					.getReceptionNo()) ? dossier.getReceptionNo()
					: StringPool.BLANK);
			sendToEngineMsg.setSignature(0);
			message.put("msgToEngine", sendToEngineMsg);
			System.out.println("BEFORE SEND============" + message);
			MessageBusUtil.sendMessage("opencps/backoffice/engine/destination",
					message);
			resultObj.put("statusCode", "Success");
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			resultObj.put("statusCode", "ActionNotFound");
		} catch (NoSuchProcessOrderException e) {
			// TODO Auto-generated catch block
			resultObj.put("statusCode", "ActionNotFound");
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			resultObj.put("statusCode", "ActionNotFound");
		}
		return resultObj;
	}
}
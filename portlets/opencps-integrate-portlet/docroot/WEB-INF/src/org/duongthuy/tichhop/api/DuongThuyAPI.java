package org.duongthuy.tichhop.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.duongthuy.tichhop.api.bean.ResponseBean;
import org.duongthuy.tichhop.api.dao.model.MessageFunctionData;
import org.duongthuy.tichhop.api.dao.service.MessageFunctionDataLocalServiceUtil;
import org.duongthuy.tichhop.api.message.DossierMessage;
import org.duongthuy.tichhop.util.DateTimeUtil;
import org.duongthuy.tichhop.util.RESTfulUtils;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

@Path("/dossier")
public class DuongThuyAPI {

	@GET
	@Path("/new/messagefunction/{messagefunction}/messageid/{messageid}")
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response getNewDossier(@NotNull @PathParam("messagefunction") String messagefunction,
			 @PathParam("messageid") String messageid) {
		String output = StringPool.BLANK;
		InputStream inputStream = null;
		try {
			_log.info("************** messagefunction: "+messagefunction);
			_log.info("************** messageid: "+messageid);
			
			//(\\w+)
			MessageFunctionData messageFunctionData = MessageFunctionDataLocalServiceUtil.getByF_O(messagefunction, messageid);
			
			if(Validator.isNotNull(messageFunctionData)){
				output = messageFunctionData.getMessageFileIdData();
			}else{
				output = "{\"error\":\"no messageid\"}";
				return Response.status(200).entity(output).build();
			}
			//output = messageFunctionData.getMessageFileIdData();
			
//			long fileId = 0;
//			
//			if(Validator.isNotNull(messageFunctionData)){
//				
//				fileId = messageFunctionData.getMessageFileIdData();
//				
//			}
//			
//			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileId);
//			
//			_log.info(dlFileEntry);
//			
//			if(Validator.isNotNull(dlFileEntry)){
//				
//				//RESTfulUtils.acceptReadFile();
//				
//				inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(dlFileEntry.getFileEntryId(), dlFileEntry.getVersion());
//				
//				int i;
//			    
//				char c;
//			    
//			    while((i=inputStream.read())!=-1)
//			    {
//			    	c=(char)i;
//			            
//			    	output += c;
//			            
//			    }
//				
//			}
			
			_log.info("************** output - new *******************: "+output);
//			File file = FileUtil.createTempFile(dlFileEntry.getContentStream());
//			_log.info("****************"+file.length() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.info("************ no messageid *******************");
			output = "{\"error\":\"no messageid\"}";
			return Response.status(200).entity(output).build();
		}finally{

			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/update/messagefunction/{messagefunction}/messageid/{messageid}")
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response getUpdateDossier(@NotNull @PathParam("messagefunction") String messagefunction,
			 @PathParam("messageid") String messageid) {
		String output = StringPool.BLANK;
		InputStream inputStream = null;
		try {
			_log.info("************** messagefunction: "+messagefunction);
			_log.info("************** messageid: "+messageid);
			//(\\w+)
			MessageFunctionData messageFunctionData = null;
			if (messagefunction.equals("02")) {
				messageFunctionData = MessageFunctionDataLocalServiceUtil.getByMI(messageid);				
			}
			else {
				
			}
			messageFunctionData = MessageFunctionDataLocalServiceUtil.getByF_O(messagefunction, messageid);
			
			if(Validator.isNotNull(messageFunctionData)){
				output = messageFunctionData.getMessageFileIdData();
			}else{
				output = "{\"error\":\"no messageid\"}";
				return Response.status(200).entity(output).build();
			}
			//output = messageFunctionData.getMessageFileIdData();
			
//			long fileId = 0;
//			
//			if(Validator.isNotNull(messageFunctionData)){
//				
//				fileId = messageFunctionData.getMessageFileIdData();
//				
//			}
//
//			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileId);
//
//			_log.info(dlFileEntry);
//			
//			if(Validator.isNotNull(dlFileEntry)){
//				
//				RESTfulUtils.acceptReadFile();
//				
//				inputStream = dlFileEntry.getContentStream();
//				
//				int i;
//			    
//				char c;
//			    
//			    while((i=inputStream.read())!=-1)
//			    {
//			    	c=(char)i;
//			            
//			    	output += c;
//			            
//			    }
//				
//			}
			
			_log.info("************** output - update *******************: "+output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.info("************ no messageid *******************");
			output = "{\"error\":\"no messageid\"}";
			return Response.status(200).entity(output).build();
			
		}finally{

			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/cancel/messagefunction/{messagefunction}/messageid/{messageid}")
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response getCancelDossier(@NotNull @PathParam("messagefunction") String messagefunction,
			 @PathParam("messageid") String messageid) {
		String output = StringPool.BLANK;
		InputStream inputStream = null;
		try {
			_log.info("************** messagefunction: "+messagefunction);
			_log.info("************** messageid: "+messageid);
			//(\\w+)
			MessageFunctionData messageFunctionData = MessageFunctionDataLocalServiceUtil.getByF_O(messagefunction, messageid);
			
			if(Validator.isNotNull(messageFunctionData)){
				output = messageFunctionData.getMessageFileIdData();
			}else{
				output = "{\"error\":\"no messageid\"}";
				return Response.status(200).entity(output).build();
			}
			//output = messageFunctionData.getMessageFileIdData();
			
//			long fileId = 0;
//			
//			if(Validator.isNotNull(messageFunctionData)){
//				
//				fileId = messageFunctionData.getMessageFileIdData();
//				
//			}
//
//			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(fileId);
//
//			_log.info(dlFileEntry);
//			
//			if(Validator.isNotNull(dlFileEntry)){
//				
//				RESTfulUtils.acceptReadFile();
//				
//				inputStream = dlFileEntry.getContentStream();
//				
//				int i;
//			    
//				char c;
//			    
//			    while((i=inputStream.read())!=-1)
//			    {
//			    	c=(char)i;
//			            
//			    	output += c;
//			            
//			    }
//				
//			}
			
			_log.info("************** output - update *******************: "+output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.info("************ no messageid *******************");
			output = "{\"error\":\"no messageid\"}";
			return Response.status(200).entity(output).build();
			
		}finally{

			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
		return Response.status(200).entity(output).build();
	}
	
	//POST insert messageId
	@POST
	@Path("/addMessageFunctionData")
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response addMessageFunctionData(String input){
		String output = StringPool.BLANK;
		InputStream fileInputStream = null;
		try {
			//(\\w+)
			if(Validator.isNull(input)){
		        return Response.status(400).entity("Create packages fail!").build();
		    }
			JSONObject inputJsonObject = JSONFactoryUtil.createJSONObject(input);
			_log.info("userId: "+inputJsonObject.getString("userId").toString());
			_log.info("userName: "+inputJsonObject.getString("userName").toString());
			_log.info("messageFunction: "+inputJsonObject.getString("messageFunction").toString());
			_log.info("messageId: "+inputJsonObject.getString("messageId").toString());
			_log.info("version: "+inputJsonObject.getString("version").toString());
			_log.info("sendDate: "+inputJsonObject.getString("sendDate").toString());

			String userId = inputJsonObject.getString("userId");
			String userName = inputJsonObject.getString("userName");
			String messageFunction = inputJsonObject.getString("messageFunction");
			String messageId = inputJsonObject.getString("messageId");
			String messageFileIdData = inputJsonObject.getString("messageFileIdData");
			String version = inputJsonObject.getString("version");
			String sendDate = inputJsonObject.getString("sendDate");
			DateFormat dateFormat = DateFormatFactoryUtil
					.getSimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			Date dateSend = null;
			if(Validator.isNotNull(sendDate)){
				dateSend = dateFormat.parse(sendDate);
			}
//			ServiceContext serviceContext = RESTfulUtils.getServiceContextIntegrate();
//			
//			
//			DLFolder dlFolderOPENCPS = DLFolderLocalServiceUtil.getFolder(serviceContext.getScopeGroupId(), 0, PortletProps.get("OPENCPS")) ;
//			DLFolder dlFolderINTEGRATE = DLFolderLocalServiceUtil.getFolder(serviceContext.getScopeGroupId(), dlFolderOPENCPS.getFolderId(), PortletProps.get("INTEGRATE")) ;
//			
//			long repositoryId = 0;
//			long folderId = 0;
//			String sourceFileName = "";
//			String mimeType = "";
//			String title = "";
//			byte[] bytes = null;
//			
//			repositoryId = dlFolderINTEGRATE.getRepositoryId();
//			folderId = dlFolderINTEGRATE.getFolderId();
//			mimeType = "text/html";
//			sourceFileName =
//					PortletProps.get("opencps.file.system.temp.dir")+"INTEGRATE/"+inputJsonObject.getString("messageFunction")+"_"+inputJsonObject.getString("messageId");
//			title = inputJsonObject.getString("messageFunction")+"_"+inputJsonObject.getString("messageId");
//			
//			fileInputStream = new ByteArrayInputStream(inputJsonObject.getJSONObject("messageFileIdData").toString().getBytes("UTF-8"));
////			
//			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(serviceContext.getUserId(), 
//					repositoryId, 
//					folderId, 
//					sourceFileName, 
//					mimeType, 
//					title, 
//					"Integrate", 
//					"Integrate ChangeLog", 
//					RESTfulUtils.getBytes(fileInputStream), 
//					serviceContext);
			
			MessageFunctionData meData =MessageFunctionDataLocalServiceUtil.addMessageFunctionData(userId, userName, messageFunction, messageId, messageFileIdData,version,dateSend);
//			_log.info("************** output - fileEntry *******************: "+fileEntry.getFileEntryId());
			JSONObject responeJson = JSONFactoryUtil.createJSONObject();
			responeJson.put("ReceiveDate", dateFormat.format(new Date()));
			output = responeJson.toString();
			_log.info("************** output - addMessageFunctionData *******************: "+output);
			String oid = StringPool.BLANK;
			if(Validator.isNotNull(meData) && meData.getMessageId().contains("_")){
				oid = meData.getMessageId().substring(0, meData.getMessageId().lastIndexOf("_"));
			
				JSONObject paramNotification = JSONFactoryUtil.createJSONObject();
				paramNotification.put("messagefunction", meData.getMessageFunction());
				paramNotification.put("messageid", meData.getMessageId());
				paramNotification.put("messagecontent", "Call responsePOSTAPI_Notification");
				paramNotification.put("reference", oid);
				_log.info("==========================================");
				_log.info("==============" + paramNotification.toString() + "=================");
				_log.info("==========================================");
				String inputPOSTNotification = paramNotification.toString();
			RESTfulUtils.responsePOSTAPI_Notification("http://daotao.viwa.gov.vn/notifications/instance", inputPOSTNotification, "dvcviwa","dvc2016");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			if(fileInputStream != null || Validator.isNotNull(fileInputStream)){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return Response.status(201).entity(output).build();
	}
	
	@POST
	@Path("/instance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dossierInstance(
			@FormParam("messagefunction") @DefaultValue("21") String messagefunction, 
			@FormParam("messageid") @DefaultValue("") String messageid, 
			@FormParam("messagecontent") @DefaultValue("") String messagecontent) {
		
		String output = StringPool.BLANK;
		JSONObject inputJsonObject;
		try {

			
			_log.info("Message function: " + messagefunction);
			if (messagefunction != null) {
				switch (messagefunction) {
				case "01":
					break;
				case "02":
					break;
				case "03":			
					break;
				case "11":
					output = confirmCancellation(messagefunction, messageid, messagecontent);
					break;
				case "20":
					output = requestChanged(messagefunction, messageid, messagecontent);
					break;
				case "21":
					output = acceptance(messagefunction, messageid, messagecontent);
					break;
				case "22":
					output = reject(messagefunction, messageid, messagecontent);
					break;
				case "23":
					output = noticeOfAssignment(messagefunction, messageid, messagecontent);
					break;
				case "24":
					output = confirmCancellation(messagefunction, messageid, messagecontent);
					break;
				default:
					output = "{ 'error': 'Parameter not valid' }";
					break;
				}				
			}
			else {
				output = "{ 'error': 'Parameter not valid' }";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
			//inputJsonObject.put("ReceiveDate", df.format(new Date()));
	
			output = "{ 'error': 'JSON error', 'receiveDate': '" + df.format(new Date()) + "'}";

			//e.printStackTrace();
			_log.error(e);
		}
		
		
		return Response.status(200).entity(output).build();
	}	
	/*
	public Response dossierInstance(String input) {
		String output = StringPool.BLANK;
		_log.info("INPUT JSON: -----------------" + input);
		if(Validator.isNull(input)){
		    return Response.status(400).entity("Create packages fail!").build();
		}
		JSONObject inputJsonObject;
		try {
			inputJsonObject = JSONFactoryUtil.createJSONObject(input);
			_log.info("messagefunction: "+inputJsonObject.getString("messagefunction").toString());
			_log.info("messageid: "+inputJsonObject.getString("messageid").toString());
			_log.info("messagecontent: "+inputJsonObject.getString("messagecontent").toString());

			String messagefunction = inputJsonObject.getString("messagefunction");
			String messageid = inputJsonObject.getString("messageid");
			String messagecontent = inputJsonObject.getString("messagecontent");
			
			_log.info("Message function: " + messagefunction);
			if (messagefunction != null) {
				switch (messagefunction) {
				case "01":
					break;
				case "02":
					break;
				case "03":			
					break;
				case "11":
					output = confirmCacellation(messagefunction, messageid, messagecontent);
					break;
				case "20":
					output = requestChanged(messagefunction, messageid, messagecontent);
					break;
				case "21":
					output = acceptance(messagefunction, messageid, messagecontent);
					break;
				case "22":
					output = reject(messagefunction, messageid, messagecontent);
					break;
				case "23":
					output = noticeOfAssignment(messagefunction, messageid, messagecontent);
					break;
				case "24":
					output = confirmCacellation(messagefunction, messageid, messagecontent);
					break;
				default:
					output = "{ 'error': 'Parameter not valid' }";
					break;
				}				
			}
			else {
				output = "{ 'error': 'Parameter not valid' }";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
			//inputJsonObject.put("ReceiveDate", df.format(new Date()));
	
			output = "{ 'error': 'JSON error', 'content': '" + input + "', 'receiveDate': '" + df.format(new Date()) + "'}";
			//e.printStackTrace();
			_log.error(e);
		}
		
		
		return Response.status(200).entity(output).build();
	}
	*/
	//POST insert messageId
	@POST
	@Path("/cancelMessageFunctionData")
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response cancelMessageFunctionData(String input){
		String output = StringPool.BLANK;
		InputStream fileInputStream = null;
		try {
			//(\\w+)
			if(Validator.isNull(input)){
		        return Response.status(400).entity("Create packages fail!").build();
		    }
			JSONObject inputJsonObject = JSONFactoryUtil.createJSONObject(input);
			_log.info("userId: "+inputJsonObject.getString("userId").toString());
			_log.info("userName: "+inputJsonObject.getString("userName").toString());
			_log.info("messageFunction: "+inputJsonObject.getString("messageFunction").toString());
			_log.info("messageId: "+inputJsonObject.getString("messageId").toString());
			_log.info("version: "+inputJsonObject.getString("version").toString());
			_log.info("sendDate: "+inputJsonObject.getString("sendDate").toString());

			String userId = inputJsonObject.getString("userId");
			String userName = inputJsonObject.getString("userName");
			String messageFunction = inputJsonObject.getString("messageFunction");
			String messageId = inputJsonObject.getString("messageId");
			String messageFileIdData = inputJsonObject.getString("messageFileIdData");
			String version = inputJsonObject.getString("version");
			String sendDate = inputJsonObject.getString("sendDate");
			DateFormat dateFormat = DateFormatFactoryUtil
					.getSimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			Date dateSend = null;
			if(Validator.isNotNull(sendDate)){
				dateSend = dateFormat.parse(sendDate);
			}
			
			MessageFunctionData meData =MessageFunctionDataLocalServiceUtil.addMessageFunctionData(userId, userName, messageFunction, messageId, messageFileIdData,version,dateSend);
//			_log.info("************** output - fileEntry *******************: "+fileEntry.getFileEntryId());
			JSONObject responeJson = JSONFactoryUtil.createJSONObject();
			responeJson.put("ReceiveDate", dateFormat.format(new Date()));
			output = responeJson.toString();
			_log.info("************** output - addMessageFunctionData *******************: "+output);
			String oid = StringPool.BLANK;
			if(Validator.isNotNull(meData) && meData.getMessageId().contains("_")){
				oid = meData.getMessageId().substring(0, meData.getMessageId().lastIndexOf("_"));
			
				JSONObject paramNotification = JSONFactoryUtil.createJSONObject();
				paramNotification.put("messagefunction", meData.getMessageFunction());
				paramNotification.put("messageid", meData.getMessageId());
				paramNotification.put("messagecontent", "Call responsePOSTAPI_Notification");
				paramNotification.put("reference", oid);
				String inputPOSTNotification = paramNotification.toString();
			RESTfulUtils.responsePOSTAPI_Notification("http://daotao.viwa.gov.vn/notifications/instance", inputPOSTNotification, "dvcviwa","dvc2016");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			if(fileInputStream != null || Validator.isNotNull(fileInputStream)){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return Response.status(201).entity(output).build();
	}
	
	@GET
	@Path("/dossiertype/{dossiertype}/organizationcode/{organizationcode}/status/{status: .+}/fromdate/{fromdate: .+}/todate/{todate: .+}/documentyear/{documentyear: .+}/customername/{customername: .+}")
	@Produces("application/json;charset=utf-8")
	public Response searchDossier(
			@PathParam("dossiertype") String dossiertype, 
			@PathParam("organizationcode") String organizationcode, 
			@PathParam("status") @DefaultValue("-1") String status, 
			@PathParam("fromdate") @DefaultValue("") String fromdate,
			@PathParam("todate") @DefaultValue("") String todate,
			@PathParam("documentyear") @DefaultValue("") String documentyear,
			@PathParam("customername") @DefaultValue("") String customername) {
		String output = StringPool.BLANK;
		
		JSONObject sparam = JSONFactoryUtil.createJSONObject();
		sparam.put("dossiertype", dossiertype);
		sparam.put("organizationcode", organizationcode);
		sparam.put("status", status);
		sparam.put("fromdate", fromdate);
		sparam.put("todate", todate);
		sparam.put("documentyear", documentyear);
		sparam.put("customername", customername);
		String inputPOSTSearch = sparam.toString();
		
		String param = "dossiertype/" + dossiertype + "/organizationcode/" + organizationcode + "/status/" + status + "/fromdate/" + fromdate + "/todate/" + todate + "/documentyear/" + documentyear + "/customername/" + customername;
		param = "dossiertype/" + dossiertype + "/organizationcode/" + organizationcode;
		if ("''".equals(todate) && "''".equals(fromdate)) {
			param = "dossiertype/" + dossiertype + "/organizationcode/" + organizationcode + "/status/" + status + "/-fromdate/" + "-todate/" + "/documentyear/" + documentyear + "/customername/" + customername;
		}
		else if ("''".equals(fromdate)) {
			param = "dossiertype/" + dossiertype + "/organizationcode/" + organizationcode + "/status/" + status + "/-fromdate" + "/todate/" + todate + "/documentyear/" + documentyear + "/customername/" + customername;			
		}
		else if ("''".equals(todate)) { 
			param = "dossiertype/" + dossiertype + "/organizationcode/" + organizationcode + "/status/" + status + "/fromdate/" + fromdate + "/-todate" + "/documentyear/" + documentyear + "/customername/" + customername;						
		}
		else {
			param = "dossiertype/" + dossiertype + "/organizationcode/" + organizationcode + "/status/" + status + "/fromdate/" + fromdate + "/todate/" + todate + "/documentyear/" + documentyear + "/customername/" + customername;		
		}
		//http://localhost:8080/api/jsonws/opencps-portlet.dossier/search-dossier/dossiertype/-1/organizationcode/sdf/status/1/fromdate/2015-12-01%2000%3A00%3A00/todate/2016-11-01%2000%3A00%3A00/documentyear/2015/customername/Test
		//output = RESTfulUtils.responseGETAPI(PortletProps.get("PORTAL_URL") + ".dossier/search-dossier/dossiertype/-1/organizationcode/sdf/status/1/fromdate/2015-12-01%2000%3A00%3A00/todate/2016-11-01%2000%3A00%3A00/documentyear/2015/customername/Test");
		_log.info("Search URL: " + (PortletProps.get("PORTAL_URL") + "dossier/search-dossier/" + param));
		output = RESTfulUtils.responseGETAPI(PortletProps.get("PORTAL_URL") + "dossier/search-dossier/" + param);
		//output = RESTfulUtils.responsePOSTAPI("http://localhost:8080/api/jsonws/opencps-portlet.dossier/search-dossier", inputPOSTSearch);
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/oid/{oid}")
	@Produces("application/json;charset=utf-8")
	public Response getDossierByOid(@PathParam("oid") String oid) { 
		String output = StringPool.BLANK;
		_log.info("GET BY OID URL: " + PortletProps.get("PORTAL_URL") + "dossier/get-byoid/oid/" + oid);		
		output = RESTfulUtils.responseGETAPI(PortletProps.get("PORTAL_URL") + "dossier/get-byoid/oid/" + oid);
		return Response.status(200).entity(output).build();		
	}

	@POST
	@Path("/jsoninstance")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response dossierJSONInstance(DossierMessage message) {
		String output = StringPool.BLANK;
		_log.info("Message function: " + message.getMessagefunction());
		switch (message.getMessagefunction()) {
		case "01":
			break;
		case "02":
			break;
		case "03":
			
			break;
		case "11":
			break;
		case "20":
			break;
		case "21":
			break;
		case "22":
			break;
		case "23":
			break;
		case "24":
			break;
		default:
			break;
		}
		JSONObject inputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		inputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		ResponseBean bean = new ResponseBean();
		bean.setReceiveDate(df.format(new Date()));
		
		return Response.status(200).entity(inputJsonObject.toString()).build();
		//return bean;
	}

	@POST
	@Path("/textinstance")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response dossierTextInstance(String message) {
		String output = StringPool.BLANK;
		_log.info("Message function: " + message);
		switch (message) {
		case "01":
			break;
		case "02":
			break;
		case "03":
			
			break;
		case "11":
			break;
		case "20":
			break;
		case "21":
			break;
		case "22":
			break;
		case "23":
			break;
		case "24":
			break;
		default:
			break;
		}
		JSONObject inputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		inputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		ResponseBean bean = new ResponseBean();
		bean.setReceiveDate(df.format(new Date()));
		
		return Response.status(200).entity(inputJsonObject.toString()).build();
		//return bean;
	}
	
	private String requestChanged(String messagefunction, String messageid, String messagecontent) {
		String output = StringPool.BLANK;
		
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(messagecontent);
			JSONObject envelopObj = obj.getJSONObject("Envelope");
			JSONObject bodyObj = envelopObj.getJSONObject("Body");
			JSONObject contentObj = bodyObj.getJSONObject("Content");
			JSONObject dossierObj = contentObj.getJSONObject("RequestChanges");
			
			String requestDate = dossierObj.getString("RequestDate");
			String fromOrganization = dossierObj.getString("FromOrganization");
			String division = dossierObj.getString("Division");
			String name = dossierObj.getString("Name");
			String registeredNumber = dossierObj.getString("RegisteredNumber");
			String reason = dossierObj.getString("Reason");
			String finishDate = dossierObj.getString("FinishDate");
			String toOrganization = dossierObj.getString("ToOrganization");
			String docNumber = dossierObj.getString("DocNumber");
			String briefContent = dossierObj.getString("BriefContent");
			String docContent = dossierObj.getString("DocContent");
			String signName = dossierObj.getString("SignName");
			String signTitle = dossierObj.getString("SignTitle");
			String signPlace = dossierObj.getString("SignPlace");
			String signDate = dossierObj.getString("SignDate");
			JSONArray attachedFileObj = dossierObj.getJSONArray("AttachedFile");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		JSONObject outputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		outputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		output = outputJsonObject.toString();
		
		return output;
	}

	private String acceptance(String messagefunction, String messageid, String messagecontent) {
		String output = StringPool.BLANK;
		
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(messagecontent);
			JSONObject envelopObj = obj.getJSONObject("Envelope");
			JSONObject bodyObj = envelopObj.getJSONObject("Body");
			JSONObject contentObj = bodyObj.getJSONObject("Content");
	
			JSONObject acceptanceObj = contentObj.getJSONObject("Acceptance");
			
			String acceptDate = acceptanceObj.getString("AcceptDate");
			String organization = acceptanceObj.getString("Organization");
			String division = acceptanceObj.getString("Division");
			String name = acceptanceObj.getString("Name");
			String registeredNumber = acceptanceObj.getString("RegisteredNumber");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject outputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		outputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		output = outputJsonObject.toString();
		
		return output;
	}

	private String reject(String messagefunction, String messageid, String messagecontent) {
		String output = StringPool.BLANK;
		
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(messagecontent);
			JSONObject envelopObj = obj.getJSONObject("Envelope");
			JSONObject bodyObj = envelopObj.getJSONObject("Body");
			JSONObject contentObj = bodyObj.getJSONObject("Content");
			JSONObject rejectObj = contentObj.getJSONObject("Reject");
			
			String rejectCode = rejectObj.getString("RejectCode");
			String rejectDesc = rejectObj.getString("RejectDesc");
			String rejectDate = rejectObj.getString("RejectDate");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject outputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		outputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		output = outputJsonObject.toString();
		
		return output;
	}

	private String noticeOfAssignment(String messagefunction, String messageid, String messagecontent) {
		String output = StringPool.BLANK;
		
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(messagecontent);
			JSONObject envelopObj = obj.getJSONObject("Envelope");
			JSONObject bodyObj = envelopObj.getJSONObject("Body");
			JSONObject contentObj = bodyObj.getJSONObject("Content");
			JSONObject nOfAssignmentObj = contentObj.getJSONObject("NoticeOfAssignment");
			
			String assignDate = nOfAssignmentObj.getString("AssignDate");
			String organization = nOfAssignmentObj.getString("Organization");
			String division = nOfAssignmentObj.getString("Division");
			String name = nOfAssignmentObj.getString("Name");
			String registeredNumber = nOfAssignmentObj.getString("RegisteredNumber");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject outputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		outputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		output = outputJsonObject.toString();
		
		return output;
	}

	private String confirmCancellation(String messagefunction, String messageid, String messagecontent) {
		String output = StringPool.BLANK;
		
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(messagecontent);
			JSONObject envelopObj = obj.getJSONObject("Envelope");
			JSONObject bodyObj = envelopObj.getJSONObject("Body");
			JSONObject contentObj = bodyObj.getJSONObject("Content");
			JSONObject cancelObj = contentObj.getJSONObject("ConfirmCancellation");
			
			String registeredNumber = cancelObj.getString("RegisteredNumber");
			String finishDate = cancelObj.getString("FinishDate");
			String result = cancelObj.getString("Result");
			String fromOrganization = cancelObj.getString("FromOrganization");
			String division = cancelObj.getString("Division");
			String toOrganization = cancelObj.getString("ToOrganization");
			String docNumber = cancelObj.getString("DocNumber");
			String briefContent = cancelObj.getString("BriefContent");
			String docContent = cancelObj.getString("DocContent");
			String signName = cancelObj.getString("SignName");
			String signTitle = cancelObj.getString("SignTitle");
			String signPlace = cancelObj.getString("SignPlace");
			String signDate = cancelObj.getString("SignDate");
			JSONArray attachedFileObj = cancelObj.getJSONArray("AttachedFile");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject outputJsonObject = JSONFactoryUtil.createJSONObject();
		DateFormat df = new SimpleDateFormat(DateTimeUtil._VN_DATE_TIME_FORMAT);
		outputJsonObject.put("ReceiveDate", df.format(new Date()));
		
		output = outputJsonObject.toString();
		
		return output;
	}
	
	private static Log _log = LogFactoryUtil.getLog(DuongThuyAPI.class);
}

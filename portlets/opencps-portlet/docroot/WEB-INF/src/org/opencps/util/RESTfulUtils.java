package org.opencps.util;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.portlet.PortletProps;

public class RESTfulUtils {

	final static String virtualHOST = PortletProps.get("VIRTUAL_HOST");
	final static String accountLiferay = PortletProps.get("ACCOUNT_LIFERAY");
	final static String accountLiferayPass = PortletProps.get("ACCOUNT_LIFERAY_PASS");
	
	public static String responseGETAPI(String urlAPI){
		String result = StringPool.BLANK;
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(accountLiferay, accountLiferayPass);
		
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );

		client.register(feature);
		 
		WebTarget webTarget = client.target(urlAPI);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		_log.info(response.getStatus());
		
		result = response.readEntity(String.class);
		
		_log.info(result);
		
		return Validator.isNotNull(result)?result:"{}";
	}
	
	public static String responsePOSTAPI(String urlAPI, String input){
		String result = StringPool.BLANK;
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(accountLiferay, accountLiferayPass);
		
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		
		client.register(feature);
		
		WebTarget webTarget = client.target(urlAPI);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(input, MediaType.APPLICATION_JSON));
		
		_log.info(response.getStatus());
		
		result = response.readEntity(String.class);
		
		_log.info(result);
		
		return Validator.isNotNull(result)?result:"{}";
	}
	
	public static String responsePOSTAPI_Notification(String urlAPI, String input, String acc, String pass){
		String result = StringPool.BLANK;
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(acc, pass);
		
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		
		client.register(feature);
		
		WebTarget webTarget = client.target(urlAPI);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.post(Entity.entity(input, MediaType.APPLICATION_JSON));
		
		_log.info(response.getStatus());
		
		result = response.readEntity(String.class);
		
		_log.info(result);
		
		return Validator.isNotNull(result)?result:"{}";
	}
	
	public static void acceptReadFile() throws Exception {
		// TODO Auto-generated method stub
		Company company = CompanyLocalServiceUtil.getCompanyByVirtualHost(virtualHOST);
		
		User user = UserLocalServiceUtil.getUserByEmailAddress(company.getCompanyId(), accountLiferay);

		PrincipalThreadLocal.setName(user.getUserId());
		
		PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user, true);
		
		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}
	
	private static Log _log = LogFactoryUtil.getLog(RESTfulUtils.class);
	
}

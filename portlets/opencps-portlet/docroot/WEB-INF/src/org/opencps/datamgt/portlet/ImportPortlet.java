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

package org.opencps.datamgt.portlet;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.opencps.datamgt.search.DictItemDisplayTerms;
import org.opencps.datamgt.service.DictItemLocalServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author binhth
 */
public class ImportPortlet extends MVCPortlet {

	public void importDictitem(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {

		UploadPortletRequest uploadPortletRequest =
			    PortalUtil.getUploadPortletRequest(actionRequest);
		
		File file = uploadPortletRequest.getFile("fileImport");
		
		long dictCollectionId = ParamUtil.getLong(uploadPortletRequest, "dictCollectionId");
		
		ServiceContext serviceContext = ServiceContextFactory
				.getInstance(actionRequest);
		
		Scanner scan = new Scanner(file);
	    while(scan.hasNextLine()){
	        String line = scan.nextLine();
	        
	        String[] lineSplit = line.split("\t");
	        
	        String xml = LocalizationUtil.updateLocalization(StringPool.BLANK, "static-content", lineSplit[1], "vi_VN", "vi_VN", true, true);
	        
    		Map<Locale, String> itemNameMap = LocalizationUtil.getLocalizationMap(xml);
    		
    		_log.info("******************************"+itemNameMap);
    		
    		DictItemLocalServiceUtil.addDictItem(
				serviceContext.getUserId(), dictCollectionId,
				lineSplit[0], itemNameMap, 0,
				serviceContext);
		    }
		
	}

	private Log _log = LogFactoryUtil
			.getLog(ImportPortlet.class.getName());
}

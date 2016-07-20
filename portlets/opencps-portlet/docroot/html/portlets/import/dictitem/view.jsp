<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Locale"%>

<%
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
%>
<%@ include file="../init.jsp"%>

<%
	List<DictCollection> listCollection = new ArrayList<DictCollection>();
	listCollection = DictCollectionLocalServiceUtil.getDictCollections();
%>

<liferay-portlet:actionURL var="importDictitemURL" name="importDictitem" ></liferay-portlet:actionURL>
<aui:form action="<%=importDictitemURL.toString() %>" method="post" name="fmImport" enctype="multipart/form-data" >
	
	<aui:select name="dictCollectionId">
		<%
			for(DictCollection item: listCollection){
		%>
		<aui:option value="<%=item.getDictCollectionId() %>"> <%=HtmlUtil.escape(item.getCollectionName(locale)) %> </aui:option>
		<%
			}
		%>
	</aui:select>
	
	<aui:input name="fileImport" type="file"></aui:input>
	
	<aui:button-row>
		<aui:button name="import" type="submit"></aui:button>
	</aui:button-row>
</aui:form>

/*******************************************************************************
 * PubMatic Inc. ("PubMatic") CONFIDENTIAL Unpublished Copyright (c) 2006-2014
 * PubMatic, All Rights Reserved.
 * 
 * 
 * 
 * NOTICE: All information contained herein is, and remains the property of
 * PubMatic. The intellectual and technical concepts contained
 * 
 * herein are proprietary to PubMatic and may be covered by U.S. and Foreign
 * Patents, patents in process, and are protected by trade secret or copyright
 * law.
 * 
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained
 * 
 * from PubMatic. Access to the source code contained herein is hereby forbidden
 * to anyone except current PubMatic employees, managers or contractors who have
 * executed
 * 
 * Confidentiality and Non-disclosure agreements explicitly covering such
 * access.
 * 
 * 
 * 
 * The copyright notice above does not evidence any actual or intended
 * publication or disclosure of this source code, which includes
 * 
 * information that is confidential and/or proprietary, and is a trade secret,
 * of PubMatic. ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC
 * PERFORMANCE,
 * 
 * OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS SOURCE CODE WITHOUT THE EXPRESS
 * WRITTEN CONSENT OF PubMatic IS STRICTLY PROHIBITED, AND IN VIOLATION OF
 * APPLICABLE
 * 
 * LAWS AND INTERNATIONAL TREATIES. THE RECEIPT OR POSSESSION OF THIS SOURCE
 * CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS
 * 
 * TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR
 * SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 *******************************************************************************/

package com.pubmatic.curatedaudience.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.pubmatic.curatedaudience.util.StringUtil;
import com.pubmatic.curatedaudience.util.UrlBuilder;

@Component
public class ProductConfig implements UrlBuilder {
	
	@Value("${product.host}")
	private String hostIP;
	@Value("${product.port}")
	private String port;
	@Value("${product.resourceURL}")
	private String resourceURL;
	@Value("${product.ownerType}")
	private String ownerType;
	@Value("${product.name.format}")
	private String nameFormat;
	@Value("${product.productCategory}")
	private String productCategory;
	@Value("${product.adCodeTypes}")
	private String adCodeTypes;
	@Value("${product.platforms}")
	private String platforms;
	@Value("${product.siteName}")
	private String siteName;
	@Value("${product.productType}")
	private String productType;
	@Value("${product.vpaidComplianceVersions}")
	private String vpaidComplianceVersions;
	@Value("${product.videoMIMETypes}")
	private String videoMIMETypes;
	private Integer publisherId;
	
	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	
	public ProductConfig() {

	}
	
	public ProductConfig(Integer publisherId) {
		this.publisherId = publisherId;
	}
	
	public String getOwnerType() {
		return ownerType;
	}

	public String getNameFormat() {
		return nameFormat;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public String getAdCodeTypes() {
		return adCodeTypes;
	}

	public String getPlatforms() {
		return platforms;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getProductType() {
		return productType;
	}

	public String getVpaidComplianceVersions() {
		return vpaidComplianceVersions;
	}

	public String getVideoMIMETypes() {
		return videoMIMETypes;
	}

	public String getHostIP() {
		return hostIP;
	}

	public String getPort() {
		return port;
	}

	public String getResourceURL() {
		return resourceURL;
	}

	@Override
	public String buildURL() {
		StringBuilder url = new StringBuilder();
		url.append(getHostIP());
		url.append(":");
		url.append(getPort());
		url.append(getResourceURL());
		return url.toString();
	}
	
	public JSONObject convertToJSONRequest() throws JSONException {
		JSONObject request = new JSONObject();
		request.put("ownerType", Integer.parseInt(getOwnerType()));
		request.put("ownerId", getPublisherId().toString());
		request.put("name", getNameFormat().replaceAll("<PublisherId>", getPublisherId().toString()));
		request.put("productCategory", StringUtil.convertStringArrayToIntAndJSONArray(getProductCategory().split(",")));
		request.put("adCodeTypes", StringUtil.convertStringArrayToJSONArray(getAdCodeTypes().split(",")));
		request.put("platforms", StringUtil.convertStringArrayToIntAndJSONArray(getPlatforms().split(",")));
		request.put("siteName", getSiteName());
		request.put("productType", Integer.parseInt(getProductType()));
		request.put("vpaidComplianceVersions", StringUtil.convertStringArrayToJSONArray(getVpaidComplianceVersions().split(",")));
		request.put("videoMIMETypes", StringUtil.convertStringArrayToIntAndJSONArray(getVideoMIMETypes().split(",")));
		request.put("adTags", new JSONArray());
		request.put("adServerInventoryUnits", new JSONArray());
		request.put("description", "");
		request.put("tags", "");
		return request;
	}

}

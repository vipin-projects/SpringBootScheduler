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
import org.springframework.stereotype.Component;

import com.pubmatic.curatedaudience.util.DateUtil;
import com.pubmatic.curatedaudience.util.StringUtil;
import com.pubmatic.curatedaudience.util.UrlBuilder;

@Component
public class OfferConfig implements UrlBuilder {
	@Value("${offer.host}")
	private String hostIP;
	@Value("${offer.port}")
	private String port;
	@Value("${offer.resourceURL}")
	private String resourceURL;
	@Value("${offer.name.format}")
	private String offerNameFormat;
	@Value("${offer.publisherEmails}")
	private String publisherEmails;
	@Value("${offer.percentageAvails}")
	private String percentageAvails;
	@Value("${offer.cpm}")
	private String cpm;
	@Value("${offer.ownerId}")
	private String ownerId;
	@Value("${offer.ownerType}")
	private String ownerType;
	@Value("${offer.channels}")
	private String channels;
	@Value("${offer.offerEndDate}")
	private String offerEndDate;
	@Value("${offer.transactionEndDate}")
	private String transactionEndDate;
	@Value("${offer.logoPath}")
	private String logoPath;
	@Value("${offer.featured}")
	private String featured;
	private Integer publisherId;
	private Long productId;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getOfferNameFormat() {
		return offerNameFormat;
	}

	public String getPublisherEmails() {
		return publisherEmails;
	}

	public String getPercentageAvails() {
		return percentageAvails;
	}

	public String getCpm() {
		return cpm;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public String getChannels() {
		return channels;
	}

	public String getOfferEndDate() {
		return offerEndDate;
	}

	public String getTransactionEndDate() {
		return transactionEndDate;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public String getFeatured() {
		return featured;
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
		request.put("oneClickBuy", "true");
		request.put("exclusive", "false");
		request.put("publisherEmails", getPublisherEmails());
		request.put("name", getOfferNameFormat().replaceAll("<PublisherId>", getPublisherId().toString()));
		request.put("percentageAvails", Integer.parseInt(getPercentageAvails()));
		request.put("cpm", Double.parseDouble(getCpm()));
		request.put("ownerId", getPublisherId().toString());
		request.put("ownerType", Integer.parseInt(getOwnerType()));
		request.put("buyers", new JSONArray());
		request.put("dsps", new JSONArray());
		request.put("advertisers", new JSONArray());
		request.put("channels", StringUtil.convertStringArrayToIntAndJSONArray(getChannels().split(",")));
		request.put("description", "");
		request.put("tags", "");
		request.put("offerStartDate", DateUtil.getTodayDate(null));
		request.put("offerEndDate", getOfferEndDate());
		request.put("transactionStartDate", DateUtil.getTodayDate(null));
		request.put("transactionEndDate", getTransactionEndDate());
		System.out.println(getLogoPath().replace("<PublisherId>", getPublisherId().toString()).replaceAll("\\\\",""));
		request.put("logoPath", getLogoPath().replace("<PublisherId>", getPublisherId().toString()).replaceAll("\\\\",""));
		request.put("featured", "false");
		request.put("product", getProductId());
		
		request.put("displayLabel", JSONObject.NULL);
		request.put("description", JSONObject.NULL);
		request.put("notes", JSONObject.NULL);
		request.put("minSpend", JSONObject.NULL);
		request.put("impressions", JSONObject.NULL);
		request.put("offerType", 3);
		
		return request;
	}

}

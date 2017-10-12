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

package com.pubmatic.curatedaudience.service;

import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pubmatic.curatedaudience.config.ProductConfig;
import com.pubmatic.curatedaudience.exception.CustomException;
import com.pubmatic.curatedaudience.rest.client.RestClient;

@Component
public class ProductService extends AbstractService{
	@Value("${product.pubToken}")
	private String pubToken;
	private static final String CHILD_ELEMENT_TAG = "id";
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	RestClient restClient;
	@Autowired
	ProductConfig productConfig;
	
	public List<Long> createCuratedProduct(Integer publisherId) throws JSONException, CustomException {
		logger.info("Called createCuratedProduct method");
		logger.debug("With Inputs, publisherId {} ", publisherId);
		productConfig.setPublisherId(publisherId);
		logger.info("Initialization completed for product call");
		ResponseEntity<String> response = restClient.post(productConfig.buildURL(), pubToken, productConfig.convertToJSONRequest().toString());
		logger.debug("Response received {} ", response);
		return extractChildElementfromJSON(response.getBody(), CHILD_ELEMENT_TAG);
	}
}

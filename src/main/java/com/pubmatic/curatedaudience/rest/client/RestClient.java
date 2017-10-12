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

package com.pubmatic.curatedaudience.rest.client;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.pubmatic.curatedaudience.service.PublisherService;
import com.pubmatic.curatedaudience.util.StringUtil;

import ch.qos.logback.core.net.SocketConnector.ExceptionHandler;

@Component
public class RestClient {
	private final String PUBTOKEN = "pubToken";
	private final String HEADER_PARAMETERS = "parameters";
	private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<String> get(String resourceUrlWithParameters, String pubToken ) {
		logger.info("Called get method");
		logger.debug("With Inputs, resourceUrlWithParameters {}, pubToken {} ", resourceUrlWithParameters, pubToken);
		HttpEntity<String> headerEntity = buildHeaders(pubToken);
		restTemplate.setErrorHandler(new com.pubmatic.curatedaudience.exception.ExceptionHandler());
		return restTemplate.exchange(resourceUrlWithParameters, HttpMethod.GET, headerEntity,
					String.class);
	}
	
	public ResponseEntity<String> post(String resourceUrl, String pubToken, String jsonRequest) {
		logger.info("Called post method");
		logger.debug("With Inputs, resourceUrl {}, pubToken {}, jsonRequest {} ", resourceUrl, pubToken, jsonRequest);
		HttpEntity<String> requestPayload = buildHeadersForPostCall(pubToken, StringUtil.removeBackslashWithSpace(jsonRequest));
		restTemplate.setErrorHandler(new com.pubmatic.curatedaudience.exception.ExceptionHandler());
		return restTemplate.postForEntity( resourceUrl, requestPayload , String.class );
	}
	
	private HttpHeaders buildHttpHeaders( String pubToken ) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(PUBTOKEN, pubToken);
		return headers;
	}
	
	
	private HttpEntity<String> buildHeaders( String pubToken ) {
		logger.info("Called buildHeaders method");
		return new HttpEntity<>(HEADER_PARAMETERS, buildHttpHeaders( pubToken ));
	}
	
	private HttpEntity<String> buildHeadersForPostCall( String pubToken, String jsonRequest ) {
		logger.info("Called buildHeadersForPostCall method");
		HttpHeaders headers = buildHttpHeaders( pubToken );
		return new HttpEntity<>(jsonRequest, headers);
	}
}

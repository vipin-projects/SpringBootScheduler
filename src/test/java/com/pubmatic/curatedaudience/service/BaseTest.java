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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import org.json.JSONException;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pubmatic.curatedaudience.config.OfferConfig;
import com.pubmatic.curatedaudience.config.ProductConfig;
import com.pubmatic.curatedaudience.config.PublisherConfig;
import com.pubmatic.curatedaudience.rest.client.RestClient;

public class BaseTest {
	
	public final String PUBLISHER_SUCCESS = "{\"metaData\":{\"request\":{\"pageSize\":2000,\"pageNumber\":1,\"metrics\":null,\"dimensions\":null,\"sort\":\"companyName\",\"filters\":null,\"fromDate\":\"2010-01-02\",\"toDate\":\"2099-01-01\",\"tz\":null,\"originalQuery\":\"pageSize=2000&pageNumber=0&fromDate=2010-01-02&toDate=2010-01-02\"},\"startIndex\":1,\"totalRecords\":455,\"endIndex\":455},\"items\":[{\"publisherId\":29515,\"url\":\"http://blacksheep4u.blogspot.com/\",\"companyName\":\"\",\"pubIntegrationTypeId\":0,\"pubTypeId\":0,\"id\":29515,\"idAsString\":\"29515\",\"name\":\"\"}]}";
	public final String SUCCESS = "{\"id\":2345}";
	public final String FAILED_RESPONSE = "{\"metaData\":{\"request\":{\"errorCode\":\"IU_001_0070\"}}}";
	@Mock RestClient restClientMock;
	@Mock RestTemplate restTemplateMock;
	
	@Autowired PublisherService publisherService;
	@Autowired OfferService offerService;
	@Autowired ProductService productService;
	
	final Integer publisherId = 24011;
	final Long productId = 432l;
	
	@Before
	public void setup() {
		Whitebox.setInternalState(publisherService, "restClient", restClientMock);
		Whitebox.setInternalState(offerService, "restClient", restClientMock);
		Whitebox.setInternalState(productService, "restClient", restClientMock);
		
		Whitebox.setInternalState(restClientMock, "restTemplate", restTemplateMock);
		
		Mockito.doCallRealMethod().when(restClientMock).post(anyString(), anyString(), anyString());
		Mockito.doCallRealMethod().when(restClientMock).get(anyString(),anyString());
	}
	
	public void successResponseForPublisher() throws JSONException {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(PUBLISHER_SUCCESS, HttpStatus.OK);
		Mockito.when(restTemplateMock.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class))).thenReturn(responseEntity);
	}
	
	public void successResponse() {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		Mockito.when(restTemplateMock.postForEntity(anyString(), anyString(), any(Class.class))).thenReturn(responseEntity);
	}
	
	public void failedResponse() {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(FAILED_RESPONSE, HttpStatus.BAD_GATEWAY);
		Mockito.when(restTemplateMock.postForEntity(anyString(), anyString(), any(Class.class))).thenReturn(responseEntity);
	}
	
	public void failedResponseForPublisher() {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(FAILED_RESPONSE, HttpStatus.BAD_REQUEST);
		Mockito.when(restTemplateMock.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class))).thenReturn(responseEntity);
		Mockito.doCallRealMethod().when(restClientMock).get(anyString(),anyString());
	}
	
	@TestConfiguration
	@ComponentScan
	static class TestCofiguration {
		@Bean
		PropertyPlaceholderConfigurer propConfig() {
		    PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
		    placeholderConfigurer.setLocation(new ClassPathResource("curatedAudience.properties"));
		    return placeholderConfigurer;
		}
		
		@Bean
		public ProductConfig buildProductConfig() {
			return new ProductConfig();
		}
		
		@Bean
		public PublisherConfig buildPublisherConfig() {
			return new PublisherConfig();
		}
		
		@Bean
		public OfferConfig buildOfferConfig() {
			return new OfferConfig();
		}
	}
}

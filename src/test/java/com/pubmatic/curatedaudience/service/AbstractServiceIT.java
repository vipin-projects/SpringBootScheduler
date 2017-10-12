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

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import com.pubmatic.curatedaudience.config.OfferConfig;
import com.pubmatic.curatedaudience.config.ProductConfig;
import com.pubmatic.curatedaudience.config.PublisherConfig;
import com.pubmatic.curatedaudience.rest.client.RestClient;

public abstract class AbstractServiceIT {
	@TestConfiguration
	@ComponentScan
	static class TestCofiguration {
		
		@Bean
		public RestClient buildRestClient() {
			return new RestClient();
		}
		
		@Bean
		public RestTemplate buildRestTemplate() {
			return new RestTemplate();
		}
		
		@Bean
		public PublisherService buildPublisherService() {
			return new PublisherService();
		}
		
		@Bean
		public ProductService buildProductService() {
			return new ProductService();
		}
		
		@Bean
		public OfferService buildOfferService() {
			return new OfferService();
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
		
		@Bean
		PropertyPlaceholderConfigurer propConfig() {
		    PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
		    placeholderConfigurer.setLocation(new ClassPathResource("curatedAudience.properties"));
		    return placeholderConfigurer;
		}
		
	}
	
}

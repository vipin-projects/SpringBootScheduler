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

package com.pubmatic.curatedaudience;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pubmatic.curatedaudience.service.OfferService;
import com.pubmatic.curatedaudience.service.ProductService;
import com.pubmatic.curatedaudience.service.PublisherService;

@Component
public class JobExecutor {
	private static final Logger logger = LoggerFactory.getLogger(JobExecutor.class);
	@Autowired
	ProductService productService;
	@Autowired
	PublisherService publisherService;
	@Autowired
	OfferService offerService;

	private static AtomicInteger COUNTER = new AtomicInteger(0);

	private final ExecutorService executor;

	@Autowired
	public JobExecutor(@Value("${scheduled.thread.pool.size:10}") int threadPoolSize) {
		executor = Executors.newFixedThreadPool(threadPoolSize, (Runnable r) -> {
			Thread t = new Thread(r);
			t.setName("scheduled-executor-" + COUNTER.incrementAndGet());
			return t;
		});
	}

	public void execute() {
		logger.info("Called execute method");
		try {
			List<Integer> publisherId = executePublisherService();
			publisherId.stream().forEach(pubId -> {
				try {
					executor.submit(() -> {
						executeOfferService(pubId, executeProductService(pubId).get(0));
						return null;
					});
				} catch (Exception e) {
					logger.error("An error occured while processing request, exception {}, {} ", e.getMessage(), e);
				}
			});
		} catch (Exception e) {
			logger.error("An error occured while processing request, exception {}, {} ", e.getMessage(), e);
		}
		logger.info("End execute method");
	}

	private List<Integer> executePublisherService() throws Exception {
		logger.info("Called executePublisherService method");
		return publisherService.getPublisherIdList();
	}

	private List<Long> executeProductService(Integer publisherId) throws Exception {
		logger.info("Called executeProductService method");
		logger.info("With Inputs, publisherId {} ", publisherId);
		return productService.createCuratedProduct(publisherId);
	}

	private Boolean executeOfferService(Integer publisherId, Long productId) throws Exception {
		logger.info("Called executeOfferService method");
		logger.info("With Inputs, publisherId {}, productId {} ", publisherId, productId);
		return offerService.createCuratedOffer(publisherId, productId);
	}

}

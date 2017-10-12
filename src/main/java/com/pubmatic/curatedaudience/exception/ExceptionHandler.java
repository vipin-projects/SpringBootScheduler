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

package com.pubmatic.curatedaudience.exception;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.pubmatic.curatedaudience.rest.client.RestClient;

public class ExceptionHandler extends DefaultResponseErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		logger.info("Called handleError method");
		logger.error("status code {}, status text {}, raw status code {}", response.getStatusCode(),
				response.getStatusText(), response.getRawStatusCode());
		BufferedReader in = new BufferedReader(new InputStreamReader(response.getBody()));
		String line = null;
		StringBuilder responseData = new StringBuilder();
		while((line = in.readLine()) != null) {
		    responseData.append(line);
		}
		logger.info("response error message {} ", responseData.toString());
		logger.info("End handleError method");
		throw new CustomException(responseData.toString());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		logger.info("Called hasError method");
		logger.error("status code {}, status text {}, raw status code {}", response.getStatusCode(),
				response.getStatusText(), response.getRawStatusCode());
		if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR)
				|| (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
			logger.info("End hasError method");
			return true;
		}
		logger.info("End hasError method");
		return false;
	}
}

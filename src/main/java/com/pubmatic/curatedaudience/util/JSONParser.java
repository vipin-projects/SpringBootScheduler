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

package com.pubmatic.curatedaudience.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pubmatic.curatedaudience.service.AbstractService;

public class JSONParser {
	private static final Logger logger = LoggerFactory.getLogger(JSONParser.class);
	
	public static List<Integer> parseJSON( String json, String rootElementTag, String actualElement ) throws JSONException {
		logger.info("Called parseJSON method");
		logger.debug("With Inputs, json {}, rootElementTag {}, actualElement {} ", json, rootElementTag, actualElement);
		JSONObject jsonObj = new JSONObject(json);
		JSONArray itemArr = jsonObj.getJSONArray(rootElementTag);
		List<Integer> publisherIds = new ArrayList<>();
		for(int i=0; i < itemArr.length(); i++) {
			JSONObject jsonObj1 = (JSONObject)itemArr.get(i);
			Integer actualElementValue = (Integer)jsonObj1.get(actualElement);
			publisherIds.add(actualElementValue);
		}
		logger.info("End parseJSON method");
		logger.debug("With Output, publisherIds {} ", publisherIds);
		return publisherIds;
	}
	
	public static List<Long> parseJSON( String json, String actualElement ) throws JSONException {
		logger.info("Called parseJSON method");
		logger.debug("With Inputs, json {}, actualElement {} ", json, actualElement);
		JSONObject jsonObj = new JSONObject(json);
		Number actualElementValue = (Number)jsonObj.get(actualElement);
		logger.info("End parseJSON method");
		logger.debug("With Output, actualElementValue {} ", actualElementValue);
		List<Long> list = new ArrayList<>();
		list.add(actualElementValue.longValue());
		return list;
	}
}

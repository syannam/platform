/*
 * Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.connector.twilio.phonenumbers;

import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.SynapseLog;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.util.ConnectorUtils;
import org.wso2.carbon.connector.twilio.util.TwilioUtil;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestResponse;

/*
 * Class mediator for getting the list of incoming phone numbers.
 * For more information, see
 * http://www.twilio.com/docs/api/rest/incoming-phone-numbers
 */
public class GetOutgoingPhoneNumberList extends AbstractConnector {

	@Override
	public void connect(MessageContext messageContext) throws ConnectException {

		SynapseLog log = getLog(messageContext);
		log.auditLog("Start: get outgoing phone list");
		// Parameters for the filters
		Map<String, String> params = getParameter(messageContext);

		try {
			TwilioRestClient twilioRestClient = TwilioUtil.getTwilioRestClient(messageContext);
			TwilioRestResponse response =
			                              twilioRestClient.request(TwilioUtil.API_URL +
			                                                               "/" +
			                                                               TwilioUtil.API_VERSION +
			                                                               "/" +
			                                                               TwilioUtil.API_ACCOUNTS +
			                                                               "/" +
			                                                               twilioRestClient.getAccountSid() +
			                                                               "/" +
			                                                               TwilioUtil.API_OUTGOING_PHONENUMBER,
			                                                       "GET", params);
			OMElement omResponse = TwilioUtil.parseResponse(response);
			TwilioUtil.preparePayload(messageContext, omResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new SynapseException(e);
		}
		log.auditLog("End: get outgoing phone list");
	}

	private Map<String, String> getParameter(MessageContext messageContext) {
		String friendlyName =
		                      (String) ConnectorUtils.lookupTemplateParamater(messageContext,
		                                                                  TwilioUtil.PARAM_FRIENDLY_NAME);
		String phoneNumber =
		                     (String) ConnectorUtils.lookupTemplateParamater(messageContext,
		                                                                 TwilioUtil.PARAM_PHONENUMBER);

		Map<String, String> params = new HashMap<String, String>();
		if (friendlyName != null) {
			params.put(TwilioUtil.TWILIO_FRIENDLY_NAME, friendlyName);
		}
		if (phoneNumber != null) {
			params.put(TwilioUtil.TWILIO_PHONENUMBER, phoneNumber);
		}

		return params;
	}
}

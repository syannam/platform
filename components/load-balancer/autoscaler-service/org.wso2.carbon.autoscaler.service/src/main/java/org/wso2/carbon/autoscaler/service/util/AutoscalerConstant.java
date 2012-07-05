/*
*  Copyright (c) 2005-2011, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.autoscaler.service.util;

public final class AutoscalerConstant {

    /**
     * elastic-scaler-config XML file's elements
     */
    public static final String IAAS_PROVIDER_ELEMENT = "iaasProvider";
    public static final String IAAS_PROVIDER_TYPE_ATTR = "type";
    public static final String IAAS_PROVIDER_NAME_ATTR = "name";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String PROPERTY_NAME_ATTR= "name";
    public static final String PROPERTY_VALUE_ATTR = "value";
    public static final String IMAGE_ID_ELEMENT = "imageId";
    public static final String SCALE_DOWN_ORDER_ELEMENT = "scaleDownOrder";
    public static final String SCALE_UP_ORDER_ELEMENT = "scaleUpOrder";
    public static final String PROVIDER_ELEMENT = "provider";
    public static final String IDENTITY_ELEMENT = "identity";
    public static final String CREDENTIAL_ELEMENT = "credential";
    public static final String DEFAULT_SERVICE_ELEMENT = "default";
    public static final String SERVICE_ELEMENT = "service";
    public static final String SERVICE_DOMAIN_ATTR = "domain";
    
    /**
     * Secret Manager related aliases.
     */
    public static final String EC2_IDENTITY_ALIAS = "elastic.scaler.ec2.identity";
    public static final String EC2_CREDENTIAL_ALIAS = "elastic.scaler.ec2.credential";
    public static final String OPENSTACK_IDENTITY_ALIAS = "elastic.scaler.openstack.identity";
    public static final String OPENSTACK_CREDENTIAL_ALIAS = "elastic.scaler.openstack.credential";
}

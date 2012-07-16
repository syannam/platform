/**
 * Copyright (c) 2009, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.wso2.carbon.bam.toolbox.deployer.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.bam.toolbox.deployer.ServiceHolder;
import org.wso2.carbon.base.api.ServerConfigurationService;
import org.wso2.carbon.dashboard.DashboardDSService;
import org.wso2.carbon.dashboard.mgt.gadgetrepo.GadgetRepoService;
import org.wso2.carbon.databridge.core.DataBridgeReceiverService;
import org.wso2.carbon.ndatasource.core.DataSourceService;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.ConfigurationContextService;

/**
 * @scr.component name="org.wso2.carbon.bam.toolbox.deployer" immediate="true"
 * @scr.reference name="server.configuration"
 * interface="org.wso2.carbon.base.api.ServerConfigurationService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setServerConfiguration"
 * unbind="unsetServerConfiguration"
 * @scr.reference name="registry.service"
 * interface="org.wso2.carbon.registry.core.service.RegistryService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setRegistryService"
 * unbind="unsetRegistryService"
 * @scr.reference name="config.context.service"
 * interface="org.wso2.carbon.utils.ConfigurationContextService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setConfigurationContextService"
 * unbind="unsetConfigurationContextService"
 * @scr.reference name="user.realm.delegating"
 * interface="org.wso2.carbon.user.core.UserRealm"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setUserRealm"
 * unbind="unsetUserRealm"
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setRealmService"
 * unbind="unsetRealmService"
 * @scr.reference name="org.wso2.carbon.dashboard.DashboardDSService"
 * interface="org.wso2.carbon.dashboard.DashboardDSService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setDashboardService"
 * unbind="unsetDashboardService"
 * @scr.reference name="org.wso2.carbon.dashboard.mgt.gadgetrepo.GadgetRepoService"
 * interface="org.wso2.carbon.dashboard.mgt.gadgetrepo.GadgetRepoService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setGadgetRepoService"
 * unbind="unsetGadgetRepoService"
 * @scr.reference name="datasources.service"
 * interface="org.wso2.carbon.ndatasource.core.DataSourceService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setDataSourceService"
 * unbind="unsetDataSourceService"
 * @scr.reference name="databridge.reciever"
 * interface="org.wso2.carbon.databridge.core.DataBridgeReceiverService"
 * cardinality="0..1"
 * policy="dynamic"
 * bind="setDataBridgeReceiverService"
 * unbind="unsetDataBridgeReceiverService"
 */

public class BAMToolBoxDeployerComponent {
    private static final Log log = LogFactory.getLog(BAMToolBoxDeployerComponent.class);

    protected void activate(ComponentContext context) {
        log.info("Successfully Started BAM Toolbox Deployer");
    }

    protected void setConfigurationContextService(ConfigurationContextService contextService) {
        ServiceHolder.setConfigurationContextService(contextService);
    }

    protected void unsetConfigurationContextService(ConfigurationContextService contextService) {
        ServiceHolder.setConfigurationContextService(null);
    }

    protected void setRegistryService(RegistryService registryService) {
        ServiceHolder.setRegistryService(registryService);
    }

    protected void unsetRegistryService(RegistryService registryService) {
        ServiceHolder.setRegistryService(null);
    }

    protected void setUserRealm(UserRealm userRealm) {
        ServiceHolder.setUserRealm(userRealm);
    }

    protected void unsetUserRealm(UserRealm userRealm) {
        ServiceHolder.setUserRealm(userRealm);

    }

    protected void setRealmService(RealmService realmService) {
        ServiceHolder.setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {
        ServiceHolder.setRealmService(null);
    }

    protected void setDashboardService(DashboardDSService dashboardService) {
        ServiceHolder.setDashboardService(dashboardService);
    }

    protected void unsetDashboardService(DashboardDSService dashboardService) {
        ServiceHolder.setDashboardService(null);
    }

    protected void setServerConfiguration(ServerConfigurationService serverConfiguration) {
        ServiceHolder.setServerConfiguration(serverConfiguration);
    }

    protected void unsetServerConfiguration(ServerConfigurationService serverConfiguration) {
        ServiceHolder.setConfigurationContextService(null);
    }


    protected void setGadgetRepoService(GadgetRepoService gadgetRepoService) {
        ServiceHolder.setGadgetRepoService(gadgetRepoService);
    }

    protected void unsetGadgetRepoService(GadgetRepoService gadgetRepoService) {
        ServiceHolder.setGadgetRepoService(null);
    }

    protected void setDataSourceService(DataSourceService dataSourceService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the Carbon Data Sources Service");
        }
        ServiceHolder.setDataSourceService(dataSourceService);
    }

    protected void unsetDataSourceService(
            DataSourceService dataSourceService) {
        if (log.isDebugEnabled()) {
            log.debug("Unsetting the Carbon Data Sources Service");
        }
        ServiceHolder.setDataSourceService(null);
    }

   protected void setDataBridgeReceiverService(DataBridgeReceiverService dataBridgeReceiverService){
        ServiceHolder.setDataBridgeReceiverService(dataBridgeReceiverService);
   }

   protected void unsetDataBridgeReceiverService(DataBridgeReceiverService dataBridgeReceiverService){
        ServiceHolder.setDataBridgeReceiverService(null);
   }

}
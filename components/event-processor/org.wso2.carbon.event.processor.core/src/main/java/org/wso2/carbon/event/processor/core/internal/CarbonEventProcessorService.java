/**
 * Copyright (c) 2005 - 2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.event.processor.core.internal;

import org.apache.axis2.engine.AxisConfiguration;
import org.wso2.carbon.event.processor.core.EventProcessorService;
import org.wso2.carbon.event.processor.core.QueryPlanConfiguration;

import java.util.List;

public class CarbonEventProcessorService implements EventProcessorService {


    @Override
    public void addQueryPlanConfiguration(QueryPlanConfiguration queryPlanConfiguration, AxisConfiguration axisConfiguration) {
        // todos
    }

    @Override
    public void removeQueryPlanConfiguration(QueryPlanConfiguration queryPlanConfiguration, AxisConfiguration axisConfiguration) {
    }

    @Override
    public QueryPlanConfiguration getQueryPlanConfiguration(String name, AxisConfiguration axisConfiguration) {
        return null;
    }

    @Override
    public List<QueryPlanConfiguration> getAllQueryPlanConfigurations(AxisConfiguration axisConfiguration) {
        return null;
    }
}
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.poc.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.poc.model.Agent;

/**
 * Define REST services using the Camel REST DSL
 */
public class POCRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // configure we want to use servlet as the component for the rest DSL
        // and we enable json binding mode
    	
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
            // and output using pretty print
            .dataFormatProperty("prettyPrint", "true")
            .contextPath("camelroute/rest").port(8080);

        // this user REST service is json only
        rest("/agent").description("agent rest service")
            .consumes("application/json").produces("application/json")

            .get("/{id}").description("Find agent by id").outType(Agent.class)
                .to("bean:agentClientBean?method=getAgentDetails(${header.id})");
        /*    
            .put().description("Updates or create a Agent").type(Agent.class)
                .to("bean:agentClientBean?method=updateAgent")

            .get("/findAll").description("Find all Agents").outTypeList(Agent.class)
                .to("bean:agentClientBean?method=listAgents");*/
    }

}
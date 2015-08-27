
package com.poc.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.poc.model.Agent;


public class POCRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .contextPath("camelroute/rest").port(8080);


        rest("/agent").description("Agent Rest Service")
            .consumes("application/json").produces("application/json")

            .get("/{id}").description("Find agent by id").outType(Agent.class)
                .to("bean:agentClientBean?method=getAgentDetails(${header.id})")
            
            .put().description("Updates or create a Agent").type(Agent.class)
                .to("bean:agentClientBean?method=updateAgent")

            .get("/findAll").description("Find all Agents").outTypeList(Agent.class)
                .to("bean:agentClientBean?method=listAgents");
    }

}
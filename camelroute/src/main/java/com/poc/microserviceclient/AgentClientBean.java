package com.poc.microserviceclient;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.poc.model.Agent;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



public class AgentClientBean {

	Client client = Client.create();
	public static String AGENT_URL="http://localhost:9080/agent";
	
	
	public Agent getAgentDetails(String agentId) throws JsonParseException, JsonMappingException, IOException{
		WebResource webResource = client.resource(AGENT_URL + "?agentId="+agentId);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if(response.getStatus()!=200){
			throw new RuntimeException("HTTP Error: "+ response.getStatus());
		}
		
		String result = response.getEntity(String.class);
		System.out.println("Response from the Server: ");
		System.out.println(result);
		
		ObjectMapper mapper = new ObjectMapper();
		Agent agent = mapper.readValue(result, Agent.class);
		return agent;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		AgentClientBean agentClientBean = new AgentClientBean();
		agentClientBean.getAgentDetails("3");
	}
}

package com.poc.microserviceclient;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.poc.model.Agent;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



public class AgentClientBean {

	
      
	public final static Map<String, Agent> agents = new TreeMap<String, Agent>();
	 
	Client client = Client.create();
	public static String AGENT_URL="http://localhost:9080/agent";
	
	
	AgentClientBean(){
		agents.put("123", new Agent("1", "John Doe"));
		agents.put("456", new Agent("2", "Donald Duck"));
		agents.put("789", new Agent("3", "Slow Turtle"));
	}
	
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
	
	
	  public void updateAgent(Agent agent) {
		  System.out.println("**********updateAgent: Received Agent Id:"   + agent.getAgentId());
		  System.out.println("**********updateAgent: Received Agent Name:" + agent.getAgentName());
	   }
	
	  public Collection<Agent> listAgents() {
		  
	        return agents.values();
	    }

	  
	  
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		AgentClientBean agentClientBean = new AgentClientBean();
		//agentClientBean.getAgentDetails("3");
		//System.out.println(agentClientBean.listAgents().toString());
	}
}

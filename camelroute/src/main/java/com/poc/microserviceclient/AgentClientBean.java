package com.poc.microserviceclient;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
	public static String AGENT_URL = "http://localhost:9080/agent";

	AgentClientBean() {
		//For Test
		agents.put("123", new Agent("1", "John Doe"));
		agents.put("456", new Agent("2", "Donald Duck"));
		agents.put("789", new Agent("3", "Slow Turtle"));
	}

	public Agent getAgentDetails(String agentId) throws JsonParseException,
			JsonMappingException, IOException {
		WebResource webResource = client.resource(AGENT_URL + "?agentId="
				+ agentId);
		ClientResponse response = webResource.accept("application/json").get(
				ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		System.out.println("Response from the Server: ");
		System.out.println(result);

		
		//The Service returns JSON , here I am converting it to JAVA 
		//When returning at the end , the camel-restlet converts JAVA to JSON
		//Either service should JAVA or I should avoid camel-restlet converting to JSON ???
		
		ObjectMapper mapper = new ObjectMapper();
		Agent agent = mapper.readValue(result, Agent.class);
		return agent;
	}

	public void updateAgent(Agent agent) {
		
		//TODO Should use loggers not sysouts...
		
		System.out.println("**********updateAgent: Received Agent Id:"
				+ agent.getAgentId());
		System.out.println("**********updateAgent: Received Agent Name:"
				+ agent.getAgentName());
		agents.put(agent.getAgentId(), agent);
	}

	public Collection<Agent> listAgents() {
		return agents.values();
	}

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		AgentClientBean agentClientBean = new AgentClientBean();
		agentClientBean.getAgentDetails("3");
		
		displayAgents(agentClientBean.listAgents());
		
	}

	private static void displayAgents(Collection<Agent> agentList) {
		
		Iterator<Agent> agentItr=agentList.iterator();
		
		while(agentItr.hasNext()){
			System.out.println("***BEGIN: Agent List***");			
			Agent agent=(Agent) agentItr.next();

			System.out.println("Agent ID   is: " + agent.getAgentId());
			System.out.println();
			System.out.println("Agent Name is: " + agent.getAgentName());
		}
		System.out.println("***END: Agent List***");

		
	}
}

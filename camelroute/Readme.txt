To Build the project ( Builds a WAR ) 

	mvn package
	
	
This project tries to download the swagger-ui jars used for documenting of REST Service.

If any issues in connecting to github and downloading it during build , download manually from
https://github.com/swagger-api/swagger-ui/tree/v2.0.24    and add to target folder and then build the project.

Also, ensure you comment out the Step 1 in pom.xml to download the swagger-ui from github repo.

I used one of the sample which was integrated with swagger-ui. It can be removed from pom.xml.



 
	
To Run the service (Camel Route is exposed as a Service)
	
	1.Copy the built WAR to tomcat webapps folder and start the server.
	
	GET 	http://localhost:8080/camelroute/rest/agent/1
	
	GET 	http://localhost:8080/camelroute/rest/agent/findAll
	
	Use Rest Client for POST Request, Input/Output both in JSON
	POST	http://localhost:8080/camelroute/rest/agent
	
	Pass below sample input in JSON format for add/update agent using REST Client
	{
		"agentId" : "3",
  		"agentName" : "Unknown Agent"
	}
	
	2.mvn jetty:run
	
	GET 	http://localhost:8080/rest/agent/1
	GET 	http://localhost:8080/rest/agent/findAll
	
	Use Rest Client for POST Request,
	POST	http://localhost:8080/rest/agent
	
	
	
Camel route consumes the REST Service running on another port, Refer , https://github.com/bsridhar77/services repo for that.


http://localhost:9080/agent/1
	

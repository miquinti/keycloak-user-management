# keycloak-user-manament
Main application to manage user credential using keycloack and its Spring Boot Adapter: User Login, User registration and User profile. This web application conects to a keycloak server and display a Login interface, new user registration and user profile or user account access. Additionaly, the logued user can logout at any time (logout functionality).

As a result of this project, developers may manage user access easily. Additionally, to adapt the interfaces desing to company/project requirements see wiki of this project (Customization by using Keycloak Thems).

# Functionalities
- User Login
- User registration: Set up a new user
- User profile: Users are allowed to change their details (personal details, password, etc.)
- Authorizations: Using application.properties file, users will be allowed to access (or not) to any endpoint

# Preconditions
## Keycloak Server configuration
- Setting Up a Keycloak Server: See full details here: - http://www.keycloak.org/docs/latest/getting_started/index.html
	- Create admin user
		- /opt/wildly/bin/add-user-keycloak -u admin -p password
		- `service wildfly restart`
	- Login into keycloak as admin
	    - <keycloakdomain>/auth/admin/
	- Create a realm for the project: e.g "TestingRealm", see details:
	    - https://www.keycloak.org/docs/latest/getting_started/index.html#creating-a-realm-and-user
		- Go to login tab and set "User registration" to on. A link for registration will be showed
	- Create a client for the project inside the realm: e.g. "testing-cli", and set up valid Redirect URIs (those allowed to access: <localhost:8080/*> for testing purpose), see details:
	    - https://www.keycloak.org/docs/3.0/authorization_services/topics/resource-server/create-client.html 
	- Create a role (e.g. "testing-role") 
		- See details: https://www.keycloak.org/docs/3.2/server_admin/topics/roles.html
		- Go to "Default Roles" tab and Set up this role ("testing-role") as default (to avoid problems)

# Installation and Execution
## Installation
- Clone (or upload) repository from here: https://github.com/miquinti/keycloak-user-management.git

## Execution
- Set up properties file
    - create your own `application.properties` file and save it into your project root path with following details:

    ```
	
    keycloak.auth-server-url=https://<keycloak-domain>/auth/

    keycloak.realm=TestingRealm
    keycloak.resource=testing-cli
	
    keycloak.public-client=true

    #to set up the server on port  (optional)
    server.port=8080

    #Permitions example, use your own permitions details
    #Administrator is allowed to acess to administration path
    keycloak.security-constraints[1].authRoles[0]=testing-role-admin
    keycloak.security-constraints[1].securityCollections[0].patterns[0]=/administration

	#Type your own permitions authorization requeriments
   
	```

- From Eclipse IDE
    - Right botton on project -> run as -> Spring boot
	- Go to your browser and type http://localhost:8080
	
- From command line
    - `run mvn package` from the command line into your project root to generate jar file
    - in the target directory, you should see projectName-0.0.1-SNAPSHOT
	- `java -jar projetName-0.0.1-SNAPSHOT.java`
	- Go to your browser and type http://localhost:8080
	

# Technology

- Maven for Java dependency management
- Spring Boot 
- keycloak server


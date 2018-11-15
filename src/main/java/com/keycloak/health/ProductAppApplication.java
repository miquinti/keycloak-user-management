/*
 * 
 * This is free software: you can redistribute it and/or modify it under the 
 * terms of the Apache License, Version 2.0 (the License);
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * The software is provided "AS IS", without any warranty of any kind, express or implied,
 * including but not limited to the warranties of merchantability, fitness for a particular
 * purpose and noninfringement, in no event shall the authors or copyright holders be 
 * liable for any claim, damages or other liability, whether in action of contract, tort or
 * otherwise, arising from, out of or in connection with the software or the use or other
 * dealings in the software.
 * 
 * See README file for the full disclaimer information and LICENSE file for full license 
 * information in the project root.
 * 
 * @author  Miriam Quintero
 *         miquinti@gmail.com
 * 
 * @date 02/10/2018
 * Main App entrance
 */
package com.keycloak.health;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.util.Set;

@SpringBootApplication
public class ProductAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAppApplication.class, args);
	}
}

@Controller
class ProductController {

    //Constants
    @Value("${keycloak.auth-server-url}")
    private String keucloakURL;

    
    @Value("${keycloak.realm}")
    private String REALM;
    
    @GetMapping(path = "/")
    public String initialPage(HttpServletRequest request,
    		Model model) throws ServletException {
    	Principal principal = request.getUserPrincipal();
    	AccessToken token = ((KeycloakPrincipal) principal).getKeycloakSecurityContext().
    			getToken();
  
    	Set<String> roles = token.getRealmAccess().getRoles();

    	model.addAttribute("userId", principal.getName());
    	model.addAttribute("userName", principal.getName());
    	model.addAttribute("userFullName", token.getGivenName() + " " + 
    			token.getFamilyName());
    	model.addAttribute("roles", roles);

    	return "home";

    }

    /* To logout from keycloack*/
    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
    	request.logout();
    	return "redirect:/";
    }

    /* To logout from keycloack*/
    @GetMapping(path = "/profile")
    public String profile(HttpServletRequest request) throws ServletException {
    	return "redirect:"+ keucloakURL +"realms/" + REALM + "/account";
    }

    /* To Administration user into keycloak, only for administrator user */
    @GetMapping(path = "/administration")
    public String administration(HttpServletRequest request) throws ServletException {
    	try{
    		return "redirect:"+ keucloakURL + "admin/" + REALM + "/console";	   
    	}
    	catch (Exception ex){
    		return ex.getMessage();
    	}
    }
}

package com.onionsquare.web.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;



public class CustomSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	public String targetUrlParameter;
	
	    private boolean forwardToDestination = false;
	  
	    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	    
	    public CustomSimpleUrlAuthenticationFailureHandler() {
	    }
	    
	    public CustomSimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
	        super.setDefaultFailureUrl(defaultFailureUrl);
	    }
	    
	    public CustomSimpleUrlAuthenticationFailureHandler(String defaultUrl, String targetUrl){
	    	this.targetUrlParameter=targetUrl;
	    }
	
 @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

	 String defaultFailureUrl = request.getParameter(targetUrlParameter);
        if (defaultFailureUrl == null) {
            logger.debug("No failure URL set, sending 401 Unauthorized error");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
        } else {
            saveException(request, exception);

            if (forwardToDestination) {
                logger.debug("Forwarding to " + defaultFailureUrl);

                request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
            } else {
                logger.debug("Redirecting to " + defaultFailureUrl);
                redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
            }
        }
    }

public String getTargetUrlParameter() {
	return targetUrlParameter;
}

public void setTargetUrlParameter(String targetUrlParameter) {
	this.targetUrlParameter = targetUrlParameter;
}
	
	
	
}

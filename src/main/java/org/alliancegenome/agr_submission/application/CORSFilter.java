package org.alliancegenome.agr_submission.application;

import java.io.IOException;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		//responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		//responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		//responseContext.getHeaders().add("Access-Control-Max-Age", "-1");
		//responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}
}

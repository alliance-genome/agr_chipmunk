package org.alliancegenome.agr_submission.entities.log;

import java.util.*;

import org.alliancegenome.agr_submission.entities.LoggedInUser;

import lombok.*;

@Getter @Setter @ToString
public class LogApiDTO {
	
	public String requestMethod;
	public String headersString;
	public String address;
	public List<String> userAgent = new ArrayList<String>();
	public HashMap<String, List<String>> queryParameters;
	public HashMap<String, List<String>> pathParameters;
	public String requestUri;
	public LoggedInUser user;

	
}

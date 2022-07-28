package org.alliancegenome.agr_submission.entities.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.alliancegenome.agr_submission.entities.LoggedInUser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

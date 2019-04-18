package org.alliancegenome.agr_submission;

import javax.enterprise.context.RequestScoped;

import org.alliancegenome.agr_submission.config.ConfigHelper;

@RequestScoped
public class BaseController {

	/* if token is not defined in properties file, then do not require one.	 Otherwise, must
	 * be an exact match (case sensitive).
	 */
	protected boolean authenticate(String api_access_token) {
		String accessToken = ConfigHelper.getApiAccessToken();
		if (accessToken != null) {
			return accessToken.equals(api_access_token);
		}
		return true;
	}

}

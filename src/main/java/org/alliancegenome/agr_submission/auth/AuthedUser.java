package org.alliancegenome.agr_submission.auth;

import org.alliancegenome.agr_submission.entities.LoggedInUser;

import lombok.*;

@Getter @Setter
public class AuthedUser {
	private LoggedInUser user = null;
}

package org.alliancegenome.agr_submission.auth;

import org.alliancegenome.agr_submission.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthedUser {
	private User user = null;
}

package org.alliancegenome.agr_submission.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
public class Credentials implements Serializable {

	private String username;
	private String password;

}

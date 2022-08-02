package org.alliancegenome.agr_submission.responces;

import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

@Getter
@Setter
public class APIResponce {
	@JsonView({View.API.class})
	private String status;
	@JsonView({View.API.class})
	private String message;
}

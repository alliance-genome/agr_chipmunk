package org.alliancegenome.agr_submission.responces;

import java.util.HashMap;

import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

@Getter
@Setter
public class SubmissionResponce extends APIResponce {

	@JsonView({View.API.class})
	private HashMap<String, String> fileStatus = new HashMap<>();

}

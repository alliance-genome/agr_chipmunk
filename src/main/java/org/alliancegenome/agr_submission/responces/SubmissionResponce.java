package org.alliancegenome.agr_submission.responces;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionResponce extends APIResponce {

	private HashMap<String, String> fileStatus = new HashMap<>();

}

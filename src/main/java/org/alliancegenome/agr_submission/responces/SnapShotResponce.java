package org.alliancegenome.agr_submission.responces;

import org.alliancegenome.agr_submission.entities.SnapShot;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

@Getter
@Setter
public class SnapShotResponce extends APIResponce {
	@JsonView({View.SnapShotView.class})
	private SnapShot snapShot;
}

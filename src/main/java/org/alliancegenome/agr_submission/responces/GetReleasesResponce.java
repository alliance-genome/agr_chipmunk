package org.alliancegenome.agr_submission.responces;

import java.util.List;

import org.alliancegenome.agr_submission.entities.ReleaseVersion;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReleasesResponce extends APIResponce {
	@JsonView({View.ReleaseVersionView.class})
	private List<ReleaseVersion> releases;
}

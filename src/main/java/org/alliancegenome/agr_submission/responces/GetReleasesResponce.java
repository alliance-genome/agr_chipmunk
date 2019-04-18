package org.alliancegenome.agr_submission.responces;

import java.util.List;

import org.alliancegenome.agr_submission.entities.ReleaseVersion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReleasesResponce extends APIResponce {
	private List<ReleaseVersion> releases;
}

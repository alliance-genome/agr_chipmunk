package org.alliancegenome.agr_submission.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Entity @ApiModel
@Getter @Setter
public class ReleaseVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.ReleaseVersionView.class, View.SchemaVersionView.class})
	private Long id;
	@JsonView({View.ReleaseVersionView.class, View.SchemaVersionView.class})
	private String releaseVersion;
	
	@OneToMany(mappedBy="releaseVersion")
	private List<SnapShot> snapShots;
	
	@ManyToMany
	private List<SchemaVersion> schemaVersions;
	
}

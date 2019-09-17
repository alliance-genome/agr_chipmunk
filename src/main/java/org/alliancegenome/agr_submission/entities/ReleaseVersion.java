package org.alliancegenome.agr_submission.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ReleaseVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.ReleaseVersionView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private Long id;
	@JsonView({View.ReleaseVersionView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private String releaseVersion;
	
	@OneToMany(mappedBy="releaseVersion")
	private List<SnapShot> snapShots;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<SchemaVersion> schemaVersions = new ArrayList<>();
	
	@OneToMany(mappedBy="releaseVersion", fetch=FetchType.EAGER)
	@JsonView({View.ReleaseVersionView.class})
	private Set<DataFile> dataFiles;
	
}

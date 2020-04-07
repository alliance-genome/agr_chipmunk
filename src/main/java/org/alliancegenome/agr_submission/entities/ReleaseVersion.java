package org.alliancegenome.agr_submission.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString(of= {"id", "releaseVersion", "releaseDate", "defaultSchemaVersion"})
@Schema(name="ReleaseVersion", description="ReleaseVersion model")
public class ReleaseVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	@Column(unique = true)
	@JsonView({View.API.class})
	private String releaseVersion;
	@JsonView({View.API.class})
	private Date releaseDate;
	
	@OneToMany(mappedBy="releaseVersion")
	@Schema(implementation = SnapShot.class)
	private List<SnapShot> snapShots;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JsonView({View.ReleaseVersionView.class})
	@Schema(implementation = SchemaVersion.class)
	private SchemaVersion defaultSchemaVersion;

	// fetch=FetchType.EAGER is needed for take snapshot
	// fetch=FetchType.EAGER is needed for /api/releaseversion/all
	// fetch=FetchType.EAGER is NOT needed for /api/releaseversion/{id}
	@ManyToMany(mappedBy = "releaseVersions")
	//@JsonView({View.ReleaseVersionView.class})
	@Schema(implementation = DataFile.class)
	private Set<DataFile> dataFiles = new HashSet<>();
	
}

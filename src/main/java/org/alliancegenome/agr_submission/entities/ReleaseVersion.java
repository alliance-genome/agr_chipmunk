package org.alliancegenome.agr_submission.entities;

import java.util.*;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString(of= {"id", "releaseVersion", "releaseDate", "defaultSchemaVersion"})
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
	private List<SnapShot> snapShots;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JsonView({View.ReleaseVersionView.class})
	private SchemaVersion defaultSchemaVersion;

	// fetch=FetchType.EAGER is needed for take snapshot
	// fetch=FetchType.EAGER is needed for /api/releaseversion/all
	// fetch=FetchType.EAGER is NOT needed for /api/releaseversion/{id}
	@ManyToMany(mappedBy = "releaseVersions")
	//@JsonView({View.ReleaseVersionView.class})
	private Set<DataFile> dataFiles = new HashSet<>();
	
}

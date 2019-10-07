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

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

	@ManyToMany(mappedBy = "releaseVersions", fetch=FetchType.EAGER)
	//@JsonView({View.ReleaseVersionView.class})
	private Set<DataFile> dataFiles = new HashSet<>();
	
}

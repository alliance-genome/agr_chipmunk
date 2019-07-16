package org.alliancegenome.agr_submission.entities;

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
public class SchemaVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	private Long id;
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	private String schema;
	
	@OneToMany(mappedBy="schemaVersion", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	private Set<SchemaFile> schemaFiles;
	
	@ManyToMany(mappedBy="schemaVersions", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	private Set<ReleaseVersion> releaseVersions;
	
	@OneToMany(mappedBy="schemaVersion", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	private Set<DataFile> dataFiles;

}

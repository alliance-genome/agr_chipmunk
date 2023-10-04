package org.alliancegenome.agr_submission.entities;

import java.util.Set;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class SchemaVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	private Long id;
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	@Column(unique = true)
	private String schema;
	
	@OneToMany(mappedBy="schemaVersion")
	@JsonView({View.SchemaVersionView.class})
	private Set<SchemaFile> schemaFiles;
	
	@OneToMany(mappedBy="defaultSchemaVersion")
	@JsonView({View.SchemaVersionView.class})
	private Set<ReleaseVersion> releaseVersions;
	
	@OneToMany(mappedBy="schemaVersion")
	@JsonView({View.SchemaVersionView.class})
	private Set<DataFile> dataFiles;

}

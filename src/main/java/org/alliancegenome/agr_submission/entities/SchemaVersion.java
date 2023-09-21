package org.alliancegenome.agr_submission.entities;

import java.util.Set;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class SchemaVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	private Long id;
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	@Column(unique = true)
	private String schema;
	
	@OneToMany(mappedBy="schemaVersion", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	private Set<SchemaFile> schemaFiles;
	
	@OneToOne(mappedBy="defaultSchemaVersion")
	@JsonView({View.SchemaVersionView.class})
	private ReleaseVersion releaseVersion;
	
	@OneToMany(mappedBy="schemaVersion", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	private Set<DataFile> dataFiles;

}

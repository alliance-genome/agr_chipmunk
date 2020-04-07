package org.alliancegenome.agr_submission.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Schema(name="SchemaVersion", description="SnapShot model")
public class SchemaVersion extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	private Long id;
	@JsonView({View.SchemaVersionView.class, View.ReleaseVersionView.class, View.DataFileView.class, View.DataTypeView.class, View.SnapShotView.class, View.SchemaFileView.class})
	@Column(unique = true)
	private String schema;
	
	@OneToMany(mappedBy="schemaVersion", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	@Schema(implementation = SchemaFile.class)
	private Set<SchemaFile> schemaFiles;
	
	@OneToOne(mappedBy="defaultSchemaVersion")
	@JsonView({View.SchemaVersionView.class})
	@Schema(implementation = ReleaseVersion.class)
	private ReleaseVersion releaseVersion;
	
	@OneToMany(mappedBy="schemaVersion", fetch=FetchType.EAGER)
	@JsonView({View.SchemaVersionView.class})
	@Schema(implementation = DataFile.class)
	private Set<DataFile> dataFiles;

}

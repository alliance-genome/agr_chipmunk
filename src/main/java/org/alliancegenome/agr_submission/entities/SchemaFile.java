package org.alliancegenome.agr_submission.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Schema(name="SchemaFile", description="SchemaFile model")
public class SchemaFile extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.SchemaFileView.class, View.DataTypeView.class, View.SchemaVersionView.class})
	private Long id;
	@JsonView({View.SchemaFileView.class, View.DataTypeView.class, View.SchemaVersionView.class})
	private String filePath;
	
	@ManyToOne
	@JsonView({View.SchemaFileView.class, View.DataTypeView.class})
	@Schema(implementation = SchemaVersion.class)
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	@Schema(implementation = DataType.class)
	private DataType dataType;

}

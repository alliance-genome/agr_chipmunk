package org.alliancegenome.agr_submission.entities;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class SchemaFile extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.SchemaFileView.class, View.DataTypeView.class, View.SchemaVersionView.class})
	private Long id;
	@JsonView({View.SchemaFileView.class, View.DataTypeView.class, View.SchemaVersionView.class})
	private String filePath;
	
	@ManyToOne
	@JsonView({View.SchemaFileView.class, View.DataTypeView.class})
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	private DataType dataType;

}

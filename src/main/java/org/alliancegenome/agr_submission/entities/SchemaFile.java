package org.alliancegenome.agr_submission.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class SchemaFile extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.DataTypeView.class, View.SchemaVersionView.class})
	private Long id;
	@JsonView({View.DataTypeView.class, View.SchemaVersionView.class})
	private String filePath;
	
	@ManyToOne
	@JsonView({View.DataTypeView.class})
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	private DataType dataType;

}

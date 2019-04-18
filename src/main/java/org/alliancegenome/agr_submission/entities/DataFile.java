package org.alliancegenome.agr_submission.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Entity @ApiModel
@Getter @Setter
public class DataFile extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class})
	private Long id;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class})
	private String s3Path;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class})
	private String urlPath;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class})
	private Date uploadDate = new Date();
	
	@ManyToOne
	@JsonView({View.DataFileView.class})
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class})
	private DataType dataType;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class})
	private DataSubType dataSubType;

}

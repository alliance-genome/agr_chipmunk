package org.alliancegenome.agr_submission.entities;

import java.util.Date;

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
public class DataFile extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private Long id;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private String s3Path;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private String urlPath;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private String md5Sum;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private Boolean valid = true;
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private Date uploadDate = new Date();
	
	@ManyToOne
	@JsonView({View.DataFileView.class})
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private DataType dataType;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class})
	private DataSubType dataSubType;

	public boolean isValid() {
		return (valid == null || valid);
	}

}

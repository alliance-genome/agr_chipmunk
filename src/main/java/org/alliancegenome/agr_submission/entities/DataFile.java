package org.alliancegenome.agr_submission.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Schema(name="DataFile", description="DataFile model")
public class DataFile extends BaseEntity implements Comparable<DataFile> {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	@JsonView({View.API.class})
	private String s3Path;
	@JsonView({View.API.class})
	private String urlPath;
	@JsonView({View.API.class})
	@Column(unique=true)
	private String md5Sum;
	@JsonView({View.API.class})
	private Boolean valid = true;
	@JsonView({View.API.class})
	private Date uploadDate = new Date();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonView({View.DataFileView.class})
	//@ArraySchema(arraySchema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ReleaseVersion.class))
	private Set<ReleaseVersion> releaseVersions = new HashSet<ReleaseVersion>();

	@ManyToOne
	@JsonView({View.DataFileView.class})
	@Schema(implementation = SchemaVersion.class)
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class, View.ReleaseVersionView.class})
	@Schema(implementation = DataType.class)
	private DataType dataType;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class, View.ReleaseVersionView.class})
	@Schema(implementation = DataSubType.class)
	private DataSubType dataSubType;

	public boolean isValid() {
		return (valid == null || valid);
	}

	@Override
	public int compareTo(DataFile o) {
		return o.uploadDate.compareTo(uploadDate);
	}

}

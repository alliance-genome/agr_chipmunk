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
import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
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
	private Set<ReleaseVersion> releaseVersions = new HashSet<ReleaseVersion>();

	@ManyToOne
	@JsonView({View.DataFileView.class})
	private SchemaVersion schemaVersion;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class, View.ReleaseVersionView.class})
	private DataType dataType;
	
	@ManyToOne
	@JsonView({View.DataFileView.class, View.SchemaVersionView.class, View.SnapShotView.class, View.ReleaseVersionView.class})
	private DataSubType dataSubType;

	@JsonView({View.API.class})
	public String getStableURL() {
		if(ConfigHelper.getAWSBucketName().equals(ConfigHelper.getAWSBucketName())) {
			return ConfigHelper.getDownloadSite() + dataType.getName() + "_" + dataSubType.getName() + "." + dataType.getFileExtension();
		} else if(ConfigHelper.getAWSBucketName().contentEquals(ConfigHelper.getAWSBucketNameDev())) {
			return ConfigHelper.getAWSBucketNameDev() + dataType.getName() + "_" + dataSubType.getName() + "." + dataType.getFileExtension();
		} else {
			return null;
		}
	}
	
	@JsonView({View.API.class})
	public String getStableGzipURL() {
		String suffix = "";
		if(!dataType.getFileExtension().contains(".gz")) {
			suffix = ".gz";
		}
		if(ConfigHelper.getAWSBucketName().equals(ConfigHelper.getAWSBucketName())) {
			return ConfigHelper.getDownloadSite() + dataType.getName() + "_" + dataSubType.getName() + "." + dataType.getFileExtension() + suffix;
		} else if(ConfigHelper.getAWSBucketName().contentEquals(ConfigHelper.getAWSBucketNameDev())) {
			return ConfigHelper.getAWSBucketNameDev() + dataType.getName() + "_" + dataSubType.getName() + "." + dataType.getFileExtension() + suffix;
		} else {
			return null;
		}
	}
	
	@JsonView({View.API.class})
	public String getS3Url() {
		if(ConfigHelper.getAWSBucketName().equals(ConfigHelper.getAWSBucketName())) {
			return ConfigHelper.getDownloadSite() + s3Path;
		} else if(ConfigHelper.getAWSBucketName().contentEquals(ConfigHelper.getAWSBucketNameDev())) {
			return ConfigHelper.getAWSBucketNameDev() + s3Path;
		} else {
			return null;
		}
	}
	
	public boolean isValid() {
		return (valid == null || valid);
	}

	@Override
	public int compareTo(DataFile o) {
		return o.uploadDate.compareTo(uploadDate);
	}

}

package org.alliancegenome.agr_submission.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
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

	
	@Transient
	@JsonIgnore
	private String currentRelease = null;
	@Transient
	@JsonIgnore
	private String requestRelease = null;
	
	@Transient
	@JsonIgnore
	public void setStableURL(String currentRelease, String requestRelease) {
		this.currentRelease = currentRelease;
		this.requestRelease = requestRelease;
	}

	@JsonView({View.API.class})
	public String getStableURL() {
		Config config = ConfigProvider.getConfig();
		String fmsDownloadUrlPrefix = config.getValue("fms.download.urlPrefix", String.class);
		if((currentRelease == null && requestRelease == null) || requestRelease.equals(currentRelease)) {
			String suffix = "";
			if(s3Path.endsWith(".gz")) suffix = ".gz";

			if(fmsDownloadUrlPrefix != null) {
				return fmsDownloadUrlPrefix + "/" + dataType.getName() + "_" + dataSubType.getName() + "." + dataType.getFileExtension() + suffix;
			} else {
				return null;
			}
		} else {
			return getS3Url();
		}
	}
	
	@JsonView({View.API.class})
	public String getS3Url() {
		Config config = ConfigProvider.getConfig();
		String bucketHost = config.getValue("aws.bucket.host", String.class);
		if(bucketHost != null) {
			return bucketHost + "/" + s3Path;
		} else {
			return null;
		}
	}
	
	public Boolean isValid() {
		return (valid == null || valid);
	}

	@Override
	public int compareTo(DataFile o) {
		return o.uploadDate.compareTo(uploadDate);
	}

}

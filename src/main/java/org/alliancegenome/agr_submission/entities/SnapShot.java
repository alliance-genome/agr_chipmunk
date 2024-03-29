package org.alliancegenome.agr_submission.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.apache.commons.collections4.keyvalue.MultiKey;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString(of= {"id", "snapShotDate"})
public class SnapShot extends BaseEntity {

	@Id @GeneratedValue
	@JsonView(View.API.class)
	private Long id;
	@JsonView(View.API.class)
	private Date snapShotDate;

	@ManyToOne
	@JsonView({ View.SnapShotView.class, View.SnapShotMultipleView.class })
	private ReleaseVersion releaseVersion;
	
	@Transient
	@JsonProperty(access = Access.READ_ONLY)
	@JsonView({View.SnapShotView.class, View.ReleaseVersionView.class})
	public ArrayList<DataFile> getDataFiles() {
		ArrayList<DataFile> dataFiles = new ArrayList<DataFile>();
		HashMap<MultiKey<String>, DataFile> currentFiles = new HashMap<>();

		for(DataFile df: releaseVersion.getDataFiles()) {
			MultiKey<String> key = new MultiKey<String>(df.getDataType().getName(), df.getDataSubType().getName());
			DataFile entry = currentFiles.get(key);
			if(df.getUploadDate().before(snapShotDate) && df.isValid()) {
				if(entry == null) {
					currentFiles.put(key, df);
				} else {
					if(df.getUploadDate().after(entry.getUploadDate())) {
						currentFiles.put(key, df);
					}
				}
			}
		}

		for(MultiKey<String> key: currentFiles.keySet()) {
			dataFiles.add(currentFiles.get(key));
		}
		Collections.sort(dataFiles);
		return dataFiles;
	}
	
}

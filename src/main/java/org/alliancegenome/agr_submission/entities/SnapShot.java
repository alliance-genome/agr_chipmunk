package org.alliancegenome.agr_submission.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.alliancegenome.agr_submission.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Entity @ApiModel
@Getter @Setter
public class SnapShot extends BaseEntity {

	@Id @GeneratedValue
	private Long id;
	private Date snapShotDate;

	@ManyToOne
	private ReleaseVersion releaseVersion;
}

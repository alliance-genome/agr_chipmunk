package org.alliancegenome.agr_submission.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.alliancegenome.agr_submission.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class User extends BaseEntity {

	@Id @GeneratedValue
	private Long id;
	private String username;
	private String password;
}

package org.alliancegenome.agr_submission.entities;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity @Data @ToString
@JsonView({View.UserView.class})
public class LoggedInUser extends BaseEntity {

	@Id @GeneratedValue
	private Long id;
	private String name;
	private String username;
	private String password;
	private String apiKey;
}

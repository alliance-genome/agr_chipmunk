package org.alliancegenome.agr_submission.entities;

import javax.persistence.*;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

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

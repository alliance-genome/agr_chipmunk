package org.alliancegenome.agr_submission.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Getter @Setter @ToString
@JsonView({View.UserView.class})
@Schema(name="User", description="User model")
public class User extends BaseEntity {

	@Id @GeneratedValue
	private Long id;
	private String name;
	private String username;
	private String password;
	private String apiKey;
}

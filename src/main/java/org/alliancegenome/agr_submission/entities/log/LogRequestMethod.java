package org.alliancegenome.agr_submission.entities.log;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class LogRequestMethod extends BaseEntity {
	
	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	
	@JsonView({View.API.class})
	private String requestMethod;
}

package org.alliancegenome.agr_submission.entities.log;

import javax.persistence.*;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

@Entity
@Getter @Setter @ToString
public class LogRequestMethod extends BaseEntity {
	
	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	
	@JsonView({View.API.class})
	private String requestMethod;
}

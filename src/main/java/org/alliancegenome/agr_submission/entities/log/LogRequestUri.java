package org.alliancegenome.agr_submission.entities.log;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class LogRequestUri extends BaseEntity {
	
	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	
	@JsonView({View.API.class})
	@Lob
	private String requestUri;
}

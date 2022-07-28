package org.alliancegenome.agr_submission.entities.log;

import javax.persistence.*;

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
	@Column(columnDefinition="TEXT")
	private String requestUri;
}

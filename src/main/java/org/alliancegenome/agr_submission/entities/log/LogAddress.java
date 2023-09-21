package org.alliancegenome.agr_submission.entities.log;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
public class LogAddress extends BaseEntity {
	
	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	
	@JsonView({View.API.class})
	private String address;
}

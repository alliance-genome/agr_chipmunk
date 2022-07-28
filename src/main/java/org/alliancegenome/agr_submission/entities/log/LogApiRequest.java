package org.alliancegenome.agr_submission.entities.log;

import java.util.Date;

import javax.persistence.*;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.entities.LoggedInUser;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

@Entity
@Getter @Setter @ToString
public class LogApiRequest extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	
	@JsonView({View.API.class})
	@Column(columnDefinition="TEXT")
	private String headerString;
	
	@JsonView({View.API.class})
	@Column(columnDefinition="TEXT")
	private String queryParametersString;
	
	@JsonView({View.API.class})
	@Column(columnDefinition="TEXT")
	private String pathParametersString;
	
	@JsonView({View.API.class})
	private Date timeStamp = new Date();
	
	@ManyToOne
	private LoggedInUser user;
	
	@ManyToOne
	private LogUserAgent userAgent;
	
	@ManyToOne
	private LogRequestMethod requestMethod;
	
	@ManyToOne
	private LogAddress address;
	
	@ManyToOne
	private LogRequestUri requestUri;


}

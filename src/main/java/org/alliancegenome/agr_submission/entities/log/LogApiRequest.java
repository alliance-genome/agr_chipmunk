package org.alliancegenome.agr_submission.entities.log;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class LogApiRequest extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	
	@JsonView({View.API.class})
	@Lob
	private String headerString;
	
	@JsonView({View.API.class})
	@Lob
	private String queryParametersString;
	
	@JsonView({View.API.class})
	@Lob
	private String pathParametersString;
	
	@JsonView({View.API.class})
	private Date timeStamp = new Date();
	
	@ManyToOne
	private LogUserAgent userAgent;
	
	@ManyToOne
	private LogRequestMethod requestMethod;
	
	@ManyToOne
	private LogAddress address;
	
	@ManyToOne
	private LogRequestUri requestUri;


}

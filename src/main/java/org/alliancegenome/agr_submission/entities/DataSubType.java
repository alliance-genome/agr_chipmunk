package org.alliancegenome.agr_submission.entities;

import java.util.List;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString(of = {"id", "name", "description"})
public class DataSubType extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	@Column(unique=true)
	@JsonView({View.API.class})
	private String name;
	@JsonView({View.API.class})
	private String description;
	
	@ManyToMany(mappedBy = "dataSubTypes")
	private List<DataType> dataTypes;
	
	@Transient
	@JsonIgnore
	@JsonView({View.API.class})
	public String getLabel() {
		return name;
	}
	
}

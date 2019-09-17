package org.alliancegenome.agr_submission.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString(of = {"id", "name", "description"})
public class DataSubType extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	@JsonView({View.API.class})
	private String name;
	@JsonView({View.API.class})
	private String description;
	
	@ManyToMany(mappedBy = "dataSubTypes")
	@JsonView({View.DataSubTypeView.class})
	private List<DataType> dataTypes;
	
	@Transient
	@JsonIgnore
	@JsonView({View.API.class})
	public String getLabel() {
		return name;
	}
	
}

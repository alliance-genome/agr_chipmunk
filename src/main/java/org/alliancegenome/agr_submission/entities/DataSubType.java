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

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Entity @ApiModel
@Getter @Setter
public class DataSubType extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.DataSubTypeView.class, View.DataTypeView.class, View.DataFileView.class, View.SchemaVersionView.class})
	private Long id;
	@JsonView({View.DataSubTypeView.class, View.DataTypeView.class, View.DataFileView.class, View.SchemaVersionView.class})
	private String name;
	@JsonView({View.DataSubTypeView.class, View.DataTypeView.class, View.SchemaVersionView.class})
	private String description;
	
	@ManyToMany(mappedBy = "dataSubTypes")
	private List<DataType> dataTypes;
	
	@Transient
	@JsonIgnore
	@JsonView({View.DataSubTypeView.class, View.DataTypeView.class, View.DataFileView.class, View.SchemaVersionView.class})
	public String getLabel() {
		return name;
	}
	
}

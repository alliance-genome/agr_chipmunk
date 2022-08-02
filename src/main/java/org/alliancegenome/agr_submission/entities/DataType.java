package org.alliancegenome.agr_submission.entities;

import java.util.*;

import javax.persistence.*;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.*;

@Entity
@Getter @Setter @ToString
public class DataType extends BaseEntity {

	@Id @GeneratedValue
	@JsonView({View.API.class})
	private Long id;
	@JsonView({View.API.class})
	@Column(unique = true)
	private String name;
	@JsonView({View.API.class})
	private String description;
	@JsonView({View.API.class})
	private String fileExtension;
	@JsonView({View.API.class})
	private boolean dataSubTypeRequired;
	@JsonView({View.API.class})
	private boolean validationRequired;

	@ManyToMany(fetch=FetchType.EAGER)
	@JsonView({View.DataTypeView.class})
	private Set<DataSubType> dataSubTypes;

	@OneToMany(mappedBy = "dataType", fetch=FetchType.EAGER)
	@JsonView({View.DataTypeView.class})
	private Set<SchemaFile> schemaFiles;

	@Transient
	public Map<String, String> getSchemaFilesMap() {
		HashMap<String, String> map = new HashMap<>();
		if(schemaFiles != null) {
			for(SchemaFile s: schemaFiles) {
				map.put(s.getSchemaVersion().getSchema(), s.getFilePath());
			}
		}
		return map;
	}

}

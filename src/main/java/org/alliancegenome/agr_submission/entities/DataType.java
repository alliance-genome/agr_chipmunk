package org.alliancegenome.agr_submission.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.alliancegenome.agr_submission.BaseEntity;
import org.alliancegenome.agr_submission.views.View;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Schema(name="DataType", description="DataType model")
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
	@Schema(implementation = DataSubType.class)
	private Set<DataSubType> dataSubTypes;

	@OneToMany(mappedBy = "dataType", fetch=FetchType.EAGER)
	@JsonView({View.DataTypeView.class})
	@Schema(implementation = SchemaFile.class)
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

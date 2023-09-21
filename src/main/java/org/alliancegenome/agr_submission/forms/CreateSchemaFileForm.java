package org.alliancegenome.agr_submission.forms;

import org.alliancegenome.agr_submission.BaseForm;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateSchemaFileForm extends BaseForm {

	private String schema;
	private String filePath;
}

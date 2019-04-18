package org.alliancegenome.agr_submission.forms;

import org.alliancegenome.agr_submission.BaseForm;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @ApiModel
public class CreateSchemaFileForm extends BaseForm {

	private String schema;
	private String filePath;
}

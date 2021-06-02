package org.alliancegenome.agr_submission.main;

import java.io.*;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.*;
import com.github.fge.jsonschema.main.*;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestValidation {

	public static void main(String[] args) throws Exception {
		
		//File schemaFile = new File("/Users/olinblodgett/git/agr_schemas/ingest/phenotype/phenotypeMetaDataDefinition.json");
		//File inFile = new File("/Users/olinblodgett/Downloads/ZFIN_1.0.1.4_phenotype.json.gz");
		File schemaFile = new File("/Users/olinblodgett/git/agr_schemas/ingest/disease/diseaseMetaDataDefinition.json");
		File inFile = new File("/Users/olinblodgett/Desktop/1.0.1.4_DAF_ZFIN_0.json.gz");
		
		
		
		JsonSchema schemaNode = JsonSchemaFactory.byDefault().getJsonSchema(schemaFile.toURI().toString());
		Reader reader = new InputStreamReader(new GZIPInputStream(new FileInputStream(inFile)));
		JsonNode jsonNode = JsonLoader.fromReader(reader);
		ProcessingReport report = schemaNode.validate(jsonNode);
		reader.close();

		//if(!report.isSuccess()) {
			for(ProcessingMessage message: report) {
				if(message.getLogLevel() == LogLevel.WARNING) {
					log.info(message);
				}
			}
		//}
		log.info("Validation Complete: " + report.isSuccess());

	}

}

package org.alliancegenome.agr_submission.dao;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.SchemaVersion;
import org.alliancegenome.agr_submission.util.github.GithubRESTAPI;
import org.alliancegenome.agr_submission.util.github.GithubRelease;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
public class SchemaVersionDAO extends BaseSQLDAO<SchemaVersion> {

	private GithubRESTAPI githubAPI = new GithubRESTAPI();
	
	public SchemaVersionDAO() {
		super(SchemaVersion.class);
	}

	public SchemaVersion getCurrentSchemaVersion() {
		//getMetaDocument();
		//if(metaData.getCurrentRelease().length() > 0) {
		//	return metaData.getReleaseSchemaMap().get(metaData.getCurrentRelease());
		//} else {
			log.warn("Current Release Version is not Set on metadata document");
			return null;
		//}
	}
	
	public SchemaVersion getByName(String schemaVersion) {
		return findByField("schema", schemaVersion);
	}
	
	// Null -> Returns current schema from release marked current
	// Invalid schema -> null
	// Schema not in ES but in Github -> schema version
	public SchemaVersion getSchemaVersion(String schemaVersion) {

		log.debug("Getting Schema Version");
		if(schemaVersion != null) {
			SchemaVersion sv = findByField("schema", schemaVersion);
			if(sv == null) {
				GithubRelease gitHubSchema = githubAPI.getRelease("agr_schemas", schemaVersion);

				if(gitHubSchema != null) {
					log.info("Found Github Schema need to save to DB: ");
					sv = new SchemaVersion();
					sv.setSchema(schemaVersion);
					persist(sv);
					return sv;
				} else {
					log.debug("Github Schema Version was null: " + gitHubSchema);
					return null;
				}
			}
			log.debug("Schema Found: " + schemaVersion);
			return sv;
		} else {
			log.debug("Null Schema version");
			return getCurrentSchemaVersion();
		}
	}

	public String getPreviousVersion(String version) {
		String[] array = version.split("\\.");
		int out = Integer.parseInt(array[0] + array[1] + array[2] + array[3]);
		if(out <= 0) return null;
		out--;
		String a = (out / 1000) + "";
		out = out % 1000;
		String b = (out / 100) + "";
		out = out % 100;
		String c = (out / 10) + "";
		out = out % 10;
		String d = out + "";
		return a + "." + b + "." + c + "." + d;
	}
	
}

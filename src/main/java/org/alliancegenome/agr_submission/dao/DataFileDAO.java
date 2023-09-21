package org.alliancegenome.agr_submission.dao;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.DataFile;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataFileDAO extends BaseSQLDAO<DataFile> {

	public DataFileDAO() {
		super(DataFile.class);
	}

	public DataFile getByIdOrMD5Sum(String id) {
		try {
			Long ident = Long.parseLong(id);
			return find(ident);
		} catch (NumberFormatException ex) {
			return findByField("md5Sum", id);
		}
	}

}

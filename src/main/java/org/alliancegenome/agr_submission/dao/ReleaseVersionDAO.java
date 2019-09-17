package org.alliancegenome.agr_submission.dao;

import java.util.List;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ApplicationScoped
public class ReleaseVersionDAO extends BaseSQLDAO<ReleaseVersion> {

	public ReleaseVersionDAO() {
		super(ReleaseVersion.class);
	}
	
	public ReleaseVersion getByName(String releaseVersion) {
		return findByField("releaseVersion", releaseVersion);
	}

	public ReleaseVersion getCurrentReleaseVersion() {
		List<ReleaseVersion> versions = findAll();
		TreeSet<String> set = new TreeSet<>();
		for(ReleaseVersion rv: versions) {
			set.add(rv.getReleaseVersion());
		}
		log.debug("Return most current Release Version: " + set.pollFirst());
		return getByName(set.pollLast());
	}

}

package org.alliancegenome.agr_submission.dao;

import java.util.*;

import javax.enterprise.context.ApplicationScoped;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.ReleaseVersion;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
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

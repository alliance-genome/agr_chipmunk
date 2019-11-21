package org.alliancegenome.agr_submission.services;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.alliancegenome.agr_submission.BaseService;
import org.alliancegenome.agr_submission.dao.log.LogAddressDAO;
import org.alliancegenome.agr_submission.dao.log.LogApiRequestDAO;
import org.alliancegenome.agr_submission.dao.log.LogRequestMethodDAO;
import org.alliancegenome.agr_submission.dao.log.LogRequestUriDAO;
import org.alliancegenome.agr_submission.dao.log.LogUserAgentDAO;
import org.alliancegenome.agr_submission.entities.log.LogApiDTO;
import org.alliancegenome.agr_submission.entities.log.LogApiRequest;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class LogApiRequestService extends BaseService<LogApiRequest> {

	
	@Inject LogApiRequestDAO apiRequestDAO;
	@Inject LogUserAgentDAO userAgentDAO;
	@Inject LogRequestMethodDAO requestMethodDAO;
	@Inject LogAddressDAO logAddressDAO;
	@Inject LogRequestUriDAO logRequestUriDAO;

	@Transactional
	public void saveLogApi(@Observes LogApiDTO info) {
		log.debug(info);
		
		LogApiRequest apiLog = new LogApiRequest();
		
		apiLog.setHeaderString(info.getHeadersString());
		apiLog.setQueryParametersString(info.getQueryParameters().toString());
		apiLog.setPathParametersString(info.getPathParameters().toString());
		
		apiLog.setUserAgent(userAgentDAO.save(info.getUserAgent().toString()));
		apiLog.setRequestMethod(requestMethodDAO.save(info.getRequestMethod()));
		apiLog.setAddress(logAddressDAO.save(info.getAddress()));
		apiLog.setRequestUri(logRequestUriDAO.save(info.getRequestUri()));
		create(apiLog);
	}

	@Override
	public LogApiRequest create(LogApiRequest entity) {
		return apiRequestDAO.persist(entity);
	}

	@Override
	public LogApiRequest get(Long id) {
		return null;
	}

	@Override
	public LogApiRequest update(LogApiRequest entity) {
		return null;
	}

	@Override
	public LogApiRequest delete(Long id) {
		return null;
	}
}

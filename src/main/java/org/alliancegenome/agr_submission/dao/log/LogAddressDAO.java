package org.alliancegenome.agr_submission.dao.log;

import org.alliancegenome.agr_submission.BaseSQLDAO;
import org.alliancegenome.agr_submission.entities.log.LogAddress;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogAddressDAO extends BaseSQLDAO<LogAddress> {

	public LogAddressDAO() {
		super(LogAddress.class);
	}
	
	public LogAddress save(String logAddress) {
		LogAddress address = findByField("address", logAddress);
		if(address == null) {
			LogAddress newAddress = new LogAddress();
			newAddress.setAddress(logAddress);
			return persist(newAddress);
		} else {
			return address;
		}
	}
}

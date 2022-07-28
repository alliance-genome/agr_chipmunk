package org.alliancegenome.agr_submission;

public class BaseDAO<E extends BaseEntity> {

	protected Class<E> myClass;

	protected BaseDAO(Class<E> myClass) {
		this.myClass = myClass;
	}

}

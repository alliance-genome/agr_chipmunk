package org.alliancegenome.agr_submission;

public abstract class BaseService<E extends BaseEntity> {
	
	public abstract E create(E entity);
	public abstract E get(Long id);
	public abstract E update(E entity);
	public abstract E delete(Long id);
	
}

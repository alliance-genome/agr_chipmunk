package org.alliancegenome.agr_submission.application;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class RestDefaultObjectMapper implements ContextResolver<ObjectMapper> {

	private final ObjectMapper mapper;

	public RestDefaultObjectMapper() {
		mapper = new ObjectMapper();
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}

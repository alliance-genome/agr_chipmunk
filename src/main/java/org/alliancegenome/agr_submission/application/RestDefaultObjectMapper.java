package org.alliancegenome.agr_submission.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestDefaultObjectMapper implements ContextResolver<ObjectMapper> {

	private final ObjectMapper mapper;

	public RestDefaultObjectMapper() {
		mapper = new ObjectMapper();
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}

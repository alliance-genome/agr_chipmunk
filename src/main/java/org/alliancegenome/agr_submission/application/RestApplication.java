package org.alliancegenome.agr_submission.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@ApplicationPath("/api")
@OpenAPIDefinition(
	info = @Info(
		description = "This is the File Management System Java API",
		title = "File Management System API",
		version = "1.0 Beta"
	),
	security = {
		@SecurityRequirement(name = "api_token"),
		@SecurityRequirement(name = "no_auth"),
	},
	components = @Components(
		securitySchemes = {
			@SecurityScheme(
				securitySchemeName="api_token",
				type = SecuritySchemeType.HTTP,
				description="Curator API Token",
				scheme="bearer"
			),
			@SecurityScheme(
				securitySchemeName="no_auth",
				type=SecuritySchemeType.DEFAULT,
				scheme="None"
			)
		}
	)
)
public class RestApplication extends Application {

}

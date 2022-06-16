package br.com.douglas.apigateway.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenApiGateway {
	
	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters configParameters, RouteDefinitionLocator definitionLocator) {
		
		List<RouteDefinition> routesDefinitions = definitionLocator.getRouteDefinitions().collectList().block();
		
		routesDefinitions.stream()
			.filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
			.forEach(routeDefinition -> {
				String name = routeDefinition.getId();
				
				configParameters.addGroup(name);
				
				GroupedOpenApi.builder()
					.pathsToMatch("/" + name + "/**")
					.group(name)
					.build();
		});
		
		return new ArrayList<>();
	}
	
}

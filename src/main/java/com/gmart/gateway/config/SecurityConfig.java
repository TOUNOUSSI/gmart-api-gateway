/*
 *  This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/deed.en_US">Creative Commons Attribution 3.0 Unported License</a>.
 *  Copyright © GMART, unpublished work. This computer program
 *  includes confidential, proprietary information and is a trade secret of GMART Inc.
 *  All use, disclosure, or reproduction is prohibited unless authorized
 *  in writing by TOUNOUSSI Youssef. All Rights Reserved.
 */
package com.gmart.gateway.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.gmart.gateway.adapters.OrganizationSubClaimAdapter;

/**
 * @author <a href="mailto:youssef.tounoussi@gmail.com">TOUNOUSSI Youssef</a>
 * @create 6 avr. 2021
 **/
@Configuration
@EnableOAuth2Sso
@Order(value = 0)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private OAuth2ClientContextFilter oauth2ClientContextFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

		.authorizeRequests()
				.antMatchers("/", "/*.js", "/*.jsp", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js", "/authentication/**", "/invalidSession",
						"/resources/invalidSession", "oauth/**", "/**")
				.permitAll()
				.antMatchers(HttpMethod.POST,"/token/generate-token").permitAll();
	}

//	@Bean
//	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(filter);
//		registration.setOrder(-100);
//		return registration;
//	}

	@Bean
	public JwtDecoder customDecoder(OAuth2ResourceServerProperties properties) {
		NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.getJwt().getJwkSetUri()).build();

		jwtDecoder.setClaimSetConverter(new OrganizationSubClaimAdapter());
		return jwtDecoder;
	}

	@Bean
	public OAuth2ProtectedResourceDetails bnetResource() {
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setId("2d158462-1bdf-4fa7-b2c4-dd2f878fe43f");
		resource.setClientId("GMART_APP");
		resource.setClientSecret("VE9VTk9VU1NJOnA=");
		resource.setAccessTokenUri("http://localhost:9092/oauth/token");
		resource.setUserAuthorizationUri("http://localhost:9092/oauth/authorize");
		resource.setScope(Arrays.asList("role_user", "role_admin"));
		return resource;

	}
}

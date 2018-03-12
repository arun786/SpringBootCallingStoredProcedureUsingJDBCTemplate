/**
 * 
 */
package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * @author Adwiti
 *
 */
@Configuration
public class SimpleJDBCCallImplementation extends SimpleJdbcCall {

	public SimpleJDBCCallImplementation(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

}

/**
 * 
 */
package com.example.demo.config;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import com.example.demo.country.Country;

/**
 * @author Adwiti
 *
 */
public class CountryStoredProcedure extends StoredProcedure {

	public CountryStoredProcedure(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "getCountry");

		RowMapper<Country> rowMapper = new CountryRowMapper();
		declareParameter(new SqlReturnResultSet("country", rowMapper));
		declareParameter(new SqlParameter("id", Types.VARCHAR));
		declareParameter(new SqlOutParameter("id", Types.VARCHAR));
		declareParameter(new SqlOutParameter("name", Types.VARCHAR));
		declareParameter(new SqlOutParameter("capital", Types.VARCHAR));
		declareParameter(new SqlOutParameter("currency", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getCountry(String id) {
		Map in = new HashMap<>();
		in.put("id", id);
		Map out = execute(in);
		return out;
	}

}

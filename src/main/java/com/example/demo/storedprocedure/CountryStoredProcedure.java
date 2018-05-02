/**
 * 
 */
package com.example.demo.storedprocedure;

import com.example.demo.config.CountryRowMapper;
import com.example.demo.model.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adwiti
 *
 */
public class CountryStoredProcedure extends StoredProcedure {

	public CountryStoredProcedure(JdbcTemplate jdbcTemplate) {
		/**
		 * This will be the name of the stored procedure, getCountry
		 */
		super(jdbcTemplate, "getCountry");
		RowMapper<Country> rowMapper = new CountryRowMapper();
		declareParameter(new SqlReturnResultSet("country", rowMapper));
		declareParameter(new SqlParameter("id", Types.VARCHAR));
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

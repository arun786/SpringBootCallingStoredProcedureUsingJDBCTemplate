/**
 * 
 */
package com.example.demo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.config.CountryStoredProcedure;
import com.example.demo.config.SimpleJDBCCallImplementation;
import com.example.demo.country.Country;

/**
 * @author Adwiti
 *
 */
@Repository
public class CountryDAO implements ICountryDAO {

	private CountryStoredProcedure countryStoredProcedure;
	@Autowired
	SimpleJDBCCallImplementation simpleJdbcCall;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void postConstruct() {
		countryStoredProcedure = new CountryStoredProcedure(jdbcTemplate);
	}

	@Override
	public List<Country> getAllCountry(String id) {

		simpleJdbcCall.withProcedureName("getAllCountry");
		MapSqlParameterSource inputMaps = new MapSqlParameterSource();
		inputMaps.addValue("country_id", id);
		Map<String, Object> outMaps = simpleJdbcCall.execute(inputMaps);
		System.out.println(outMaps);
		System.out.println(outMaps.get("#result-set-1"));
		Country country = new Country();
		country.setId(String.valueOf(outMaps.get("id")));
		country.setName(String.valueOf(outMaps.get("name")));
		country.setCapital(String.valueOf(outMaps.get("capital")));
		country.setCurrency(String.valueOf(outMaps.get("currency")));
		return Arrays.asList(country);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Country getACountry(String id) {

		/*@SuppressWarnings("rawtypes")
		List paramList = new ArrayList();
		paramList.add(new SqlParameter(Types.VARCHAR));
		paramList.add(new SqlOutParameter("id", Types.VARCHAR));
		paramList.add(new SqlOutParameter("name", Types.VARCHAR));
		paramList.add(new SqlOutParameter("capital", Types.VARCHAR));
		paramList.add(new SqlOutParameter("currency", Types.VARCHAR));

		Map<String, Object> inputMap = jdbcTemplate.call(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {

				CallableStatement callableStatement = con.prepareCall("{call getCountry(?,?,?,?,?)}");
				callableStatement.setString(1, id);
				callableStatement.registerOutParameter(2, Types.VARCHAR);
				callableStatement.registerOutParameter(3, Types.VARCHAR);
				callableStatement.registerOutParameter(4, Types.VARCHAR);
				callableStatement.registerOutParameter(5, Types.VARCHAR);
				return callableStatement;
			}
		}, paramList);

		System.out.println("Arun..." + inputMap);*/

		Map data = countryStoredProcedure.getCountry(id);
		
		@SuppressWarnings("rawtypes")
		List result = (List) data.get("country");
		System.out.println("Arun " + result.get(0));
		
		Country c = (Country) result.get(0);
		System.out.println(c.getCapital());
		
		/**
		 * Start completed.
Arun Country [id=3, name=Pakistan, capital=Karachi, currency=Rupee]
Karachi
		 */
		
		
		return null;
	}

}

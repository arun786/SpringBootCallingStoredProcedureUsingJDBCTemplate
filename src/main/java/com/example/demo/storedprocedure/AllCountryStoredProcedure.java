package com.example.demo.storedprocedure;

import com.example.demo.rowmapper.CountryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adwiti on 5/1/2018.
 */
public class AllCountryStoredProcedure extends StoredProcedure {

    public AllCountryStoredProcedure(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "getAllCountry");
        CountryRowMapper countryRowMapper = new CountryRowMapper();
        declareParameter(new SqlReturnResultSet("AllCountry", countryRowMapper));
        compile();
    }

    public Map getAllCountry() {
        Map in = new HashMap();
        Map out = execute(in);
        return out;
    }
}

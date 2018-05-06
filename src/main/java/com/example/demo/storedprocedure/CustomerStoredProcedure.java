package com.example.demo.storedprocedure;

import com.example.demo.rowmapper.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adwiti on 5/5/2018.
 */
public class CustomerStoredProcedure extends StoredProcedure {
    public CustomerStoredProcedure(JdbcTemplate jdbcTemplate){
        super(jdbcTemplate, "getCustomer");
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        declareParameter(new SqlReturnResultSet("customer", customerRowMapper));
        declareParameter(new SqlParameter("id", Types.VARCHAR));
    }

    public Map<String,Object> getCustomer(String id){
        Map<String,Object> in = new HashMap<>();
        in.put("id",id);
        Map<String,Object> out = execute(in);
        return out;
    }
}

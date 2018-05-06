package com.example.demo.rowmapper;

import com.example.demo.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Adwiti on 5/5/2018.
 */
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getString("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        return customer;
    }
}
